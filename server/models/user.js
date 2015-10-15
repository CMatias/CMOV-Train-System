var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var UserSchema   = new Schema({
    name: String
});

module.exports = mongoose.model('User', UserSchema);

/*
options = { discriminatorKey: 'role' };
var UserSchema = new Schema({
    username: {
        type String,
        required: 'Username is required',
        min: 8,
        max 16
    },
    email: {
        type: String,
        trim: true,
        unique: true,
        required: 'Email address is required',
        validate: [validateEmail, 'Please fill a valid email address'],
        match: [/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/, 'Please fill a valid email address']
    },
    password: { 
        type: String, 
        required: 'Password is required',
        min: 8,
        max: 16
    },
}, options);
util.inherits(UserSchema, Schema);

var PassengerSchema = new Schema({
    creditCard: {
        type: {
            type: String,
            required: true,
            enum: ['Credit', 'Debit']
        },
        number: {
            type: Number, 
            min: 12,
            max: 12 
        },
        validity: {
            type: Date
        }
    }
}, options);

var InspectorSchema = new Schema({
    trips: [Schema.Types.ObjectId]
}, options);

module.exports = {
    User: mongoose.model('User', UserSchema),
    Passenger: UserSchema.discriminator('Passenger', PassengerSchema),
    Inspector: UserSchema.discriminator('Inspector', InspectorSchema)
};
*/