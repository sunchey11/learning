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

public class TestMainEmbedded {

	public static void main(String[] args) {
		String title = "aaa";
		ContextFactory factory =  new ContextFactory();;
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
					//可以跟踪到
					cx.evaluateString(scope, "var x='a'", "<cmd>", 1, null);
					scope.put("testMe", scope, "value1234");
					//testDebugger1.js里定义了一个函数f,在testDebugger2.js里用脚本调用是可以跟踪源代码的
					Function f1 = (Function) cx.evaluateReader(scope, new FileReader("testDebugger1.js"), "testDebugger1", 1, null);
					cx.evaluateReader(scope, new FileReader("testDebugger2.js"), "testDebugger2", 1, null);
					
					
					//这个也可以跟踪
					Object r = f1.call(cx, scope, scope,
							new Object[] {"xx,bb"  });
					System.out.println(r);
					
					//换一个函数的作用域
					Scriptable newScope = cx.newObject(scope);
					newScope.put("varInNewScope", newScope, 1234567);
//					newScope.setPrototype(null);
					System.out.println(newScope.getPrototype());
					Object r2 = f1.call(cx, newScope, newScope,
							new Object[] {"xx,bb"  });
					System.out.println(r2);
					
					//编译成script以后,就不能再跟踪了
					Script script = cx.compileString("function f2(a) {debugger;return a+':xxbb';}", "<cmd>", 1, null);
					script.exec(cx, scope);
					//无法跟踪到f2调用里面了
					cx.evaluateString(scope, "var x=f2('a');java.lang.System.out.println(x)", "<cmd>", 1, null);
					
					cx.evaluateString(scope, "Packages.debugger1.Utils.hello('aaa')", "<cmd>", 1, null);
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
