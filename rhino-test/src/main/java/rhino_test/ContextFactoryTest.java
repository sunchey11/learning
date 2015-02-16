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
	
	/**
	 * 测试下ContextFactory.Listener的功能
	 */
	public void testListener() {
		ContextFactory.Listener listener = new ContextFactory.Listener() {

			public void contextReleased(Context cx) {
				System.out.println("contextReleased:" + cx);

			}

			public void contextCreated(Context cx) {
				System.out.println("contextCreated:" + cx);

			}
		};
		
		ContextFactory cf = new ContextFactory();
		cf.addListener(listener);
		//listener只有在ContextFactory.call调用时有效,Context.enter()无效
		cf.call(new ContextAction() {
			
			public Object run(Context cx) {
				System.out.println("action:"+cx);
				return null;
			}
		});
		
		
	}
}
