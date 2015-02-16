package debugger;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.debug.DebugFrame;
import org.mozilla.javascript.debug.DebuggableScript;
import org.mozilla.javascript.debug.Debugger;

public class MyDebugger implements Debugger {

	public void handleCompilationDone(Context cx, DebuggableScript fnOrScript,
			String source) {
		System.out.println("handleCompilationDone");
	}

	public DebugFrame getFrame(Context cx, DebuggableScript fnOrScript) {
		System.out.println("getFrame");
		return new MyDebugFrame();
	}

}
