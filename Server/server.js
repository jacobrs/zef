'use strict';

var express = require('express');

var http = require('http');
var mongo = require('mongoose');

var app = express();
mongo.connect('mongodb://localhost/oop');

var Accounts = mongoose.model('accounts', { account_id: String });

var kitty = new Cat({ name: 'Zildjian' });
kitty.save(function (err) {
  if (err) // ...
  console.log('meow');
});
app.use(function(req, res, next) {
  console.log(req.method, req.url);
  next();
});

app.use('/api', function(req, res, next){
	res.send("NICE JOB");
});

console.log('Starting a server on port 8080');

var server = http.createServer(app).listen(8080);
