var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TicketSchema = new Schema({
    price: {
        type: Number,
        required: 'A Ticket must have a price.'
    },
    seat: {
        type: Number,
        required: 'A Ticket must have a seat'
    },
    _passengerId: {
        type: Schema.Types.ObjectId,
        required: 'A Ticket must be assigned to a passenger.'
    },
    _tripId: {
        type: Schema.Types.ObjectId,
        required: 'A Ticket must be assigned to trip.'
    }
});

TicketSchema.methods.isValid = function(cb) {
    if(this.date > Date.now) {
        cb(null, isValid);
    }
};

module.exports = mongoose.model('Ticket', TicketSchema);

