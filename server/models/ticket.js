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
    seat: {
        type: Number,
        min: 0,
        required: 'A Ticket must have a seat.'
    },
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
    _trip: {
        type: Schema.Types.ObjectId,
        required: 'A Ticket must be assigned to trip.',
        ref: 'Trip'
    },
    used: {
        type: Boolean,
        default: false
    },
    connection: {
        type: Schema.Types.Mixed
    }
});

module.exports = mongoose.model('Ticket', TicketSchema);

