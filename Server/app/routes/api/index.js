/*jslint node: true */
'use strict';

var express = require('express');
var mongo = require('mongoose');

var router = express.Router();
var bodyParser = require('body-parser');
var passport = require('passport');
var LocalStrategy = require('passport-localapikey').Strategy;
var Account = require('../../models/Account');

// Connect to mongo db
mongo.connect('mongodb://ec2-user@ec2-52-4-224-221.compute-1.amazonaws.com/oop');

var picturesAPI = require('./pictures');
var accountsAPI = require('./accounts');

function findByApiKey(apikey, fn) {
  var users = [];
  Account.find({}, function(err,result){
    users = result;
    console.log(users);
    for (var i = 0, len = users.length; i < len; i++) {
      var user = users[i];
      console.log(user.apikey + " " + apikey);
      if (user.apikey === apikey) {
        return fn(null, user);
      }
    }
    return fn(null, null);
  });
  // console.log(users);
  
}

passport.use(new LocalStrategy(
  function(apikey, done) {
    // asynchronous verification, for effect...
    process.nextTick(function () {
      
      // Find the user by username.  If there is no user with the given
      // username, or the password is not correct, set the user to `false` to
      // indicate failure and set a flash message.  Otherwise, return the
      // authenticated `user`.
      findByApiKey(apikey, function(err, user) {
        if (err) { return done(err); }
        if (!user) { return done(null, false, { message: 'Unknown apikey : ' + apikey }); }
       // if (user.password != password) { return done(null, false, { message: 'Invalid password' }); }
        return done(null, user);
      })
    });
  }
));

passport.serializeUser(function(user, done) {
  done(null, user.id);
});

passport.deserializeUser(function(id, done) {
  findById(id, function (err, user) {
    done(err, user);
  });
});

router.use(bodyParser.urlencoded({ extended: true }));
router.use( bodyParser.json() ); // to support JSON-encoded bodies

router.use('/accounts', accountsAPI);
router.use('/pictures', picturesAPI);

router.use('/authenticate', 
  passport.authenticate('localapikey', { failureRedirect: '/api/unauthorized' }),
  function(req, res) {
     res.json({ message: "Authenticated" });
  });

router.all('/', function(req, res, next){
    res.send("Welcome to Zef, soon to be on the High tek Interwebs");
});

router.all('*', function(req, res, next){
    res.send("404");
});

module.exports = router;
