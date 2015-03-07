package trireme86_test.mymodule;

import io.apigee.trireme.core.NodeScriptModule;

public class MyNodeScriptModule implements NodeScriptModule {

	@Override
	public String[][] getScriptSources() {
			String[] sourceDefine = new String[]{"myjsModule","/trireme86_test/mymodule/myjsModule.js"};
			return new String[][]{sourceDefine};
	}

}
