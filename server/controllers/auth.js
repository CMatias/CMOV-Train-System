var Passenger = require('../models/passenger');
var Inspector = require('../models/inspector');
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

exports.authInspector = function(req, res) {
    Inspector.findOne({
        username: req.body.username
    }, function (err, inspector) {
        if (err) throw err;
        if (!inspector) {
            res.json({success: false, message: 'Authentication failed. User not found.'});
        } else if (inspector) {
            inspector.verifyPassword(req.body.password, function(err, isMatch) {
                if (err) { throw err; }
                if (!isMatch) {
                    res.json({success: false, message: 'Authentication failed. Wrong password.'});
                }

                var token = jwt.sign(inspector, req.app.get('superSecret'), {
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

exports.getPublicKey = function(req, res){
    var keyManager =  require('./keymanager');
    var publicKey = keyManager.getKeyPair().public;

    var firstIndex = publicKey.indexOf('\n');
    var lastIndex = publicKey.lastIndexOf('\n');

    res.json(publicKey.substring(firstIndex+1, lastIndex));
};