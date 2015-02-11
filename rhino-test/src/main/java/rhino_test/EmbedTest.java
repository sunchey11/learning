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

import java.util.Arrays;

import junit.framework.TestCase;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.ScriptableObject;

/**
 * ScopeTest
 * 
 *
 */
public class EmbedTest extends TestCase {
	private Context cx;
	ScriptableObject scope;

	@Override
	protected void setUp() throws Exception {
		cx = Context.enter();
		scope = cx.initStandardObjects();

		// String s = "var greeting='greeting in parent scope'";
		// cx.evaluateString(scope, s, "<cmd>", 1, null);

		super.setUp();
	}

	public void testScriptableObject() {
		Student student = new Student();
		student.setAddresses(Arrays.asList("a1", "a5", "a4", "a3", "a2"));
		student.setColors(new String[] { "a1", "a5", "a4", "a3", "a2" });
		scope.defineProperty("student", student, ScriptableObject.DONTENUM);
		String s = "student.age";
		Object age = cx.evaluateString(scope, s, "<cmd>", 1, null);

		s = "student.colors[2]";
		Object colors = cx.evaluateString(scope, s, "<cmd>", 1, null);
		System.out.println(colors);

		s = "student.mate";
		Object stu = cx.evaluateString(scope, s, "<cmd>", 1, null);

		s = "new java.rhino_test.Student()";
		Object stu1 = cx.evaluateString(scope, s, "<cmd>", 1, null);
		System.out.println(stu1);
		// s = "student.addresses[2]";
		// Object address = cx.evaluateString(scope, s, "<cmd>", 1, null);
		// System.out.println(age);

		// scope.defineFunctionProperties(names, clazz, attributes);
	}

	public void testJsCallJava() {
		StudentMgr mgr = new StudentMgr();
		
		scope.defineProperty("mgr", Context.javaToJS(mgr, scope),
				ScriptableObject.DONTENUM);
		
		String s = "mgr.createStudent(3,{a:'aa',x:55},[2,3],{age:123})";
		Object age = cx.evaluateString(scope, s, "<cmd>", 1, null);
		assertEquals(NativeJavaObject.class, age.getClass());
	}

	@Override
	protected void tearDown() throws Exception {
		Context.exit();
		super.tearDown();
	}

}
