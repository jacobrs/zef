/*jslint node: true */
'use strict';

var express = require('express');
var mongo = require('mongoose');

var router = express.Router();
var bodyParser = require('body-parser');

var Picture = require('../../../models/Picture');
var Account = require('../../../models/Account');

var authController = require('../auth');

router.use(bodyParser.urlencoded({ extended: true }));
router.use( bodyParser.json() ); // to support JSON-encoded bodies

// PUBLIC API FOR SITE!
router.route('/public')
  .get(function(req, res, next){  // GET LIST OF PICS
    var builder = {};

    Picture.find({is_shared: true}, 'name _id', function(err, result){
      if ( err || !result ) { 
        builder.error = err;
        res.json(builder.error);
      }else{
        res.json(result);
      }
    });
  });
router.route('/public/:pic_id')
  .get(function(req, res, next){  // GET LIST OF PICS
    var builder = {};

    Picture.findOne({is_shared: true, _id: req.params.pic_id}, 'name _id', function(err, result){
      if ( err || !result ) { 
        builder.error = err;
        res.json(builder.error);
      }else{
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
  .get(authController.isAuthenticated, function(req, res, next){  // GET LIST OF PICS
    var builder = {};

    console.log(req.user._id); // THIS IS USER ID, DO COOL THINGS

    Picture.find({account_id: req.user._id}, '', function(err, result){
      if ( err || !result ) { 
        builder.error = err;

        res.json(builder.error);
      }else{
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

router.route('/:pic_id')
  .get(authController.isAuthenticated, function(req, res, next){  // GET LIST OF PICS
    // IN:  token_id, pic_id
    // OUT: invalid token/pic_id, picJSON
    var pic_id = req.params.pic_id;
    var builder = {};

     var pic="551f395df3c0026805737f23";
    console.log(pic_id);
    if( pic_id ){
      Picture.findOne({_id:pic_id}, function(err, result){
        if ( err || !result ) { 
          builder.error = err;
          res.json(builder.error);
        }else{
          builder.picture = result;
          builder.account = "";
          Account.findOne({_id:result.account_id}, 'username email', function(e,r){
            builder.account = r;
            res.json(builder);  
          });
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
      Picture.remove( { _id: pic_id }, function(err){
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




module.exports = router;
