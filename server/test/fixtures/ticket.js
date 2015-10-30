module.exports = function(fixtures) {

    var passengers = fixtures.get('Passenger').Passenger;
    p1Id = passengers[0]._id;
    p2Id = passengers[1]._id;
    p3Id = passengers[2]._id;

    var trips = fixtures.get('Trip').Trip;
    t1 = trips[0]._id;
    t2 = trips[1]._id;
    t3 = trips[2]._id;

    fixtures.save('Ticket', {
        Ticket: [
            {
                "seat": "98",
                "price": "12.5",
                "_passenger": p1Id,
                "_trip": t1
            },
            {
                "seat": "28",
                "price": "12.5",
                "_passenger": p2Id,
                "_trip": t1
            },
            {
                "seat": "56",
                "price": "12.5",
                "_passenger": p1Id,
                "_trip": t2
            },
            {
                "seat": "11",
                "price": "12.5",
                "_passenger": p3Id,
                "_trip": t3
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