'use strict';

var express = require('express');

var http = require('http');
var mongo = require('mongoose');

var app = express();

app.use(function(req, res, next) {
  console.log(req.method, req.url);
  next();
});

app.use('/api', function(req, res, next){
	res.send("NICE JOB");
});

console.log('Starting a server on port 8080');

var server = http.createServer(app).listen(8080);
