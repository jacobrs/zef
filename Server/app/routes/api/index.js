/*jslint node: true */
'use strict';

var express = require('express');
var mongo = require('mongoose');

var router = express.Router();
var bodyParser = require('body-parser');
var passport = require('passport')
  , LocalStrategy = require('passport-local').Strategy;
var User = require('../../models/Account');

// Connect to mongo db
mongo.connect('mongodb://ec2-user@ec2-52-4-224-221.compute-1.amazonaws.com/oop');

var picturesAPI = require('./pictures');
var accountsAPI = require('./accounts');

passport.serializeUser(function(user, done) {
  done(null, user);
});

passport.deserializeUser(function(user, done) {
  done(null, user);
});

router.use(bodyParser.urlencoded({ extended: true }));
router.use( bodyParser.json() ); // to support JSON-encoded bodies

router.use('/accounts', accountsAPI);
router.use('/pictures', picturesAPI);

router.all('*', function(req, res, next){
  passport.authenticate('local', function(err, user, info) {
    if (err) return next(err);
    if (!user) {
        return res.json(403, {
            message: "Thats wrong."
        });
    }

    // Manually establish the session...
    req.login(user, function(err) {
      if (err) return next(err);
      next();
    });

  })(req, res, next);
});

router.all('/', function(req, res, next){
    res.send("Welcome to Zef, soon to be on the High tek Interwebs");
});

router.all('*', function(req, res, next){
    res.send("404");
});

module.exports = router;
