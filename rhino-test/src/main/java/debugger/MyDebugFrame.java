package debugger;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.debug.DebugFrame;

public class MyDebugFrame implements DebugFrame {

	public void onEnter(Context cx, Scriptable activation, Scriptable thisObj,
			Object[] args) {
		System.out.println("onEnter");
	}

	public void onLineChange(Context cx, int lineNumber) {
		// TODO Auto-generated method stub
		System.out.println("onLineChange");
	}

	public void onExceptionThrown(Context cx, Throwable ex) {
		// TODO Auto-generated method stub
		System.out.println("onExceptionThrown");
	}

	public void onExit(Context cx, boolean byThrow, Object resultOrException) {
		// TODO Auto-generated method stub
		System.out.println("onExit");
	}

	public void onDebuggerStatement(Context cx) {
		// TODO Auto-generated method stub
		System.out.println("onDebuggerStatement");
	}

}
