var fixtures = require('node-mongoose-fixtures');
var Passenger = require('../../models/passenger');

fixtures.reset(function(err, data) {
    if(err) {
        console.log(err);
        exit();
    } else {
        console.log('Fixtures reset.');
        require('./Passenger')(fixtures);
        require('./Trip')(fixtures);
    }
});





