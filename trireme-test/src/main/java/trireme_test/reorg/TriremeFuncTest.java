package trireme_test.reorg;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.internal.AbstractModuleRegistry;
import io.apigee.trireme.core.internal.ScriptRunner;

import java.io.File;

import junit.framework.TestCase;
import trireme_test.debug.TriremeDebug2;

public class TriremeFuncTest extends TestCase {
	public void testBasic() throws NodeException {
		
	}

	public void testBasic2() throws NodeException {
		NodeEnvironment env = new NodeEnvironment();
		File file = new File("hellotest.js");
		String[] args = new String[] { "2000" };
		NodeScript script = env.createScript("hellotest.js", file, args);
		AbstractModuleRegistry registry = (AbstractModuleRegistry) TriremeDebug2.callMethod(
				NodeScript.class, script, "getRegistry", new Object[] {});
		ScriptRunner runner = new ScriptRunner(script, env, null, file, args);

		runner.setRegistry(registry);

		runner.setParentProcess(null);

		runner.call();

	}

}
