package rhino_test;

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
 * ScopeTest 
 *
 */
public class ScopeTest {
	public static void main(String args[]) {
		// Creates and enters a Context. The Context stores information
		// about the execution environment of a script.
		Scriptable scope1;

		Context cx = Context.enter();
		try {
			// Initialize the standard objects (Object, Function, etc.)
			// This must be done before scripts can be executed. Returns
			// a scope object that we use in later calls.
			scope1 = cx.initStandardObjects();

			// Collect the arguments into a single string.
			String s = "var xInScope1='inScope1'";
			// for (int i=0; i < args.length; i++) {
			// s += args[i];
			// }

			// Now evaluate the string we've colected.
			Object result = cx.evaluateString(scope1, s, "<cmd>", 1, null);

			// Convert the result to a string and print it.
			System.err.println(Context.toString(result));

		} finally {
			// Exit from the context.
			Context.exit();
		}
		Scriptable parentScope = newScope(scope1, "parentScope");
		Scriptable prototype = newScope(scope1, "prototype");
		Scriptable prop = newScope(scope1, "prop");
		Scriptable scope4 = newScope(scope1, null);
		scope4.setParentScope(parentScope);
		scope4.setPrototype(prototype);
		scope4.put("prop", scope4, prop);
		testScope(scope4);
		debug(scope1);
		debug(scope4);
		
	}

	private static void testScope(Scriptable scope) {
		Context cx = Context.enter();

		String source = "java.lang.System.out.println(greeting)\n"
				+ "java.lang.System.out.println(prop.greeting)\n"
				+ "java.lang.System.out.println(prop.prop)\n"
				+ "whereAmI='whereAmI'";
		cx.evaluateString(scope, source, "cmd", 1, null);
		cx.evaluateString(scope, "whereAmI='user1'", "cmd", 1, null);
		Context.exit();
	}

	private static Scriptable newScope(Scriptable parentScope, String id) {
		Context cx = Context.enter();
		Scriptable theScope = cx.newObject(parentScope);
		if (id != null) {
			String source = "var " + id + " = 'valueIn" + id + "';\n"
					+ "var greeting='" + id + "'";
			cx.evaluateString(theScope, source, "cmd", 1, null);
			debug(theScope);
		}

		Context.exit();
		return theScope;
	}

	static private void debug(Scriptable scope) {
		String className = scope.getClassName();
		System.out.println("className:" + className);
		Object[] ids = scope.getIds();
		for (Object obj : ids) {
			String name = (String) obj;
			Object val = scope.get(name, scope);
			System.out.println(name + ":" + val);
		}

	}
}
