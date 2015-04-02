/*jslint node: true */
'use strict';

var express = require('express');
var app = express();

var hbs = require('express-hbs');

var http = require('http');

var api = require('./app/routes/api');


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


app.set('jsonp callback name', 'callback');
app.set('json replacer', "  ");

app.engine('hbs', hbs.express3({
  partialsDir: 'app/views',
  defaultLayout: 'app/views/default.hbs'
}));
app.set('view engine', 'hbs');
app.set('views', 'app/views');

app.all('*', function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "X-Requested-With");
  next();
});

app.use(express.static('app/public'));

app.use('/api', api);

console.log('Starting a server on '+CONFIG.port);

http
  .createServer(app)
  .listen(CONFIG.port);
