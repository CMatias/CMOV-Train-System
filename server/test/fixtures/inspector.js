mongoose = require('mongoose');

module.exports = function(fixtures) {

    var trips = fixtures.get('Trip').Trip;
    t1_A_CS = trips[0]._id;
    t1_CS_B = trips[1]._id;
    t2_B_CS = trips[12]._id;
    t2_CS_A = trips[13]._id;
    t3_C_CS = trips[14]._id;
    t4_CS_C = trips[19]._id;

    fixtures.save('Inspector', {
        Inspector: [
            {
                "username": "PeterGrey",
                "email": "pgrey@gmail.com",
                "password": "pgrey1234",
                "_trips": [t1_A_CS, t1_CS_B]
            },
            {
                "username": "AlexLee",
                "email": "alee@gmail.com",
                "password": "alee1234",
                "_trips": [t2_B_CS, t2_CS_A]
            },
            {
                "username": "MarcDom",
                "email": "mdom@gmail.com",
                "password": "mdom1234",
                "_trips": [t3_C_CS, t4_CS_C]
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