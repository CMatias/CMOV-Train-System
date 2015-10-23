var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TripSchema = new Schema({
    price: {
        type: Number,
        required: 'A Ticket must have a price.'
    },
    departure: {
        type: Date,
        required: 'A Ticket must have a departure time.'
    },
    arrival: {
        type: Date,
        required: 'A Ticket must have an arrival time.'
    },
    currentCapacity: {
        type: Number,
        required: 'A Trip  must have a fixed capacity.'
    },
    maxCapacity: {
        type: Number,
        required: 'A Trip  must have a fixed capacity.'
    },
    train: {
        type: String,
        required: 'A Trip  must be assigned to a train.'
    },
    departureStation: {
        type: String,
        required: 'A Trip must be between two stations.'
    },
    arrivalStation: {
        type: String,
        required: 'A Trip must be between two stations.'
    }
});

module.exports = mongoose.model('Trip', TripSchema);

