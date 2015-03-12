package trireme86_test;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.ScriptStatus;
import io.apigee.trireme.servlet.internal.EnvironmentManager;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class HelloWorld {

	public static void main(String[] args) throws InterruptedException, ExecutionException, NodeException {
		// The NodeEnvironment controls the environment for many scripts
				NodeEnvironment env = new NodeEnvironment();
//				NodeEnvironment env = EnvironmentManager.get().getEnvironment();
				// Pass in the script file name, a File pointing to the actual script,
				// and an Object[] containg "argv"
//				String scriptName = "helloWorld.js";
				String scriptName = "myServer.js";
				NodeScript script = env.createScript(scriptName, new File(
						scriptName), new String[]{"2000"});

				// Wait for the script to complete
				ScriptStatus status = script.execute().get();

				// Check the exit code
				System.exit(status.getExitCode());

	}

}
