package trireme_test.reorg;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.internal.AbstractModuleRegistry;
import io.apigee.trireme.core.internal.ScriptRunner;
import io.apigee.trireme.core.modules.ProcessWrap;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import junit.framework.TestCase;

public class TriremeFuncTest extends TestCase {
	public void testBasic() throws NodeException {
		NodeEnvironment env = new NodeEnvironment();
		// callMethod(NodeEnvironment.class, env, "initialize", new Object[]
		// {});

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
}
