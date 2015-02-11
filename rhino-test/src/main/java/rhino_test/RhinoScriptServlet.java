package rhino_test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import rhino_test.DynamicScopes.MyFactory;

public class RhinoScriptServlet extends HttpServlet {

	public static boolean useDynamicScope = false;

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
	public static String GLOBLE_SCRIPT;
	public static String THREAD_SCRIPT;
	private static final long serialVersionUID = 1L;
	private ScriptableObject globleScope = null;
	

	@Override
	public void init() throws ServletException {
		super.init();
		Context cx = Context.enter();
		try {
			globleScope = cx.initStandardObjects();
			
			cx.evaluateString(globleScope, GLOBLE_SCRIPT, "<cmd>", 1, null);
			globleScope.sealObject();
		} finally {
			Context.exit();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		Context cx = Context.enter();
		try {
			
			Scriptable threadScope = cx.newObject(globleScope);
			threadScope.setPrototype(globleScope);
			threadScope.setParentScope(null);
			//取得的是gloableScope中的greeting值
			Object result = cx.evaluateString(threadScope, THREAD_SCRIPT, "<cmd>", 1, null);
			System.err.println(Context.toString(result));
			
			result = cx.evaluateString(threadScope, "greeting", "<cmd>", 1, null);
			System.err.println(Context.toString(result));
		} finally {
			Context.exit();
		}
	}

}