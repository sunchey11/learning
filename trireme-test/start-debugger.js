var util = require('util'),
    path = require('path'),
    net = require('net'),
    vm = require('vm'),
    repl = require('repl'),
    inherits = util.inherits,
    spawn = require('child_process').spawn;
 repl.start();
 var d = require('_debugger');
 d.start('my-test-script.js');
console.log('liuliu');