// Load required packages
var passport = require('passport');
var passport = require('passport');
var BearerStrategy = require('passport-http-bearer').Strategy;

var Account = require('../../models/Account');
var API_LIMIT = 1000 * 10 * 60; // ten minutes 

//For sessions, if we use them
passport.serializeUser(function(user, done) {
  done(null, user.id);
});

passport.deserializeUser(function(id, done) {
  findById(id, function (err, user) {
    done(err, user);
  });
});

passport.use(new BearerStrategy(
  function(apikey, done) {
    Account.findOne({ apikey: apikey }, function (err, user) {
      if (err) { return done(err); }
      if (!user) { return done(null, false); }

      //User was found, do stuff, like update last use time.
      console.log(new Date() - user.accessed);
      console.log(API_LIMIT);
      if ((new Date() - user.accessed) > API_LIMIT){
        console.log("gg");
        return done(null, false, { message: 'apikey timed out, log in again' });
      }

      return done(null, user);
    });
  }
));

exports.isAuthenticated = passport.authenticate('bearer', {failureRedirect: '/api/unauthorized'});