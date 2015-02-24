package com.tt.pwp.framework.nodejs;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeModule;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.internal.AbstractModuleRegistry;
import io.apigee.trireme.core.internal.ChildModuleRegistry;
import io.apigee.trireme.core.internal.RootModuleRegistry;
import io.apigee.trireme.core.internal.ScriptRunner;
import io.apigee.trireme.core.modules.Buffer;
import io.apigee.trireme.core.modules.NativeModule;
import io.apigee.trireme.core.modules.Process;
import io.apigee.trireme.core.modules.Process.ProcessImpl;
import io.apigee.trireme.node10.main.trireme;

import java.io.File;
import java.io.IOException;
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
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class NodeEngine extends NodeRuntimeImpl {


	public Object runScript(String script) {
		return null;
	}
	
	public Object runScriptFile(final String path, final String[] args) {
		Object result = contextFactory.call(new ContextAction() {

			public Object run(Context cx) {
				cx.putThreadLocal(ScriptRunner.RUNNER, this);
				process.initializeArgv(args);
				try {

					// Function main = (Function) mainScript.exec(cx, scope);
					Object r = main.call(cx, scope, scope,
							new Object[] { process });
					return r;
				} catch (RhinoException re) {
					throw new RuntimeException(re);
				}

			}
		});
		return result;
	}

	

}
