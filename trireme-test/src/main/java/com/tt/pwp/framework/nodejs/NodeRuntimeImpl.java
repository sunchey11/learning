package com.tt.pwp.framework.nodejs;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeModule;
import io.apigee.trireme.core.NodeRuntime;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.Sandbox;
import io.apigee.trireme.core.ScriptTask;
import io.apigee.trireme.core.internal.AbstractModuleRegistry;
import io.apigee.trireme.core.internal.ChildModuleRegistry;
import io.apigee.trireme.core.internal.RootModuleRegistry;
import io.apigee.trireme.core.modules.Buffer;
import io.apigee.trireme.core.modules.NativeModule;
import io.apigee.trireme.core.modules.Process;
import io.apigee.trireme.core.modules.Process.ProcessImpl;
import io.apigee.trireme.node10.main.trireme;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class NodeRuntimeImpl implements NodeRuntime {
	protected NodeEnvironment env;
	protected ContextFactory contextFactory;
	protected ScriptableObject scope;
	protected AbstractModuleRegistry registry;
	protected Function main;
	protected String nodeVersion = NodeEnvironment.DEFAULT_NODE_VERSION;
	protected NativeModule.NativeImpl nativeModule;
	protected Buffer.BufferModuleImpl buffer;
	protected ProcessImpl process;
	protected final HashMap<String, NativeModule.ModuleImpl> moduleCache = new HashMap<String, NativeModule.ModuleImpl>();
	protected AbstractModuleRegistry getRegistry() throws NodeException {
		RootModuleRegistry root = env.getRegistry(nodeVersion);
		if (root == null) {
			throw new NodeException(
					"No available Node.js implementation matches version "
							+ nodeVersion);
		}
		return new ChildModuleRegistry(root);
	}
	public void init() throws NodeException {
		NodeEnvironment env = new NodeEnvironment();
		callMethod(NodeEnvironment.class, env, "initialize", new Object[0]);
		registry = getRegistry();

		contextFactory = env.getContextFactory();
		scope = (ScriptableObject) contextFactory.call(new ContextAction() {

			public Object run(Context cx) {

				// All scripts get their own global scope. This is a lot
				// safer
				// than sharing them in case a script wants
				// to add to the prototype of String or Date or whatever
				// (as
				// they often do)
				// This uses a bit more memory and in theory slows down
				// script
				// startup but in practice it is
				// a drop in the bucket.

				ScriptableObject scope = cx.initStandardObjects();
				// Lazy first-time init of the node version.
				registry.loadRoot(cx);
				
				try {
					initGlobals(cx);

					String triremeMain;
					triremeMain = IOUtils.toString(trireme.class
							.getResourceAsStream("trireme.js"));
					main = (Function) cx.evaluateString(scope, triremeMain,
							"<cmd>", 1, null);
					return scope;
				} catch (IOException e) {
					throw new RuntimeException(e);
				} catch (NodeException e) {
					throw new RuntimeException(e);
				}

				
			}

		});
	}

	protected void initGlobals(Context cx) throws NodeException {
		try {
			// Need to bootstrap the "native module" before we can do anything
			NativeModule.NativeImpl nativeMod = (NativeModule.NativeImpl) initializeModule(
					NativeModule.MODULE_NAME,
					AbstractModuleRegistry.ModuleType.PUBLIC, cx, scope);
			this.nativeModule = nativeMod;
			NativeModule.ModuleImpl nativeModMod = NativeModule.ModuleImpl
					.newModule(cx, scope, NativeModule.MODULE_NAME,
							NativeModule.MODULE_NAME);
			nativeModMod.setLoaded(true);
			nativeModMod.setExports(nativeMod);
			cacheModule(NativeModule.MODULE_NAME, nativeModMod);

			// Next we need "process" which takes a bit more care
			process = (Process.ProcessImpl) require(Process.MODULE_NAME, cx);
			// Check if we are connected to a parent via API
			process.setConnected(false);

			// The buffer module needs special handling because of the
			// "charsWritten" variable
			buffer = (Buffer.BufferModuleImpl) require("buffer", cx);

			// Set up metrics -- defining these lets us run internal Node
			// projects.
			// Presumably in "real" node these are set up by some sort of
			// preprocessor...
			Scriptable metrics = nativeMod.internalRequire("trireme_metrics",
					cx);
			copyProp(metrics, scope, "DTRACE_NET_SERVER_CONNECTION");
			copyProp(metrics, scope, "DTRACE_NET_STREAM_END");
			copyProp(metrics, scope, "COUNTER_NET_SERVER_CONNECTION");
			copyProp(metrics, scope, "COUNTER_NET_SERVER_CONNECTION_CLOSE");
			copyProp(metrics, scope, "DTRACE_HTTP_CLIENT_REQUEST");
			copyProp(metrics, scope, "DTRACE_HTTP_CLIENT_RESPONSE");
			copyProp(metrics, scope, "DTRACE_HTTP_SERVER_REQUEST");
			copyProp(metrics, scope, "DTRACE_HTTP_SERVER_RESPONSE");
			copyProp(metrics, scope, "COUNTER_HTTP_CLIENT_REQUEST");
			copyProp(metrics, scope, "COUNTER_HTTP_CLIENT_RESPONSE");
			copyProp(metrics, scope, "COUNTER_HTTP_SERVER_REQUEST");
			copyProp(metrics, scope, "COUNTER_HTTP_SERVER_RESPONSE");

		} catch (InvocationTargetException e) {
			throw new NodeException(e);
		} catch (IllegalAccessException e) {
			throw new NodeException(e);
		} catch (InstantiationException e) {
			throw new NodeException(e);
		}
	}

	public void cacheModule(String name, NativeModule.ModuleImpl module) {
		moduleCache.put(name, module);
	}

	/**
	 * Initialize a native module implemented in Java code.
	 */
	public Object initializeModule(String modName,
			AbstractModuleRegistry.ModuleType type, Context cx, Scriptable scope)
			throws InvocationTargetException, InstantiationException,
			IllegalAccessException {
		NodeModule mod;
		switch (type) {
		case PUBLIC:
			mod = registry.get(modName);
			break;
		case INTERNAL:
			mod = registry.getInternal(modName);
			break;
		case NATIVE:
			mod = registry.getNative(modName);
			break;
		default:
			throw new AssertionError();
		}
		if (mod == null) {
			return null;
		}
		Object exp = mod.registerExports(cx, scope, null);
		if (exp == null) {
			throw new AssertionError("Module " + modName
					+ " returned a null export");
		}
		return exp;
	}

	@Override
	public NodeEnvironment getEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeScript getScriptObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sandbox getSandbox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object require(String modName, Context cx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unPin() {
		// TODO Auto-generated method stub

	}

	@Override
	public ExecutorService getAsyncPool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExecutorService getUnboundedPool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enqueueTask(ScriptTask task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enqueueTask(ScriptTask taskm, Scriptable domain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enqueueCallback(Function f, Scriptable scope,
			Scriptable thisObj, Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enqueueCallback(Function f, Scriptable scope,
			Scriptable thisObj, Scriptable domain, Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Scriptable getDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File translatePath(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String reverseTranslatePath(String path) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerCloseable(Closeable c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterCloseable(Closeable c) {
		// TODO Auto-generated method stub

	}

	@Override
	public Selector getSelector() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Method findMethod(Class<?> c, String name) {
		Method[] ms = c.getDeclaredMethods();
		for (Method each : ms) {
			String methodName = each.getName();
			if (name.equals(methodName)) {
				return each;
			}
		}
		return null;
	}

	public static Object callMethod(Class<?> c, Object instance,
			String methodName, Object[] args) {

		Method method = findMethod(c, methodName);
		method.setAccessible(true);
		try {
			if (Modifier.isStatic(method.getModifiers())) {
				return method.invoke(c, args);
			} else {
				return method.invoke(instance, args);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static void setField(Class<?> cls, String name, Object instance,
			Object value) {
		try {
			Field f = cls.getDeclaredField(name);
			f.setAccessible(true);
			f.set(instance, value);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
    private static void copyProp(Scriptable src, Scriptable dest, String name)
    {
        dest.put(name, dest, src.get(name, src));
    }
}
