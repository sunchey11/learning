package anders.trireme_test;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.ScriptStatus;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws NodeException,
			InterruptedException, ExecutionException {
		// The NodeEnvironment controls the environment for many scripts
		NodeEnvironment env = new NodeEnvironment();

		// Pass in the script file name, a File pointing to the actual script,
		// and an Object[] containg "argv"
		NodeScript script = env.createScript("learnyounode.js", new File(
				"learnyounode.js"), new String[]{"2000"});

		// Wait for the script to complete
		ScriptStatus status = script.execute().get();

		// Check the exit code
		System.exit(status.getExitCode());
	}
}
