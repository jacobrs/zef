// Load required packages
var passport = require('passport');
var BasicStrategy = require('passport-http').BasicStrategy;
var passport = require('passport');
var LocalStrategy = require('passport-localapikey').Strategy;

var Account = require('../../models/Account');


function findByApiKey(apikey, fn) {
  var users = [];
  Account.findOne({apikey: apikey}, function(err,result){
    return fn(null, result);
  });
}

//For sessions, if we use them
passport.serializeUser(function(user, done) {
  done(null, user.id);
});

passport.deserializeUser(function(id, done) {
  findById(id, function (err, user) {
    done(err, user);
  });
});

passport.use(new LocalStrategy(
  function(apikey, done) {
    // asynchronous verification, for effect...
    process.nextTick(function () {

      findByApiKey(apikey, function(err, user) {
        if (err) { return done(err); }
        if (!user) {console.log("gg"); return done(null, false, { message: 'Unknown apikey : ' + apikey }); }

        return done(null, user);
      })
    });
  }
));


exports.isAuthenticated = passport.authenticate('localapikey', {failureRedirect: '/api/unauthorized'});