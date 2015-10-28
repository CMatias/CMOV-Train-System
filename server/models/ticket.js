var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TicketSchema = new Schema({
    seat: {
        type: Number,
        required: 'A Ticket must have a seat'
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
    }
});

module.exports = mongoose.model('Ticket', TicketSchema);

