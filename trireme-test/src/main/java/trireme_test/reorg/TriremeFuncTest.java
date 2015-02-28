package trireme_test.reorg;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.Sandbox;
import io.apigee.trireme.core.internal.AbstractModuleRegistry;
import io.apigee.trireme.core.internal.ScriptRunner;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;
import trireme_test.debug.TriremeDebug2;

public class TriremeFuncTest extends TestCase {
	public void testBasic() throws NodeException {

	}

	public void testBasic2() throws NodeException, IOException {
		NodeEnvironment env = new NodeEnvironment();
		// File file = new File("hellotest.js");
		File file = new File("myGreetingModuleTest.js");

		String[] args = new String[] { "2000" };
		NodeScript script = env.createScript("ttt", file, args);
		AbstractModuleRegistry registry = (AbstractModuleRegistry) TriremeDebug2
				.callMethod(NodeScript.class, script, "getRegistry",
						new Object[] {});
		Sandbox sandbox = new Sandbox();
		sandbox.setWorkingDirectory("vfs://");
		ScriptRunner runner = new ScriptRunner(script, env, sandbox, file, args) {
			@Override
			public File translatePath(String path) {
				if (path.startsWith("aaaa")) {
					return new File("myGreetingModule.js");
				}
				return super.translatePath(path);
			}
		};
		runner.setRegistry(registry);

		runner.setParentProcess(null);

		runner.call();

	}

}
