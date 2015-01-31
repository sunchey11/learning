package anders;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

public class InterceptorTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Context cx = Context.enter();
		try {
			ScriptableObject scope = cx.initStandardObjects();
			Reader in = new FileReader("f1.js");
			ScriptableObjectDelegator delegator = new ScriptableObjectDelegator(
					scope);
			cx.evaluateReader(delegator, in, "cmd", 1, null);

		} finally {
			Context.exit();
		}
	}

}
