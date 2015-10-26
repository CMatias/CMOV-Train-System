var Passenger = require('../models/passenger');
var jwt = require('jsonwebtoken');

exports.authPassenger = function(req, res) {
    Passenger.findOne({
        username: req.body.username
    }, function (err, passenger) {
        if (err) throw err;
        if (!passenger) {
            res.json({success: false, message: 'Authentication failed. User not found.'});
        } else if (passenger) {
            passenger.verifyPassword(req.body.password, function(err, isMatch) {
                if (err) { throw err; }
                if (!isMatch) {
                    res.json({success: false, message: 'Authentication failed. Wrong password.'});
                }

                var token = jwt.sign(passenger, req.app.get('superSecret'), {
                    expiresIn: 24*60*60*3 // expires in 3*24 hours
                });
                res.json({
                    success: true,
                    message: 'Enjoy your token!',
                    token: token
                });
            });
        }
    });
};

