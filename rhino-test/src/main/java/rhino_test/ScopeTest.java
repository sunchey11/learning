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
public class ScopeTest extends TestCase {
	private Context cx;
	Scriptable parentScope;
	Scriptable prototype;
	Scriptable sampleScope;

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

		sampleScope = cx.newObject(parentScope);
		sampleScope.setParentScope(parentScope);
		sampleScope.setPrototype(prototype);

		ScriptableObject.putProperty(sampleScope, "userName", "rhino");

		super.setUp();
	}

	public void test1() {

		String s = "var o = new Object();"
				+ "o.__defineGetter__('xx',function(){return 'user'});"
				+ "o.xx";
		Object v1 = cx.evaluateString(sampleScope, s, "<cmd>", 1, null);
		System.out.println(v1);
	}

	public void testScriptableObject() {
		// Scriptable.get()不会处理prototype,所以greeting的值为空
		Object v1 = sampleScope.get("greeting", sampleScope);
		assertEquals(Scriptable.NOT_FOUND, v1);

		// ScriptableObject.getProperty会处理prototype
		Object v2 = ScriptableObject.getProperty(sampleScope, "greeting");
		assertEquals("greeting in prototype scope", v2);

		// 但是ScriptableObject.getProperty不会处理parent scope
		Object v3 = ScriptableObject.getProperty(sampleScope,
				"varInParentScope");
		assertEquals(Scriptable.NOT_FOUND, v3);

		// 设置sampleScope.greeting,不会影响prototype.greeting
		ScriptableObject.putProperty(sampleScope, "greeting",
				"greeting in sample scope");
		Object v4 = sampleScope.get("greeting", sampleScope);
		assertEquals("greeting in sample scope", v4);
		Object v5 = ScriptableObject.getProperty(prototype, "greeting");
		assertEquals("greeting in prototype scope", v5);

	}

	public void testEvalVar() {
		// 取得的是prototypeScope.greeting
		String s = "greeting";
		Object v1 = cx.evaluateString(sampleScope, s, "<cmd>", 1, null);
		assertEquals("greeting in prototype scope", v1);

		// 取得的是parentScope.varInParentScope
		s = "varInParentScope";
		Object v2 = cx.evaluateString(sampleScope, s, "<cmd>", 1, null);
		assertEquals("only in parentScope", v2);
	}

	public void testInitStandardObjectsTimeCost() {
		// initStandardObjects耗费的时间是newObject的100倍,所以应该context共享
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			ScriptableObject initStandardObjects = cx.initStandardObjects();
			Object o = initStandardObjects;
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000.0);

	}

	public void testNewObjectTimeCost() {
		// initStandardObjects耗费的时间是newObject的100倍,所以应该context共享
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			Scriptable newObject = cx.newObject(parentScope);
			Object o = newObject;
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000.0);

	}

	public void testSealObject1() {
		// 调用sealObject之后,就不能改变变量值了
		Context context = Context.enter();
		try {

			ScriptableObject scope = context.initStandardObjects();
			String s = "var greeting='greeting'";
			cx.evaluateString(scope, s, "<cmd>", 1, null);
			scope.sealObject();

			s = "greeting='changed'";
			cx.evaluateString(scope, s, "<cmd>", 1, null);
		} finally {
			Context.exit();
		}

	}

	public void testSealObject2() {
		// 但是可以修改变量对象内的属性值,即使一定调用了sealObject
		Context context = Context.enter();
		try {

			ScriptableObject scope = context.initStandardObjects();
			String s = "var greeting = {name:'liu'}";
			cx.evaluateString(scope, s, "<cmd>", 1, null);
			scope.sealObject();

			s = "greeting.name='changed'";
			cx.evaluateString(scope, s, "<cmd>", 1, null);
		} finally {
			Context.exit();
		}

	}

	@Override
	protected void tearDown() throws Exception {
		Context.exit();
		super.tearDown();
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
