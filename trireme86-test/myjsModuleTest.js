debugger
var hello = require('myjsModule');
hello.hello('yangyang');
var hello2 = require('myjsModule');
hello2.hello('yangyang');
console.log(hello2===hello)