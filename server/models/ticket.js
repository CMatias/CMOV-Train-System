var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TicketSchema = new Schema({
    departure: {
        station: {
            type: String,
            required: 'A Ticket must have a departure station.'
        },
        date: {
            type: Date,
            required: 'A Station must have a date.'
        }
    },
    arrival: {
        station: {
            type: String,
            required: 'A Ticket must have an arrival station.'
        },
        date: {
            type: Date,
            required: 'A Station must have a date.'
        }
    },
    duration: {
        type: Date,
        required: 'A Ticket must have a duration.'
    },
    seats: [
        {
            type: Number,
            min: 0,
            required: 'A Ticket must have a seat.'

        }
    ],
    price: {
        type: Number,
        min: 0,
        required: 'A Ticket must have a price.'
    },
    _passenger: {
        type: Schema.Types.ObjectId,
        required: 'A Ticket must be assigned to a passenger.',
        ref: 'Passenger'
    },
    _trips: [{ type: Schema.Types.ObjectId, ref: 'Trip' }],
    used: {
        type: Boolean,
        default: false
    },
    connection: {
        type: Schema.Types.Mixed
    },
    signature: {
        type: Schema.Types.Mixed
    }
});

module.exports = mongoose.model('Ticket', TicketSchema, 'Ticket');

