var mongoose = require('mongoose');
var shortid = require('shortid');

var AccountSchema = new mongoose.Schema({
  username: { type: String, unique: true, required: true },
  password: { type: String, required: true },
  email: { type: Object, required: true },
  apikey: { type: String, required: true, unique: true, 'default': shortid.generate}
});

AccountSchema.methods.validPassword = function(password, cb) {
  return password == this.password;
};

module.exports = mongoose.model('accounts', AccountSchema);
