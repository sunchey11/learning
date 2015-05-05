package debugger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.debugger.Main;

public class DebuggerInOtherThreadMain {

	public static void main(String[] args) {
		final String title = "aaa";
		final ContextFactory factory = new ContextFactory();
		final Scriptable scope = (Scriptable) factory.call(new ContextAction() {
			public Object run(Context cx) {
				Scriptable scope = cx.initStandardObjects();
				return scope;
			}
		});
		Main.mainEmbedded(factory, scope, title);
		factory.call(new ContextAction() {
			public Object run(Context cx) {
				// 放到这里就不能调试了
				// Main.mainEmbedded(factory, scope, title);
				cx.evaluateString(scope, "var x='a'", "<cmd>", 1, null);
				return scope;
			}
		});
	}

}
