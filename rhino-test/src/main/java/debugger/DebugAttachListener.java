package debugger;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.debug.Debugger;
import org.mozilla.javascript.tools.debugger.Dim.ContextData;

public class DebugAttachListener implements ContextFactory.Listener {

	public void contextCreated(Context cx) {
		 ContextData contextData = new ContextData();
         Debugger debugger = new MyDebugger();;
         cx.setDebugger(debugger, contextData);
         cx.setGeneratingDebug(true);
         cx.setOptimizationLevel(-1);		
	}

	public void contextReleased(Context cx) {
		// TODO Auto-generated method stub
		
	}

}
