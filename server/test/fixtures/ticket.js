var Passenger = require('../../models/passenger');

module.exports = function(fixtures) {

    var passengers = fixtures.get('Passenger').Passenger;
    p1Id = passengers[0]._id;
    p2Id = passengers[1]._id;
    p3Id = passengers[2]._id;

    var trips = fixtures.get('Trip').Trip;
    t1_A_CS = trips[0]._id;
    t1_CS_B = trips[1]._id;
    t2_B_CS = trips[12]._id;
    t2_CS_A = trips[13]._id;
    t3_C_CS = trips[14]._id;
    t4_CS_C = trips[19]._id;


    fixtures.save('Ticket', {
        Ticket: [
            {
                "price": "4.50",
                "seat": "98",
                "_passengerId": p1Id,
                "_tripId": t1_A_CS
            },
            {
                "price": "5.50",
                "seat": "98",
                "_passengerId": p1Id,
                "_tripId": t1_CS_B
            },
            {
                "price": "5.50",
                "seat": "56",
                "_passengerId": p1Id,
                "_tripId": t2_B_CS
            },
            {
                "price": "4.50",
                "seat": "56",
                "_passengerId": p1Id,
                "_tripId": t2_CS_A
            },
            {
                "price": "5.50",
                "seat": "44",
                "_passengerId": p2Id,
                "_tripId": t2_B_CS
            },
            {
                "price": "6.00",
                "seat": "44",
                "_passengerId": p3Id,
                "_tripId": t3_C_CS
            },
            {
                "price": "6.00",
                "seat": "101",
                "_passengerId": p3Id,
                "_tripId": t4_CS_C
            },
            {
                "price": "5.00",
                "seat": "22",
                "_passengerId": p1Id,
                "_tripId": t1_CS_B
            }
        ]
    });

    fixtures('Ticket', function(err, data) {
        if(err) {
            console.log(err);
        } else {
            console.log('Loaded Ticket fixtures.');
        }
    });
};