function func1(p){
	var x = "xxxx";
	java.lang.System.out.println(x);
	var func2 = function(p){
		var y = "yyy";
		java.lang.System.out.println(y);
	}
	func2("call func2");
}
new func1("call func1");


