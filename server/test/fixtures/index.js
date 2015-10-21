var fixtures = require('node-mongoose-fixtures');

fixtures.reset(function(err, data) {
    if(err) {
        console.log(err);
        exit();
    } else {
        console.log('Fixtures reset: ' + data);

        var loadIndependentFixtures = function(callback) {
            require('./Passenger')(fixtures);
            require('./Trip')(fixtures);
            callback();
        };

        loadIndependentFixtures(function() {
            require('./Ticket')(fixtures);
            require('./Inspector')(fixtures);
        });
    }
});





