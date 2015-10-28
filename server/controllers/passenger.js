var Passenger = require('../models/passenger');

exports.getPassengers = function(req, res) {
    Passenger.find(function (err, passengers) {
        if (err) {
            res.send(err);
        }
        res.json(passengers);
    });
};

exports.getPassenger = function(req, res) {
    Passenger.findById(req.decoded._id, function(err, passenger) {
        if (err) {
            res.send(err);
        }
        res.json(passenger);
    })
};

exports.getCreditCards = function(req, res) {
    Passenger.findById(req.decoded._id , function(err, passenger) {
        if (err) {
            res.send(err);
        }

        res.json(passenger.creditcards);
    })
};

exports.postPassenger = function(req, res) {

    var passenger = new Passenger();

    passenger.username = req.body.username;
    passenger.email = req.body.email;
    passenger.password = req.body.password;
    passenger.creditcards = req.body.creditcards;

    passenger.save(function(err) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Passenger created with username: ' + req.body.username + '.' });
    })
};

exports.postCreditCards = function(req, res) {

    Passenger.findById(req.decoded._id , function(err, passenger) {
        if (err) {
            res.send(err);
        }

        var creditcard = {};
        creditcard.type = req.body.type;
        creditcard.number = req.body.number;
        creditcard.validity = req.body.validity;

        cards = passenger.creditcards.concat(creditcard);
        passenger.creditcards = cards;

        passenger.save(function(err) {
            if (err) {
                res.send(err);
            }
            res.json({ message: 'Added creditcard to: ' + passenger.username + '.' });
        })
    });
};

exports.putPassenger = function(req, res) {
    Passenger.findById(req.decoded._id, function(err, passenger) {
        if (err) {
            res.send(err);
        }
        passenger.username = req.body.username;
        passenger.email = req.body.email;
        passenger.password = req.body.password;
        passenger.creditcards = req.body.creditcards;

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
        _id: req.decoded._id
    }, function(err, passenger) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Passenger successfully deleted.' });
    });
};

