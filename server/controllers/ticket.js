var Ticket = require('../models/ticket');
var Trip = require('../models/ticket');

exports.getTickets = function(req, res) {
    Ticket.find(function (err, tickets) {
        if (err) {
            res.send(err);
        }
        res.json(tickets);
    });
};

exports.getTicketsByUser = function(req, res) {
    Ticket
        .find({ _passengerId: req.user._id })
        .populate("_tripId")
        .exec(function(err, tickets) {
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
    ticket._passengerId = req.body.passengerid;
    ticket._tripId = req.body.tripid;

    ticket.save(function(err) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Ticket created.' });
    })
};



