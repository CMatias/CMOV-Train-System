var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var bcrypt = require('bcrypt-nodejs');

var PassengerSchema = new Schema({
    username: {
        type: String,
        unique: true,
        required: 'Username is required'
    },
    email: {
        type: String,
        unique: true,
        required: 'Email address is required'
    },
    password: { 
        type: String, 
        required: 'Password is required',
    },
    creditcard: {
        type: {
            type: String,
            required: true,
            enum: ['Credit', 'Debit']
        },
        number: {
            type: Number
        },
        validity: {
            type: Date
        }
    }
});

// Execute before each passenger.save() call
PassengerSchema.pre('save', function(callback) {
    var passenger = this;

    // Break out if the password hasn't changed
    if (!passenger.isModified('password')) return callback();

    // Password changed so we need to hash it
    bcrypt.genSalt(5, function(err, salt) {
        if (err) return callback(err);

        bcrypt.hash(passenger.password, salt, null, function(err, hash) {
            if (err) return callback(err);
            passenger.password = hash;
            callback();
        });
    });
});

PassengerSchema.methods.verifyPassword = function(password, cb) {
    bcrypt.compare(password, this.password, function(err, isMatch) {
        if (err) return cb(err);
        cb(null, isMatch);
    });
};

module.exports = mongoose.model('Passenger', PassengerSchema);

