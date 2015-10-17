var passport = require('passport');
var BasicStrategy = require('passport-http').BasicStrategy;
var Passenger = require('../models/passenger');

passport.use(new BasicStrategy(
    function(username, password, callback) {
        Passenger.findOne({ username: username }, function (err, passenger) {
            if (err) { return callback(err); }

            // No passenger found with that username
            if (!passenger) { return callback(null, false); }

            return callback(null, passenger);

            /*
            // Make sure the password is correct
             passenger.verifyPassword(password, function(err, isMatch) {
                if (err) { return callback(err); }

                // Password did not match
                if (!isMatch) { return callback(null, false); }

                // Success
                return callback(null, passenger);
            });
            */
        });
    }
));

exports.isAuthenticated = passport.authenticate('basic', { session : false });
