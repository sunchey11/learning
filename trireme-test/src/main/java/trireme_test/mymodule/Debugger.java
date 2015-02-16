package trireme_test.mymodule;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.ScriptStatus;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Debugger {
	/**
	 * debug功能跑不起来
	 * @param args
	 * @throws NodeException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void main(String[] args) throws NodeException, InterruptedException, ExecutionException {
		NodeEnvironment env = new NodeEnvironment();

		// Pass in the script file name, a File pointing to the actual script,
		// and an Object[] containg "argv"
		NodeScript script = env.createScript("debugger", new File(
				"start-debugger.js"), null);

		// Wait for the script to complete
		ScriptStatus status = script.execute().get();

		// Check the exit code
		System.exit(status.getExitCode());
	}
}
