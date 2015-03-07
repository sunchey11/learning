debugger;
var http = require('http');
var callback = function (req, res) {
  debugger;
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end('Hello World\n');
};
var s = http.createServer(callback);
s.listen(1337, '127.0.0.1');
console.log('Server running at http://127.0.0.1:1337/');