var globleVar = 'uudd';
function SampleDept(name,address){
	this.deptName = name;
	this.address = address;
}
SampleDept.prototype.addPeople=function(userName){
	var temp = 'xyz';
	this.userName = userName;
};
var dept = new SampleDept('yan fa bu','shanghai');
dept.addPeople('liuliu');

