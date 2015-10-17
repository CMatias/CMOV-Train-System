var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var InspectorSchema = new Schema({
    username: {
        type: String,
        required: 'Username is required',
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
    trips: [Schema.Types.ObjectId]
});
var Inspector = mongoose.model('Inspector', InspectorSchema);

module.exports = Inspector;

