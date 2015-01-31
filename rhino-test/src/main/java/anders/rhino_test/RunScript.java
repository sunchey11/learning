package anders.rhino_test;

/**
 * Hello world!
 *
 */
/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import org.mozilla.javascript.*;

/**
 * RunScript: simplest example of controlling execution of Rhino.
 * 
 * Collects its arguments from the command line, executes the script, and prints
 * the result.
 * 
 * @author Norris Boyd
 */
public class RunScript {
	public static void main(String args[]) {
		// Creates and enters a Context. The Context stores information
		// about the execution environment of a script.
		Context cx = Context.enter();
		try {
			WrapFactory wrapFactory = cx.getWrapFactory();
			// Initialize the standard objects (Object, Function, etc.)
			// This must be done before scripts can be executed. Returns
			// a scope object that we use in later calls.
			ScriptableObject scope = cx.initStandardObjects();
			
			// ScriptableObject scope = cx.initStandardObjects(null,
			// true);//调用此方法后,String.a不能改
			// scope.sealObject();//调用此方法后,x不能赋值
			// Collect the arguments into a single string.
			String s = "var x='bbb';\n" + "String.a='bbbxx'\n"
					+ "java.lang.System.out.println(String.a)";
			// for (int i=0; i < args.length; i++) {
			// s += args[i];
			// }

			// Now evaluate the string we've colected.
			Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);

			// Convert the result to a string and print it.
			System.err.println(Context.toString(result));

		} finally {
			// Exit from the context.
			Context.exit();
		}
	}
}
