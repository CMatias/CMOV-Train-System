var Trip = require('../models/trip');

exports.getTrips = function(req, res) {
    Trip.find(function (err, trips) {
        if (err) {
            res.send(err);
        }
        res.json(trips);
    });
};

exports.getTripsByDate = function(req, res) {
    Trip.find({"departure": req.params.date}, function (err, trips) {
        if (err) {
            res.send(err);
        }

        res.json(trips);
    });
};

exports.postTrip = function(req, res) {

    var trip = new Trip();

    trip.departure = req.body.departure;
    trip.arrival = req.body.arrival;
    trip.capacity = req.body.capacity;
    trip.departurestation = req.body.departurestation;
    trip.arrivalstation = req.body.arrivalstation;

    trip.save(function(err) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Trip created.' });
    })
};

exports.getTrip = function(req, res) {
    Trip.findById(req.params.trip_id, function(err, trip) {
        if (err) {
            res.send(err);
        }
        res.json(trip);
    })
};



