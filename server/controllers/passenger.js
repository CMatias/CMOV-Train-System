var Passenger = require('../models/passenger');

exports.getPassengers = function(req, res) {
    Passenger.find(function (err, passengers) {
        if (err) {
            res.send(err);
        }
        res.json(passengers);
    });
};

exports.postPassenger = function(req, res) {

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
};

exports.getPassenger = function(req, res) {
    Passenger.findById(req.params.passenger_id, function(err, passenger) {
        if (err) {
            res.send(err);
        }
        res.json(passenger);
    })
};

exports.putPassenger = function(req, res) {
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
};

exports.deletePassenger = function(req, res) {
    Passenger.remove({
        _id: req.params.passenger_id
    }, function(err, passenger) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Successfully deleted passenger.' });
    });
};

