var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PassengerSchema = new Schema({
    username: {
        type: String,
        unique: true,
        required: 'Username is required'
    },
    email: {
        type: String,
        unique: true,
        required: 'Email address is required'
    },
    password: { 
        type: String, 
        required: 'Password is required',
    },
    creditcard: {
        type: {
            type: String,
            required: true,
            enum: ['Credit', 'Debit']
        },
        number: {
            type: Number
        },
        validity: {
            type: Date
        }
    }
});

module.exports = mongoose.model('Passenger', PassengerSchema);

