var fixtures = require('node-mongoose-fixtures');

fixtures.reset(function(err, data) {
    if(err) {
        console.log(err);
        exit();
    } else {
        console.log('Fixtures reset: ' + data);

        var loadIndependentFixtures = function(callback) {
            require('./passenger')(fixtures);
            require('./trip')(fixtures);
            callback();
        };

        loadIndependentFixtures(function() {
            require('./ticket')(fixtures);
            require('./inspector')(fixtures);
        });
    }
});





