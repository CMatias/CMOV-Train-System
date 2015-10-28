var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TripSchema = new Schema({
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
    },
    stops: [
        {
            station: {
                type: String,
                required: 'A Stop must have a station.'
            },
            date: {
                type: Date,
                required: 'A Stop must have a date.'
            }
        }
    ]
});

module.exports = mongoose.model('Trip', TripSchema);

