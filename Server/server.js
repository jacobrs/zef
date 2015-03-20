'use strict';

var express = require('express');

var http = require('http');
var mongo = require('mongoose');

var app = express();

// Connect to mongo db
mongo.connect('mongodb://localhost/oop');

var Account = mongo.model('accounts', { username: String, password: String }); // Account id is _id
var Picture = mongo.model('pictures', { account_id: Number, encoded_string: String }); //Picture id is _id

// Log all reqs to console (terminal)
app.use(function(req, res, next) {
  console.log(req.method, req.url);
  next();
});

app.use('/api/accounts/create', function(req, res, next){
  res.contentType('application/json');
  // Sample create
  var username = req.query.username;
  var password = req.query.password;

  if( username && password ){
    // Sample get
    // http://localhost:8080/api/accounts/create?username=Shane&password=okay

    var builder = {};
    builder["status"] = "failed";
    Account.findOne( { username: username }, function(err, result){
      //Account doesn't exist, create and save new one.
      if ( err ){
        console.log(err);
      }
      else if ( !result ){
        var account = new Account({ username: username, password: req.query.password });

        // Save this account to mongo
        account.save(function (err) {
          if (err)
            console.log(err);
        });

        builder.status = "success";
        res.json(builder); // Send confirmation of creation 
      }else{
        builder.status = "exists";
        res.json(builder);
      }
    });
  }
  else{
    res.send("ERROR: Invalid GET request");
  }

});

app.use('/api/accounts/login', function(req, res, next){
  // Sample create
  var username = req.query.username;
  var password = req.query.password;

  if( username && password ){
    // Sample get
    // http://localhost:8080/api/accounts/create?username=Shane&password=%22okay%22

    var account = new Account({ username: username, password: req.query.password });

    // Find the account that the user wants
    Account.findOne( { username: username, password: password }, function(err, result){
      if ( err || !result ) { 
        res.send("Invalid creds");
      }else{
        res.send(result);  
      }
    });
  }
  else{
    res.send("ERROR: Invalid GET request");
  }

});

app.use('/api', function(req, res, next){
    res.send("Welcome to Zef, soon to be on the High tek Interwebs");
});

app.use('*', function(req, res, next){
    res.send("404");
});

console.log('Starting a server on port 8080');

var server = http.createServer(app).listen(8080);
