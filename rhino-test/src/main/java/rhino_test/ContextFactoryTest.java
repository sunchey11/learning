package rhino_test;

import junit.framework.TestCase;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

public class ContextFactoryTest extends TestCase {
	public void testBasicUsage() {
		ContextFactory cf = new ContextFactory();
		Object r = cf.call(new ContextAction() {

			public Object run(Context cx) {
				Scriptable scope = cx.initStandardObjects();
				String script = "var greeting='abc';greeting;";
				Script scriptObj = cx.compileString(script, "<cmd>", 1,
						null);
				Object r = scriptObj.exec(cx, scope);
				return r;
			}
		});
		String result = (String) r;
		assertEquals("abc", result);
	}
}
