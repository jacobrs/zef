/*jslint node: true */
'use strict';

var express = require('express');
var http = require('http');
var bodyParser = require('body-parser');
var passport = require('passport');

var app = express();

var api = require('./app/routes/api');
var website = require('./app/routes/website');

process.argv.forEach(function(v){
  if(v === '--development') {
    global.PRODUCTION = false;
    global.CONFIG = require('./config/main.js').development;
  } else {
    global.PRODUCTION = true;
    global.CONFIG = require('./config/main.js').production;
  }
});


// Log all reqs to console (terminal) LETS MAKE MIDDLEWARE. Maybe log stats later for webapp?
app.use(function(req, res, next) {
  console.log(req.method, req.url);
  next();
});

// Use passport
app.use(passport.initialize());
app.use(passport.session());

app.set('jsonp callback name', 'callback');
app.set('json replacer', "  ");

app.all('*', function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "X-Requested-With");
  next();
});

app.use(express.static('app/public'));
app.use('/vendor', express.static('bower_components'));

app.use('/api', api);
app.use('/', website);


console.log('Starting a server on '+CONFIG.port);

http
  .createServer(app)
  .listen(CONFIG.port);
