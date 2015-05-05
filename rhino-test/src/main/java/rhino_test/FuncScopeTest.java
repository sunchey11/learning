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

import junit.framework.TestCase;

import org.mozilla.javascript.*;

/**
 * ScopeTest
 * 
 *
 */
public class FuncScopeTest extends TestCase {
	private Context cx;
	Scriptable parentScope;
	Scriptable prototype;
	Scriptable sampleScope;

	static boolean useDynamicScope = true;

	static class MyFactory extends ContextFactory {
		@Override
		protected boolean hasFeature(Context cx, int featureIndex) {
			if (featureIndex == Context.FEATURE_DYNAMIC_SCOPE) {
				return useDynamicScope;
			}
			return super.hasFeature(cx, featureIndex);
		}
	}

	static {
		ContextFactory.initGlobal(new MyFactory());
	}

	@Override
	protected void setUp() throws Exception {
		cx = Context.enter();
		parentScope = cx.initStandardObjects();

		String s = "var greeting='greeting in parent scope'";
		cx.evaluateString(parentScope, s, "<cmd>", 1, null);
		s = "var varInParentScope ='only in parentScope'";
		cx.evaluateString(parentScope, s, "<cmd>", 1, null);

		prototype = cx.newObject(parentScope);
		s = "var greeting='greeting in prototype scope'";
		cx.evaluateString(prototype, s, "<cmd>", 1, null);
		s = "var varInPrototypeScope ='only in prototypeScope'";
		cx.evaluateString(prototype, s, "<cmd>", 1, null);

		sampleScope = cx.newObject(parentScope);
		sampleScope.setParentScope(parentScope);
		sampleScope.setPrototype(prototype);

		ScriptableObject.putProperty(sampleScope, "userName", "rhino");

		super.setUp();
	}

	public void testBasic() {
		StringBuffer script = new StringBuffer();
		script.append("var greeting='no use'\n");
		script.append("function func1(){\n");
		script.append("    bbb='222';\n");
		script.append("    java.lang.System.out.println(greeting)\n");
		script.append("}");
		script.append("func1()"
				+ "\n");

		// sampleScope.put("greeting", sampleScope,"no xxx");
		cx.evaluateString(sampleScope, script.toString(), "<cmd>", 1, null);
		Function f = (Function) sampleScope.get("func1", sampleScope);
		Object functionArgs[] = {};
		Scriptable scope = cx.newObject(parentScope);
		scope.setParentScope(sampleScope);
		Scriptable thisScope = cx.newObject(parentScope);
		thisScope.setParentScope(null);

		Object object = sampleScope.get("bbb", sampleScope);
		Object object2 = parentScope.get("bbb", parentScope);
		Object object3 = prototype.get("bbb", prototype);
		Object xxx = f.get("bbb", f);
		
		f.call(cx, parentScope, thisScope, functionArgs);
		
		Object yyy = f.get("bbb", f);
		
		Object object4 = sampleScope.get("bbb", sampleScope);
		Object object5 = parentScope.get("bbb", parentScope);
		Object object6 = prototype.get("bbb", prototype);
		System.out.println("xxx");
	}

	@Override
	protected void tearDown() throws Exception {
		Context.exit();
		super.tearDown();
	}

}
