mongoose = require('mongoose');

module.exports = function(fixtures) {
    fixtures.save('Passenger', {
        Passenger: [
            {
                "_id": mongoose.Types.ObjectId(),
                "username": "JohnDoe",
                "email": "jdoe@gmail.com",
                "password": "jdoe1234",
                "creditcards": [
                    {
                        "type": "Credit",
                        "number": "123412341234",
                        "validity": "January 10, 2016"
                    },
                    {
                        "type": "Debit",
                        "number": "234123412341",
                        "validity": "January 20, 2016"
                    }
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "username": "RobertSmith",
                "email": "rsmith@gmail.com",
                "password": "rsmith1234",
                "creditcards": [

                    {
                        "type": "Debit",
                        "number": "432143214321",
                        "validity": "February 21, 2016"
                    }
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "username": "ShelbyBell",
                "email": "sbell@gmail.com",
                "password": "sbell1234",
                "creditcards": [
                    {
                        "type": "Credit",
                        "number": "543254325432",
                        "validity": "March 12, 2016"
                    }
                ]
            }
        ]
    });

    fixtures('Passenger', function(err, data) {
        if(err) {
            console.log(err);
        } else {
            console.log('Loaded Passenger fixtures.');
        }
    });
};