package trireme_test.mymodule;

import io.apigee.trireme.core.NodeScriptModule;

public class MyNodeScriptModule implements NodeScriptModule {

	@Override
	public String[][] getScriptSources() {
			String[] sourceDefine = new String[]{"myjsModule","/org/mozilla/javascript/gen/myjsModule_1.js"};
			return new String[][]{sourceDefine};
	}

}
