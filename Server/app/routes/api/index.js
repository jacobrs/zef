/*jslint node: true */
'use strict';

var express = require('express');
var mongo = require('mongoose');
var jwt = require('jsonwebtoken');

var router = express.Router();

// Connect to mongo db
mongo.connect('mongodb://localhost/oop');

var Account = require('../../models/Account');
var Picture = require('../../models/Picture');
// var Picture = mongo.model('pictures', { account_id: String, name: String, encoded_string: Object, is_shared: Boolean }); //Picture id is _id

// IN:  username, password
// OUT: token, ERRORS
router.all('/accounts/create', function(req, res, next){
  res.contentType('application/json');
  // Sample create
  var username = req.query.username;
  var password = req.query.password;
  var email = req.query.email;

  var builder = {};
  builder.error = {};
  if( username && password && email){
    // Sample get
    // http://localhost:8080/api/accounts/create?username=Shane&password=okay&email=jacob.gagne@yahoo.ca

    Account.findOne( { username: username }, function(err, result){
      //Account doesn't exist, create and save new one.
      if ( err ){
        console.log(err);
      }
      else if ( !result ){

        Account.findOne( { email: email }, function(err, result){

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

            res.json(account); // Send confirmation of creation 
          }else{
            builder.email = "exists";
            res.json(builder);
          }
        });

      }else{
        builder.error.username = "exists";
        res.json(builder);
      }
    });
  }
  else{
    builder.error.parameters = "missing";
    res.send(builder);
  }

});

// IN:  username, password
// OUT: token, ERRORS
router.all('/accounts/login', function(req, res, next){
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

// IN:  token_id
// OUT: invalid token, list of pic names+ids
router.all('/pictures/list', function(req, res, next){
  var builder = {};

  // TODO Validate TOKEN, get user id for query

  Picture.find({}, 'name _id', function(err, result){
    if ( err || !result ) { 
      builder.error = err;
      res.json(builder.error);
    }else{
      res.json(result);
    }
  });
  
});

// IN:  token_id, pic_id
// OUT: invalid token/pic_id, picJSON
router.all('/pictures/get', function(req, res, next){
  var pic_id = req.query.pic_id;
  var builder = {};

  // TODO Validate TOKEN

  if( pic_id ){
    Picture.find({ _id: pic_id}, '', function(err, result){
      if ( err || !result ) { 
        builder.error = err;
        res.json(builder.error);
      }else{
        res.json(result);
      }
    });
  }
  else
  {
    res.send("ERROR: Invalid GET request");
  }

});

// IN:  token_id, picJSON
// OUT: invalid token, picJSON
router.all('/pictures/create', function(req, res, next){
  var  builder = {};
  var picJSON = req.query.picJSON;
  var id = 10230213123;  // USER ID, SHOULD MATCH TOKEN
  var shared = false;

  // TODO: validate token, get id of user

  if (req.query.is_shared){
    shared = true;
  }

  if( picJSON ){
    var picture = new Picture({ account_id: id, name: picJSON.name, encoded_string: picJSON, is_shared: shared });
    picture.save(function (err) {
      if (err)
        console.log(err);
    });

    res.json(picture); // Send confirmation of creation
  }
  else {
    res.send("invalid request");
  }
  
});

// IN:  token_id, pic_id
// OUT: invalid token, invalid id, confirmation
router.all('/pictures/delete', function(req, res, next){
  var pic_id = req.query.pic_id;
  var builder = {};

  // TODO Validate TOKEN
  
  if( pic_id ){
    Picture.remove( { _id: picId }, function(err){
      if (!err){
        res.send("niceee"); // How are we sending good responses?
      }
      else{
        builder.error = err;
        res.send(builder.error);
      }
    });
  }
  else
  {
    res.send("ERROR: Invalid GET request");
  }

});

router.all('/', function(req, res, next){
    res.send("Welcome to Zef, soon to be on the High tek Interwebs");
});

router.all('*', function(req, res, next){
    res.send("404");
});

module.exports = router;
