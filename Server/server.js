'use strict';

var express = require('express');

var http = require('http');
var mongo = require('mongoose');

var app = express();

// Connect to mongo db
mongo.connect('mongodb://localhost/oop');

var Account = mongoose.model('accounts', { username: String, password: String }); // Account id is _id
Account.plugin(autoIncrement.plugin, 'accounts');
var Picture = mongoose.model('pictures', { account_id: Number, encoded_string: String }); //Picture id is _id

// Log all reqs to console (terminal)
app.use(function(req, res, next) {
  console.log(req.method, req.url);
  next();
});

app.use('/api', function(req, res, next){
    res.send("NICE JOB");
});

app.use('/api/accounts/create', function(req, res, next){
  // Sample create
  var account = new Account({ username: 'Shane', password: 'pass' });

  // Save this account to mongo
  account.save(function (err) {
    if (err)
      console.log(err);
  });

  res.send(account);
});

console.log('Starting a server on port 8080');

var server = http.createServer(app).listen(8080);
