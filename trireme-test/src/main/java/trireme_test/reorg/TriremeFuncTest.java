package trireme_test.reorg;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.internal.AbstractModuleRegistry;
import io.apigee.trireme.core.internal.ScriptRunner;
import io.apigee.trireme.core.modules.Process.ProcessImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import junit.framework.TestCase;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

public class TriremeFuncTest extends TestCase {
	public void testBasic() throws NodeException {
		NodeEnvironment env = new NodeEnvironment();
		// callMethod(NodeEnvironment.class, env, "initialize", new Object[]
		// {});

		final String pathname = "hellotest.js";
		File file = new File(pathname);
		String[] args = new String[] { "2000" };
		NodeScript script = env.createScript(pathname, file, args);
		final AbstractModuleRegistry registry = (AbstractModuleRegistry) callMethod(
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

		Object result = contextFactory.call(new ContextAction() {

			public Object run(Context cx) {
				cx.putThreadLocal(ScriptRunner.RUNNER, runner);
				
				Script mainScript = registry.getMainScript();
				Function main = (Function) mainScript.exec(cx, scope);
				ProcessImpl process = runner.getProcess();
				try {
					Object r = main.call(cx, scope, scope,
							new Object[] { process });
					return r;
				} catch (RhinoException re) {
					throw new RuntimeException(re);
				}

			}

		});
		System.out.println(result);
	}

	public void testBasic2() throws NodeException {
		NodeEnvironment env = new NodeEnvironment();
		File file = new File("hellotest.js");
		String[] args = new String[] { "2000" };
		NodeScript script = env.createScript("hellotest.js", file, args);
		AbstractModuleRegistry registry = (AbstractModuleRegistry) callMethod(
				NodeScript.class, script, "getRegistry", new Object[] {});
		ScriptRunner runner = new ScriptRunner(script, env, null, file, args);

		runner.setRegistry(registry);

		runner.setParentProcess(null);

		runner.call();

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
