package trireme_test.mymodule;

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

		ScriptStatus status = script.execute().get();
		
	}

	public void testMyjsModule() throws NodeException, InterruptedException,
			ExecutionException, FileNotFoundException {
		NodeEnvironment env = new NodeEnvironment();

		NodeScript script = env.createScript("myjsModuleTest.js", new File(
				"myjsModuleTest.js"), new String[] { "2000" });

		ScriptStatus status = script.execute().get();
		System.exit(status.getExitCode());
	}

	public void testTimeCost() throws NodeException, InterruptedException,
			ExecutionException, FileNotFoundException {
		NodeEnvironment env = new NodeEnvironment();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			NodeScript script = env.createScript("hellotest", "console.log('aa')", new String[0] );
			ScriptStatus status = script.execute().get();
			ScriptRunner runner = script._getRuntime();
			
			ScriptTask task = new ScriptTask() {
				
				@Override
				public void execute(Context cx, Scriptable scope) {
					cx.evaluateString(scope, "console.log('aa')", "cmd", 1, null);
					
				}
			};
			runner.enqueueTask(task);
			
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000.0);

//		System.exit(0);
	}
}
