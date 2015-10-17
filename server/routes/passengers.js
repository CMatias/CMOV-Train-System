var express = require('express');
var router = express.Router();
var Passenger = require('../models/passenger');
var authController = require('./auth');

router.route('/')
    //Gets all the passengers.
    .get(authController.isAuthenticated, function(req, res) {
        Passenger.find(function(err, passengers) {
            if (err) {
                res.send(err);
            }
            res.json(passengers);
        });
    })
    //Creates a new passenger.
    .post(authController.isAuthenticated, function(req, res) {

        var passenger = new Passenger();

        passenger.username = req.body.username;
        passenger.email = req.body.email;
        passenger.password = req.body.password;
        passenger.creditcard = req.body.creditcard;

        passenger.save(function(err) {
            if (err) {
                res.send(err);
            }
            res.json({ message: 'Passenger created with username: ' + req.body.username + '.' });
        })
    });


router.route('/:passenger_id')
    //Gets a passenger with a specified Id.
    .get(authController.isAuthenticated, function(req, res) {
        Passenger.findById(req.params.passenger_id, function(err, passenger) {
            if (err) {
                res.send(err);
            }
            res.json(passenger);
        })
    })
    //Updates a passenger with a specified Id.
    .put(authController.isAuthenticated, function(req, res) {
        Passenger.findById(req.params.passenger_id, function(err, passenger) {
            if (err) {
                res.send(err);
            }
            passenger.username = req.body.username;
            passenger.email = req.body.email;
            passenger.password = req.body.password;
            passenger.creditcard = req.body.creditcard;

            passenger.save(function(err) {
                if (err) {
                    res.send(err);
                }
                res.json({ message: 'Passenger ' + red.body.username + ' updated!' });
            })
        })
    })
    //Updates a passenger with a specified Id.
    .delete(authController.isAuthenticated, function(req, res) {
        Passenger.remove({
            _id: req.params.passenger_id
        }, function(err, passenger) {
            if (err) {
                res.send(err);
            }
            res.json({ message: 'Successfully deleted passenger.' });
        });
    });

module.exports = router;
