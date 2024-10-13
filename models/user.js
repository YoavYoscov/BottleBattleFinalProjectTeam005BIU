// We're going to use the 'mongoose' library to interact with our MongoDB database:
const mongoose = require('mongoose');
const bcrypt = require('bcrypt');

// We're going to use the Schema class from mongoose:
// Our schema will define the structure of documents in our Users collection:
const Schema = mongoose.Schema;

// We're going to create a new schema for our Users collection:
const User = new Schema({
    username: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: false
    },
    profilePic: {
        type: String,
        required: true
    },
    fireBaseToken: {
        type: String,
        required: false
    },
    userLevel: {
        type: Number, // i.e. 0 = noviceRecycler, 1 = intermediateRecycler, 2 = expertRecycler etc.
        required: true
    },
    userPoints: {
        type: Number,
        required: true
    },
    city: {
        type: String,
        required: true
    },
    friends: [{
        // An array of usernames of the user's friends:
        type: String,
        required: false
      }],
    moneySaved: {
        type: Number,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    resetPasswordToken: {
        type: String,
        required: false
    },
    resetPasswordExpires: {
        type: Date,
        required: false
    }
});
//}, { versionKey: false });


User.methods.comparePassword = function(password) {
    return bcrypt.compareSync(password, this.password);
};


User.pre('save', function(next) {
    if (!this.isModified('password') || !this.password) {
        return next();
    }
    this.password = bcrypt.hashSync(this.password, 10);
    next();
});

// We're going to export our schema so that we can use it in other files:
// We're going to use the 'mongoose.model()' method to create a new model for our Users collection, named 'Users':
module.exports = mongoose.model('Users', User);