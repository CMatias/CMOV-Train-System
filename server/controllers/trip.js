var Trip = require('../models/trip');
var Ticket =  require('../models/ticket');

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

    Trip.find({"stops.date": {$gt: bDate, $lt: aDate }}, function (err, trips) {
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

    var ret = [];

    //alert(s.indexOf("oo") > -1);



    res.json();

};


exports.getTrip = function(req, res) {
    Trip.findById(req.params.trip_id, function(err, trip) {
        if (err) {
            res.send(err);
        }
        res.json(trip);
    })
};


exports.getTripSeats = function(req, res) {
    Ticket.find({"_trip": req.params.trip_id}, function (err, tickets) {
        if (err) {
            res.send(err);
        }

        var ret = [];
        for(var i = 0; i < tickets.length; i++) {
            ret.push(tickets[i].seat);
        }

        res.json(ret);
    });
};


