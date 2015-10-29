var Ticket = require('../models/ticket');
var Trip = require('../models/ticket');

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


exports.postTicket = function(req, res) {

    var ticket = new Ticket();

    ticket.price = req.body.price;
    ticket.seat = req.body.seat;
    ticket._passenger = req.body.passengerid;
    ticket._trip = req.body.tripid;

    ticket.save(function(err) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Ticket created.' });
    })
};



