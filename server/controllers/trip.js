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

    var bDate = new Date();
    bDate.setFullYear(req.params.year, req.params.month-1, req.params.day);
    bDate.setHours(0, 0, 0);
    var aDate = new Date();
    aDate.setDate(bDate.getDate() + 1);
    aDate.setHours(0, 0, 0);
    console.log(bDate + "<>" + aDate);

    Trip.find({"departure": {$gt: bDate, $lt: aDate }}, function (err, trips) {
        if (err) {
            res.send(err);
        }
        console.log(trips.length);
        res.json(trips);
    });
};

exports.getTripsByDateAndStations = function(req, res) {

    var bDate = new Date();
    bDate.setFullYear(req.params.year, req.params.month-1, req.params.day);
    bDate.setHours(0, 0, 0);
    var aDate = new Date();
    aDate.setDate(bDate.getDate() + 1);
    aDate.setHours(0, 0, 0);
    console.log(bDate + "<>" + aDate);

    Trip.find({
        "departure": {$gt: bDate, $lt: aDate },
        "departureStation": req.params.departure,
        "arrivalStation": req.params.arrival
    }, function (err, trips) {
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



