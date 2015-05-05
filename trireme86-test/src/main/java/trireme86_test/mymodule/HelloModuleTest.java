package trireme86_test.mymodule;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.ScriptStatus;
import io.apigee.trireme.core.ScriptTask;
import io.apigee.trireme.core.internal.ScriptRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import junit.framework.TestCase;

public class HelloModuleTest extends TestCase {
	public void testHello() throws NodeException, InterruptedException,
			ExecutionException, FileNotFoundException {
		NodeEnvironment env = new NodeEnvironment();

		NodeScript script = env.createScript("hellotest.js", new File(
				"hellotest.js"), new String[] { "2000" });
//		script.setDebugger(true);
		ScriptStatus status = script.execute().get();
		
	}

	public void testMyjsModule() throws NodeException, InterruptedException,
			ExecutionException, FileNotFoundException {
		NodeEnvironment env = new NodeEnvironment();

		NodeScript script = env.createScript("myjsModuleTest.js", new File(
				"myjsModuleTest.js"), new String[] { "2000" });

		ScriptStatus status = script.execute().get();
	}

	
}
