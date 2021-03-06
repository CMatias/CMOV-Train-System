var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var bcrypt = require('bcrypt-nodejs');
var validators = require('mongoose-validators');

var InspectorSchema = new Schema({
    username: {
        type: String,
        required: 'An Inspector must have an username.',
        validate: validators.isAlphanumeric()
    },
    email: {
        type: String,
        unique: true,
        required: 'An Inspector must have an email.',
        validate: validators.isEmail()
    },
    password: { 
        type: String, 
        required: 'An Inspector must have a password.',
        validate: validators.isAlphanumeric()
    },
    _trips: [{ type: Schema.Types.ObjectId, ref: 'Trip' }]
});

// Execute before each passenger.save() call
InspectorSchema.pre('save', function(callback) {
    var inspector = this;

    // Break out if the password hasn't changed
    if (!inspector.isModified('password')) return callback();

    // Password changed so we need to hash it
    bcrypt.genSalt(5, function(err, salt) {
        if (err) return callback(err);

        bcrypt.hash(inspector.password, salt, null, function(err, hash) {
            if (err) return callback(err);
            inspector.password = hash;
            callback();
        });
    });
});

InspectorSchema.methods.verifyPassword = function(password, cb) {
    bcrypt.compare(password, this.password, function(err, isMatch) {
        if (err) return cb(err);
        cb(null, isMatch);
    });
};
module.exports = mongoose.model('Inspector', InspectorSchema, 'Inspector');

