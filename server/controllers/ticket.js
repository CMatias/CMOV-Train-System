var Ticket = require('../models/ticket');

exports.getTickets = function(req, res) {
    Ticket.find(function (err, tickets) {
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

exports.getTicket = function(req, res) {
    Ticket.findById(req.params.ticket_id, function(err, ticket) {
        if (err) {
            res.send(err);
        }
        res.json(ticket);
    })
};

exports.putTicket = function(req, res) {
    Ticket.findById(req.params.ticket_id, function(err, ticket) {
        if (err) {
            res.send(err);
        }

        ticket.price = req.body.price;
        ticket.seat = req.body.seat;
        ticket._passengerId = req.body.passengerid;
        ticket._tripId = req.body.tripid;

        ticket.save(function(err) {
            if (err) {
                res.send(err);
            }
            res.json({ message: 'Ticket updated!' });
        })
    })
};

exports.deleteTicket = function(req, res) {
    Ticket.remove({
        _id: req.params.ticket_id
    }, function(err, ticket) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Ticket successfully deleted.' });
    });
};

