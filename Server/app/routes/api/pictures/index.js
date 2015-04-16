/*jslint node: true */
'use strict';

var express = require('express');
var mongo = require('mongoose');

var router = express.Router();
var bodyParser = require('body-parser');

var Picture = require('../../../models/Picture');
var Account = require('../../../models/Account');

var authController = require('../auth');

// PUBLIC API FOR SITE!
router.route('/public')
  .get(function(req, res, next){  // GET LIST OF PICS
    var builder = {};
    builder.status = "failed";

    Picture.find({is_shared: true}, 'name _id', function(err, result){
      if ( err || !result ) { 
        builder.message = err;
        res.json(builder);
      }else{
        builder.status = "success";
        builder.response = result;
        res.json(builder);
      }
    });
  });
router.route('/public/:pic_id')
  .get(function(req, res, next){  // GET LIST OF PICS
    var builder = {};
    builder.status = "failed";
    Picture.findOne({is_shared: true, _id: req.params.pic_id}, 'name _id', function(err, result){
      if ( err || !result ) { 
        builder.message = err;
        res.json(builder);
      }else{
        builder.status = "success";
        builder.picture = result;
        builder.account = "";
        Account.findOne({_id:result.account_id}, 'username email', function(e,r){
          builder.account = r;
          res.json(builder);  
        });
      }
    });

  });

// PRIVATE

router.route('/')
  //localhost:8080/api/pictures?apikey=test
  .get(authController.isAuthenticated, function(req, res, next){  // GET LIST OF PICS
    var builder = {};
    builder.status = "failed";
    console.log(req.user); // THIS IS USER ID, DO COOL THINGS

    Picture.find({account_id: req.user._id}, '', function(err, result){
      if ( err || !result ) {
        builder.message = err;

        res.json(builder);
      }else{
        builder.status = "success";
        builder.response = result;
        res.json(builder);
      }
    });
  })
  .put(authController.isAuthenticated, function(req, res, next){  // CREATE NEW PIC
    var  builder = {};
    builder.status = "failed";
    var picJSON = JSON.parse(req.body.encoded_string);
    var id = req.user._id;  // USER ID, SHOULD MATCH TOKEN
    var shared = req.body.shared || false;

    if( picJSON ){
      var picture = new Picture({ account_id: id, name: picJSON.name, encoded_string: picJSON, is_shared: shared });
      console.log(picture);
      picture.save(function (err) {
        if (err){
          builder.message = err;
          console.log(err);
          res.json(builder);
        }
        else{
          builder.status = "success";
          builder.response = picture;
          res.json(builder); // Send confirmation of creation
        }
      });

    }
    else {
      builder.message = "Invalid request";
      res.json(builder);
    }
  });

//localhost:8080/api/pictures/552029fd6434b60129f3e266?apikey=test
router.route('/:pic_id')
  .get(authController.isAuthenticated, function(req, res, next){  // GET LIST OF PICS
    // IN:  token_id, pic_id
    // OUT: invalid token/pic_id, picJSON
    var pic_id = req.params.pic_id;
    var builder = {};
    builder.status = "failed";
    console.log(pic_id);
    if( pic_id ){
      Picture.findOne({_id:pic_id}, function(err, result){
        if ( err || !result ) { 
          builder.message = err;
          res.json(builder);
        }else{
          builder.status = "success";
          builder.response.picture = result;
          builder.response.account = "";
          Account.findOne({_id:result.account_id}, 'username email', function(e,r){
            builder.response.account = r;
          });
          res.json(builder);  
        }
      });
    }
    else
    {
      builder.message = "Invalid request";
      res.json(builder);
    }
  })
  .delete(authController.isAuthenticated, function(req, res, next){  //DELETE PICTURE
    // IN:  token_id, pic_id
    // OUT: invalid token, invalid id, confirmation
    var pic_id = req.params.pic_id;
    var builder = {};
    builder.status = "failed";
    if( pic_id ){
      Picture.remove( { _id: pic_id }, function(err, result){
        if (!err && result.result.n >0){
          console.log(result);
          builder.status = "success";
          res.json(builder); // How are we sending good responses?
        }
        else{
          builder.message = "Could not find pic";
          res.send(builder);
        }
      });
    }
    else
    {
      builder.message = "Invalid request";
      res.json(builder);
    }
  });




module.exports = router;
