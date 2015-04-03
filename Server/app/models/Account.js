var mongoose = require('mongoose');

var AccountSchema = new mongoose.Schema({
  username: { type: String, unique: true, required: true },
  password: { type: String, required: true },
  email: { type: Object, required: true }
});

module.exports = mongoose.model('accounts', AccountSchema);