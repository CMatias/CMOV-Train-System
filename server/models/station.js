var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var StationSchema = new Schema({
    name: {
        type: String,
        required: 'A Station must have a name.'
    },
    timetable: {
        type: [Schema.Types.ObjectId]
    }
});

module.exports = mongoose.model('Station', StationSchema);

