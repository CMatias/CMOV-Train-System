var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var bcrypt = require('bcrypt-nodejs');
var validators = require('mongoose-validators');


var PassengerSchema = new Schema({
    username: {
        type: String,
        unique: true,
        required: 'A Passenger must have an username.',
        validate: validators.isAlphanumeric()
    },
    email: {
        type: String,
        unique: true,
        required: 'A Passenger must have an email.',
        validate: validators.isEmail()
    },
    password: {
        type: String,
        required: 'A Passenger must have a password.',
        validate: validators.isAlphanumeric()
    },
    creditcards: [
        {
            type: {
                type: String,
                required: true,
                enum: ['Credit', 'Debit']
            },
            number: {
                type: String,
                required: 'A CreditCard must have a number.',
                validate: validators.isLength(16)
            },
            validity: {
                type: Date,
                required: 'A CreditCard must have a validity date.'
            }
        }
    ]
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

