module.exports = function(fixtures) {

    var passengers = fixtures.get('Passenger').Passenger;
    p1Id = passengers[0]._id;
    p2Id = passengers[1]._id;
    p3Id = passengers[2]._id;

    var trips = fixtures.get('Trip').Trip;
    t1 = trips[0]._id;
    t2 = trips[1]._id;
    t3 = trips[2]._id;
    t6 = trips[6]._id;

    var today = new Date();

    fixtures.save('Ticket', {
        Ticket: [
            {
                "departure":  { station: "A", date: today.setHours(8, 0, 0)},
                "arrival": { station: "MS", date: today.setHours(9, 45, 0)},
                "duration": new Date(63000000), //1h45min in ms.
                "seat": "98",
                "price": "12.5",
                "_passenger": p1Id,
                "_trip": t1,
                "connection": {
                    "trip": t6,
                    "waitingtime": {
                        "hours": "0",
                        "minutes": "15",
                        "seconds": "0"
                    }
                }
            },
            {
                "departure": { station: "C", date: today.setHours(10, 0, 0)},
                "arrival": { station: "MS", date: today.setHours(11, 0, 0)},
                "duration": new Date(3600000),
                "seat": "98",
                "price": "7.5",
                "_passenger": p1Id,
                "_trip": t6
            },
            {
                "departure":  { station: "B", date: today.setHours(8, 0, 0)},
                "arrival":  { station: "A", date: today.setHours(11, 30, 0)},
                "duration": new Date(12600000),
                "seat": "28",
                "price": "12.5",
                "_passenger": p2Id,
                "_trip": t2
            },
            {
                "departure":   { station: "A", date: today.setHours(13, 0, 0)},
                "arrival":  { station: "B", date: today.setHours(16, 30, 0)},
                "duration": new Date(12600000),
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