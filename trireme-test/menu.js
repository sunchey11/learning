#!/usr/bin/env node


var adventure = require('adventure');
var jsing = adventure('javascripting');




jsing.add("aaaaaa", function () { console.log("aaa") });
jsing.add("bbbbbb", function () { console.log("aaa") });

jsing.execute(process.argv.slice(2));
