var fixtures = require('node-mongoose-fixtures');
var mongoose = require('mongoose');
var Passenger = require('../models/passenger');
mongoose.model('Passenger');

fixtures.save('Passenger', {
    Passenger: [
        {
            "username": "JohnDoe",
            "email": "jdoe@gmail.com",
            "password": "jdoe1234",
            "creditcard": {
                "type": "Credit",
                "number": "123412341234",
                "validity": "2016-01-01T23:28:56.782Z"
            }
        },
        {
            "username": "RobertSmith",
            "email": "rsmith@gmail.com",
            "password": "rsmith1234",
            "creditcard": {
                "type": "Debit",
                "number": "432143214321",
                "validity": "2017-02-02T23:28:56.782Z"
            }
        },
        {
            "username": "ShelbyBell",
            "email": "sbell@gmail.com",
            "password": "sbell1234",
            "creditcard": {
                "type": "Credit",
                "number": "543254325432",
                "validity": "2017-03-03T23:28:56.782Z"
            }
        }
    ]
});

fixtures('Passenger', function(err, data) {
    if(err) {
        //console.log(err);
    }
    console.log('Loaded Passenger fixtures.');
});

fixtures.get('Passenger', function(err, data){
    if(err){
        console.log(err);
    }
    console.log(data.Passenger[0]);
});


