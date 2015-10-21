module.exports = function(fixtures) {
    fixtures.save('Passenger', {
        Passenger: [
            {
                "username": "JohnDoe",
                "email": "jdoe@gmail.com",
                "password": "jdoe1234",
                "creditcard": {
                    "type": "Credit",
                    "number": "123412341234",
                    "validity": "January 10, 2016"
                }
            },
            {
                "username": "RobertSmith",
                "email": "rsmith@gmail.com",
                "password": "rsmith1234",
                "creditcard": {
                    "type": "Debit",
                    "number": "432143214321",
                    "validity": "February 21, 2016"
                }
            },
            {
                "username": "ShelbyBell",
                "email": "sbell@gmail.com",
                "password": "sbell1234",
                "creditcard": {
                    "type": "Credit",
                    "number": "543254325432",
                    "validity": "March 12, 2016"
                }
            }
        ]
    });

    fixtures('Passenger', function(err, data) {
        if(err) {
            console.log(err);
            exit();
        } else {
            console.log('Loaded Passenger fixtures.');
        }
    });
};