/*jslint node: true */
'use strict';

var express = require('express');
var mongo = require('mongoose');
var shortid = require('shortid');

var router = express.Router();
var bodyParser = require('body-parser');

var Account = require('../../../models/Account');
var API_LIMIT = 1000 * 10; // ten minutes

router.use(bodyParser.urlencoded({ extended: true }));
router.use( bodyParser.json() ); // to support JSON-encoded bodies

router.route('/')
  .put(function(req, res, next) {  //CREATE ACCOUNT
    // IN:  username, password
    // OUT: token, ERRORS

    res.contentType('application/json');
    // Sample create
    console.log(req.body);
    var username = req.body.username;
    var password = req.body.password;
    var email = req.body.email;

    var builder = {};
    builder.status = "failed";
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
              var account = new Account({ username: username, password: password, email:email,  apikey: shortid.generate() });

              // Save this account to mongo
              account.save(function (err) {
                if (err){
                  builder.message = err;
                  console.log(builder);
                }
                else
                {

                  builder.status = "success";
                  builder.account = account;
                  res.json(builder); // Send confirmation of creation 
                }
              });

            }else{
              builder.message = "Email taken";
              res.json(builder);
            }
          });

        }else{
          builder.message = "Username taken";
          res.json(builder);
        }
      });
    }
    else{
      builder.message = "Missing Fields";
      res.send(builder);
    }

  })
  .post(function(req, res, next) {  //LOGIN ACCOUNT
    // IN:  username, password
    // OUT: token, ERRORS
    console.log('POST');
    var username = req.body.username;
    var password = req.body.password;

    var builder = {};
    builder.status = "failed";

    if( username && password ){

      var account = new Account({ username: username, password: req.body.password });

      // Find the account that the user wants
      Account.findOneAndUpdate( { username: username, password: password }, {$set: {apikey: shortid.generate(), accessed: new Date() }} , {new:true}, function(err, user){
        if ( err || !user ) {
          builder.message = "Authentication Failed"; 
          res.send(builder);
        }else{

          // Send the status and token back
          builder.status = "success";
          builder.token = user.apikey;
          res.json(builder);

        }
      });
    }
    else{
      builder.message = "Invalid Request, Programming Error";
      res.send(builder);
    }
  });

module.exports = router;
