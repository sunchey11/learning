package debugger;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.debugger.Main;

import junit.framework.TestCase;

public class DebuggerTest extends TestCase {
	/**
	 * debug的底层机制
	 */
	public void testDebugger() {
		ContextFactory cf = new ContextFactory();
		cf.addListener(new DebugAttachListener());
		cf.call(new ContextAction() {

			public Object run(Context cx) {
				Scriptable scope = cx.initStandardObjects();
				StringBuffer script = new StringBuffer("var greeting1='abc1';\n");
				script.append("var greeting2='abc2';\n");
				script.append("var greeting3='abc3';\n");
				script.append("var greeting4='abc4';\n");
				script.append("var greeting5='abc5';\n");
				Object result = cx.evaluateString(scope, script.toString(),
						"<cmd>", 1, null);

				return result;
			}
		});

	}
	
}
