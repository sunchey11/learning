//package rhino_test;
//
//import javax.servlet.ServletException;
//
//import junit.framework.TestCase;
//
//public class RhinoScriptServletSealedTest extends TestCase {
//
//	@Override
//	protected void setUp() throws Exception {
//		super.setUp();
//		
//	}
//
//	public void test1() throws ServletException {
//		RhinoScriptServlet.useDynamicScope = true;
//		
//		StringBuffer globleScript = new StringBuffer();
//		globleScript.append("var greeting = 'globle';\n");
//		globleScript.append("function changeGreeting(msg){greeting=msg;return greeting};");
//		RhinoScriptServlet.GLOBLE_SCRIPT = globleScript.toString();
//		
//		StringBuffer threadScript = new StringBuffer();
//		threadScript.append("var greeting ='local';changeGreeting('changed')");
//		RhinoScriptServlet.THREAD_SCRIPT = threadScript.toString();
//		
//		RhinoScriptServlet servlet = new RhinoScriptServlet();
//		servlet.init();
//		
//		servlet.doGet(null, null);
//	}
//	public void test2() throws ServletException {
//		
//		StringBuffer globleScript = new StringBuffer();
//		globleScript.append("var greeting = {name:'liu'};\n");
//		globleScript.append("function changeGreeting(msg){greeting.name=msg;return greeting.name};");
//		RhinoScriptServlet.GLOBLE_SCRIPT = globleScript.toString();
//		
//		StringBuffer threadScript = new StringBuffer();
//		threadScript.append("var greeting ={name:'local'};changeGreeting('changed')");
//		RhinoScriptServlet.THREAD_SCRIPT = threadScript.toString();
//		
//		RhinoScriptServlet servlet = new RhinoScriptServlet();
//		servlet.init();
//		
//		servlet.doGet(null, null);
//	}
//	
//}
