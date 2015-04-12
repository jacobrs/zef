var mongoose = require('mongoose');

var AccountSchema = new mongoose.Schema({
  username: { type: String, unique: true, required: true },
  password: { type: String, required: true },
  email: { type: Object, required: true }
});

AccountSchema.methods.validPassword = function(password, cb) {
  return password == this.password;
};

module.exports = mongoose.model('accounts', AccountSchema);
