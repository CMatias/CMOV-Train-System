mongoose = require('mongoose');

module.exports = function(fixtures) {

    var trips = fixtures.get('Trip').Trip;
    var t1Id, t2Id, t3Id, t7Id;
    for(var j = 0; j < trips.length; j++)
    {
        if(trips[j].train == 'A-B-1-TD') {
            t1Id = trips[j]._id;
        } else if(trips[j].train == 'B-A-1-TD') {
            t2Id = trips[j]._id;
        } else if(trips[j].train == 'A-B-2-TD') {
            t3Id = trips[j]._id;
        } else if(trips[j].train == 'MS-C-1-TD') {
            t7Id = trips[j]._id;
        }
    }

    fixtures.save('Inspector', {
        Inspector: [
            {
                "username": "PeterGrey",
                "email": "pgrey@gmail.com",
                "password": "pgrey1234",
                "_trips": [t1Id, t3Id]
            },
            {
                "username": "AlexLee",
                "email": "alee@gmail.com",
                "password": "alee1234",
                "_trips": [t2Id]
            },
            {
                "username": "MarcDom",
                "email": "mdom@gmail.com",
                "password": "mdom1234",
                "_trips": [t7Id]
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
