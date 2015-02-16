package trireme_test.debug;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.debugger.Main;

public class TriremeDebug {

	public static void main(String[] args) {
		String title = "aaa";
		ContextFactory factory =  new ContextFactory();
		final Scriptable scope = (Scriptable) factory.call(new ContextAction() {

			public Object run(Context cx) {
				Scriptable scope = cx.initStandardObjects();
				return scope;
			}
			
		});
		Main.mainEmbedded(factory, scope, title);
		factory.call(new ContextAction() {
			
			public Object run(Context cx) {
				
				try {
					cx.evaluateString(scope, "var x='a'", "<cmd>", 1, null);
					cx.evaluateReader(scope, new FileReader("testDebugger1.js"), "testDebugger1", 1, null);
					cx.evaluateReader(scope, new FileReader("testDebugger2.js"), "testDebugger2", 1, null);
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return null;
			}
		});
	}

}
