var mongoose = require('mongoose');

var PictureSchema = new mongoose.Schema({
  account_id: { type: String, required: true },
  name: { type: String, required: true },
  encoded_string: { type: Object, required: true },
  is_shared: { type: Boolean, default: false }
});

module.exports = mongoose.model('pictures', PictureSchema);
