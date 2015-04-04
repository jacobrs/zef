/*jslint node: true */
'use strict';

var express = require('express');
var mongo = require('mongoose');

var router = express.Router();
var bodyParser = require('body-parser');

// Connect to mongo db
mongo.connect('mongodb://localhost/oop');

var Account = require('../../models/Account');
var Picture = require('../../models/Picture');

var authController = require('./auth');

router.use(bodyParser.urlencoded({ extended: true }));
router.use( bodyParser.json() ); // to support JSON-encoded bodies


router.route('/accounts')
  .put(authController.isAuthenticated, function(req, res, next) {  //CREATE ACCOUNT
    // IN:  username, password
    // OUT: token, ERRORS

    res.contentType('application/json');
    // Sample create
    var username = req.body.username;
    var password = req.body.password;
    var email = req.body.email;

    var builder = {};
    builder.error = {};
    if( username && password && email){

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
              var account = new Account({ username: username, password: req.body.password });

              // Save this account to mongo
              account.save(function (err) {
                if (err)
                  console.log(err);
                else
                {
                  res.json(account); // Send confirmation of creation 
                }
              });

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

  })
  .post(authController.isAuthenticated, function(req, res, next) {  //LOGIN ACCOUNT
    // IN:  username, password
    // OUT: token, ERRORS

    var username = req.body.username;
    var password = req.body.password;

    if( username && password ){

      var account = new Account({ username: username, password: req.body.password });

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


router.route('/pictures')
  .get(authController.isAuthenticated, function(req, res, next){  // GET LIST OF PICS
    var builder = {};

    console.log(req.user._id); // THIS IS USER ID, DO COOL THINGS

    Picture.find({}, 'name _id', function(err, result){
      if ( err || !result ) { 
        builder.error = err;
        res.json(builder.error);
      }else{
        console.log(result);
        res.json(result);
      }
    });
  })
  .put(authController.isAuthenticated, function(req, res, next){  // CREATE NEW PIC
    var  builder = {};
    var picJSON = req.body.picJSON;
    var id = req.body.id;  // USER ID, SHOULD MATCH TOKEN
    var shared = false;

    if (req.body.is_shared){
      shared = true;
    }

    if( picJSON ){
      var picture = new Picture({ account_id: id, name: picJSON.name, encoded_string: picJSON, is_shared: shared });
      console.log(picture);
      picture.save(function (err) {
        if (err){
          console.log(err);
        }
        else{    
          res.json(picture); // Send confirmation of creation
        }
      });

    }
    else {
      res.send("invalid request");
    }
  
  });

router.route('/pictures/:pic_id')
  .get(authController.isAuthenticated, function(req, res, next){  // GET LIST OF PICS
    // IN:  token_id, pic_id
    // OUT: invalid token/pic_id, picJSON
    var pic_id = req.params.pic_id;
    var builder = {};

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
  })
  .delete(authController.isAuthenticated, function(req, res, next){  //DELETE PICTURE
    // IN:  token_id, pic_id
    // OUT: invalid token, invalid id, confirmation
    var pic_id = req.params.pic_id;
    var builder = {};

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
