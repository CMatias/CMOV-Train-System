var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var TripSchema = new Schema({
    currentCapacity: {
        type: Number,
        min: 0,
        required: 'A Trip  must have a fixed capacity.'
    },
    maxCapacity: {
        type: Number,
        min: 1,
        required: 'A Trip  must have a fixed capacity.'
    },
    train: {
        type: String,
        required: 'A Trip  must be assigned to a train.'
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

module.exports = mongoose.model('Trip', TripSchema, 'Trip');

