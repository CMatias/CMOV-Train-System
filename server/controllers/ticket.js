var mongoose = require('mongoose');
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
            "_trip" : req.params.trip_id
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

    var keyManager =  require('./keymanager');

    var ccardMsg = creditCardVerifier.verifyCreditCard(null, req.body.credicard);
    if(ccardMsg == "Denied") {
        res.json("Credit Card Denied");
    } else {
        console.log("One Ticket");
        var ticket = new Ticket();
        ticket._id = mongoose.Types.ObjectId();
        ticket.arrival = req.body.arrival;
        ticket.departure = req.body.departure;
        ticket.seat = req.body.seats[0];
        ticket.duration = new Date(new Date(req.body.arrival.date) - new Date(req.body.departure.date));
        ticket.price = req.body.price[0];
        ticket._passenger = req.decoded._id;
        ticket._trip = req.body.trips[0];
        ticket.save(function (err) {
            if (err) {
                res.send(err);
            }
        });
        var signature = keyManager.getSign(ticket._id);
        res.json({"ticket": ticket, "signature": signature});
        /*
        if(req.body.trips.length == 1){
            console.log("One Ticket");
            var ticket = new Ticket();
            ticket._id = mongoose.Types.ObjectId();
            ticket.arrival = req.body.arrival;
            ticket.departure = req.body.departure;
            ticket.seat = req.body.seats[0];
            ticket.duration = new Date(new Date(req.body.arrival.date) - new Date(req.body.departure.date));
            ticket.price = req.body.price[0];
            //ticket._passenger = req.decoded._id;
            ticket._passenger = req.body.passenger;
            ticket._trip = req.body.trips[0];
            ticket.save(function (err) {
                if (err) {
                    res.send(err);
                }
            });
            var signature = keyManager.getSign(ticket._id);
            res.json({"ticket": ticket, "signature": signature});

        } else if (req.body.trips.length == 2){
            console.log("Two Tickets");
            var ret = [];
            for(var i = 0; i < req.body.seats.length; i++) {
                var ticket = new Ticket();
                ticket._id = mongoose.Types.ObjectId();
                ticket.arrival = req.body.arrival;
                ticket.departure = req.body.departure;
                ticket.duration = new Date(new Date(req.body.arrival.date) - new Date(req.body.departure.date));
                ticket.seat = req.body.seats[i];
                ticket.price = req.body.price[i];
                //ticket._passenger = req.decoded._id;
                ticket._passenger = req.body.passenger;
                ticket._trip = req.body.trips[i];

                if(i == 1){
                    ticket.connection = {
                        "trip": req.body.trips[0].id,
                        "waitingtime": req.body.waitingtime
                    };
                }

                ticket.save(function (err) {
                    if (err) {
                        res.send(err);
                    }
                });

                var signature = keyManager.getSign(ticket._id);
                ret.push({"ticket": ticket, "signature": signature});
            }
            res.send(ret);

        } else {
            res.json("Invalid Trips Defined.");
        }
        */
    }
};

var prepareRes = function(err, res, data){

    console.log(data);
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


