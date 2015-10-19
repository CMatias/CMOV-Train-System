var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TripSchema = new Schema({
    departure: {
        type: Date,
        required: 'A Ticket must have a departure time.'
    },
    arrival: {
        type: Date,
        required: 'A Ticket must have an arrival time.'
    },
    capacity: {
        type: Number,
        required: 'A Trip  must have a fixed capacity.'
    },
    train: {
        type: String
    },
    _departureStationId: {
        type: Schema.Types.ObjectId,
        required: 'A Trip must be between two stations.'
    },
    _arrivalStationId: {
        type: Schema.Types.ObjectId,
        required: 'A Trip must be between two stations.'
    }
});

module.exports = mongoose.model('Trip', TripSchema);

