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

    Trip.find({"stops.date": {$gt: bDate, $lt: aDate }}, function (err, trips) {
        if (err) {
            res.send(err);
        }
        console.log(trips.length);
        res.json(trips);
    });
};

/*
exports.getTripsByDateAndStations = function(req, res) {

    var bDate = new Date();
    bDate.setFullYear(req.params.year, req.params.month-1, req.params.day);
    bDate.setHours(0, 0, 0);
    var aDate = new Date();
    aDate.setDate(bDate.getDate() + 1);
    aDate.setHours(0, 0, 0);
    console.log(bDate + "<>" + aDate);


    if(req.params.arrival == "Central Station") {
        Trip
            .find({
                "departure": {$gt: bDate, $lt: aDate},
                "departureStation": req.params.departure,
                "arrivalStation": req.params.arrival
            })
            .sort({departure: 'ascending'})
            .exec(function (err, trips) {
                if (err) {
                    res.send(err);
                }

                res.json(trips);
            });
    } else {
        Trip
            .find({
                "departure": {$gt: bDate, $lt: aDate},
                "departureStation": req.params.departure,
                "arrivalStation":  "Central Station"})
            .sort({departure: 'ascending'})
            .exec(function (err, trips1) {
                if (err) {
                    res.send(err);
                }

                Trip
                    .find({
                        "departure": {$gt: bDate, $lt: aDate},
                        "departureStation": "Central Station",
                        "arrivalStation":  req.params.arrival
                    })
                    .sort({departure: 'ascending'})
                    .exec(function (err, trips2) {
                        if (err) {
                            res.send(err);
                        }

                        var trips = [];
                        for(var i = 0; i < trips2.length; i++){
                            trips.push(trips1[i]);
                            trips.push(trips2[i]);
                        }

                        res.json(trips);
                    });
            });
    }
};
*/


exports.getTrip = function(req, res) {
    Trip.findById(req.params.trip_id, function(err, trip) {
        if (err) {
            res.send(err);
        }
        res.json(trip);
    })
};



