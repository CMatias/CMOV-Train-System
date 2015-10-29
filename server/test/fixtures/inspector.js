mongoose = require('mongoose');

module.exports = function(fixtures) {

    var trips = fixtures.get('Trip').Trip;
    t1 = trips[0]._id;
    t2 = trips[1]._id;
    t3 = trips[2]._id;

    fixtures.save('Inspector', {
        Inspector: [
            {
                "username": "PeterGrey",
                "email": "pgrey@gmail.com",
                "password": "pgrey1234",
                "_trips": [t1, t3]
            },
            {
                "username": "AlexLee",
                "email": "alee@gmail.com",
                "password": "alee1234",
                "_trips": [t2]
            },
            {
                "username": "MarcDom",
                "email": "mdom@gmail.com",
                "password": "mdom1234",
                "_trips": [t1]
            }
        ]
    });

    fixtures('Inspector', function(err, data) {
        if(err) {
            console.log(err);
        } else {
            console.log('Loaded Inspector fixtures.');
        }
    });
};