var http = require('http')


var server = http.createServer(function(req, res) {
	var hello = require('hello-world');
hello.hello('liuliu');
	if (req.method != 'POST')
		return res.end('send me a POST\n')

	req.pipe(map(function(chunk) {
		return chunk.toString().toUpperCase()
	})).pipe(res)
})

server.listen(Number(process.argv[2]))
