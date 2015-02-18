package trireme_test.debug;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.internal.AbstractModuleRegistry;
import io.apigee.trireme.core.internal.ScriptRunner;
import io.apigee.trireme.core.modules.Process.ProcessImpl;
import io.apigee.trireme.node10.main.trireme;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.tools.debugger.Main;

import trireme_test.reorg.TriremeFuncTest;

public class TriremeDebug2 {

	public static void main(String[] theArgs) throws NodeException {
		NodeEnvironment env = new NodeEnvironment();
		// callMethod(NodeEnvironment.class, env, "initialize", new Object[]
		// {});

		final String pathname = "hellotest.js";
		File file = new File(pathname);
		String[] args = new String[] { "2000" };
		NodeScript script = env.createScript(pathname, file, args);
		final AbstractModuleRegistry registry = (AbstractModuleRegistry)callMethod(
				NodeScript.class, script, "getRegistry", new Object[] {});
		final ScriptRunner runner = new ScriptRunner(script, env, null, file,
				args);

		runner.setRegistry(registry);

		runner.setParentProcess(null);

		ContextFactory contextFactory = env.getContextFactory();
		final ScriptableObject scope = (ScriptableObject) contextFactory
				.call(new ContextAction() {

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
						setField(ScriptRunner.class, "scope", runner, scope);
						// Lazy first-time init of the node version.
						registry.loadRoot(cx);
						callMethod(ScriptRunner.class, runner, "initGlobals",
								new Object[] { cx });

						callMethod(ScriptRunner.class, runner, "setArgv",
								new Object[] { pathname });
						return scope;
					}

				});
		Main.mainEmbedded(contextFactory, scope, "aaaa");
		Object result = contextFactory.call(new ContextAction() {

			public Object run(Context cx) {
				cx.putThreadLocal(ScriptRunner.RUNNER, runner);
				
				Script mainScript = registry.getMainScript();
				
				try {
					String string = IOUtils.toString(trireme.class.getResourceAsStream("trireme.js"));
					Function main = (Function) cx.evaluateString(scope, string, "<cmd>", 1, null);
//					Function main = (Function) mainScript.exec(cx, scope);
					ProcessImpl process = runner.getProcess();
					Object r = main.call(cx, scope, scope,
							new Object[] { process });
					return r;
				} catch (RhinoException re) {
					throw new RuntimeException(re);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

			}

		});
		System.out.println(result);
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
}
