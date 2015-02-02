package rhino_test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class RhinoScriptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ScriptableObject standardObjects = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Context cx = Context.enter();
		try {
			standardObjects = cx.initStandardObjects();
			String s = "function func1(){return greeting};func1";
			cx.evaluateString(standardObjects, s, "<cmd>", 1, null);
		} finally {
			Context.exit();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		Context cx = Context.enter();
		try {
			String script = "func1()";
			Scriptable threadScope = cx.newObject(standardObjects);
			threadScope.setPrototype(standardObjects);
			threadScope.setParentScope(null);
			Object result = cx.evaluateString(threadScope, script, "<cmd>", 1, null);
			System.err.println(Context.toString(result));
		} finally {
			Context.exit();
		}
	}

}