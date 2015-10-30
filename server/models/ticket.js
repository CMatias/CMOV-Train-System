var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TicketSchema = new Schema({
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

