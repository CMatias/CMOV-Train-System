var Ticket = require('../models/ticket');
var Trip = require('../models/ticket');
var Passenger = require('../models/passenger');
var creditCardVerifier = require('./creditcardservice');

exports.getTickets = function(req, res) {
    Ticket
        .find({
            "_passenger" : req.decoded._id,
            "used": false
        })
        .populate([{path:'_trip'}, {path:'_passenger'}])
        .exec(function (err, tickets) {
            if (err) {
                res.send(err);
            }
            res.json(tickets);
        });
};

exports.getTicketsByTrip = function(req, res) {
    Ticket
        .find({
            "_trip" : req.params.trip_id,
        })
        .populate([{path:'_trip'}, {path:'_passenger'}])
        .exec(function (err, tickets) {
            if (err) {
                res.send(err);
            }
            res.json(tickets);
        });
};

exports.postTicket = function(req, res) {

    var ccardMsg = creditCardVerifier.verifyCreditCard(null, req.body.credicard);
    if(ccardMsg == "Denied") {
        res.json("Credit Card Denied");
    } else {
        if(req.body.trips.length > 0 && req.body.trips.length <= 2){
            for(var i = 0; i < req.body.seats.length; i++) {
                var ticket = new Ticket();
                ticket.seat = req.body.seats[i];
                ticket.price = req.body.price[i];
                ticket._passenger = req.decoded._id;
                ticket._trip = req.body.trips[i];


                ticket.save(function (err) {
                    if (err) {
                        res.send(err);
                    }
                })
            }
            res.json("Tickets created.");

        } else {
            res.json("Invalid Trips Defined.");
        }
    }
};

exports.putTripInfo = function(req, res) {
    for(var i = 0; i < req.body.tickets.length; i++) {
        Ticket.findById(req.body.tickets[i].id, function(err, ticket) {
            if (err) {
                res.send(err);
            }
            ticket.used = true;
            ticket.save(function(err) {
                if (err) {
                    res.send(err);
                }
            })
        })
    }
    res.json("Uploaded Trip Info");
};


