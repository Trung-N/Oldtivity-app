var mongoose = require('mongoose');
var bcrypt   = require('bcrypt-nodejs');
var userSchema = mongoose.Schema({
  "fullName": String,
  "email": String,
  "date": Date,
  "password": String,
    "smoking": Number,
    "diet": Number,
    "sleep": Number,
    "alcohol": Number,
    "height": Number,
    "weight": Number,
    "goals": [{ "name": String, "goalProgress": Number, "target": Number}],
    "friends": [{"email": String, "fullName": String}],
    "pendingFriends": [{"email": String, "fullName": String}],
    "personalFeed": [{ "body": String, "date": Date, "email": String}],
    "friendFeed": [{ "body": String, "date": Date, "email": String}],
});

mongoose.model('user',userSchema);

// Hashes given password
userSchema.methods.generateHash = function(password) {
    return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
};

// checks if password is valid
userSchema.methods.validPassword = function(password) {
    return bcrypt.compareSync(password, this.password);
};

// create the model for users and expose it to our app
module.exports = mongoose.model('User', userSchema);
