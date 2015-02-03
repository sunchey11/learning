package rhino_test;

import javax.servlet.ServletException;

import junit.framework.TestCase;

public class RhinoScriptServletTest extends TestCase {
	private RhinoScriptServlet servlet;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		StringBuffer globleScript = new StringBuffer();
		globleScript.append("var greeting = 'globle'");
		globleScript.append("function func1(){return greeting};");
		RhinoScriptServlet.GLOBLE_SCRIPT = globleScript.toString();
		
		StringBuffer threadScript = new StringBuffer();
		threadScript.append("var greeting ='local';");
		threadScript.append("func1()");
		RhinoScriptServlet.THREAD_SCRIPT = threadScript.toString();
		
		servlet = new RhinoScriptServlet();
		servlet.init();
	}
	
	public void test1() throws ServletException{
		servlet.doGet(null, null);
	}
}
