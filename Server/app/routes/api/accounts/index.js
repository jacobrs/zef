/*jslint node: true */
'use strict';

var express = require('express');
var mongo = require('mongoose');

var router = express.Router();
var bodyParser = require('body-parser');

var Account = require('../../../models/Account');

var authController = require('../auth');

router.use(bodyParser.urlencoded({ extended: true }));
router.use( bodyParser.json() ); // to support JSON-encoded bodies

router.route('/accounts')
  .put(function(req, res, next) {  //CREATE ACCOUNT
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

module.exports = router;
