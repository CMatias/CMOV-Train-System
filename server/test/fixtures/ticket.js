module.exports = function(fixtures) {

    var keyManager =  require('../../controllers/keymanager');

    var passengers = fixtures.get('Passenger').Passenger;
    var p1Id, p2Id, p3Id;
    for(var i = 0; i < passengers.length; i++)
    {
        if(passengers[i].username == 'JohnDoe') {
            p1Id = passengers[i]._id;
        } else if (passengers[i].username == 'RobertSmith') {
            p3Id = passengers[i]._id;
        } else if (passengers[i].username == 'ShelbyBell') {
            p2Id = passengers[i]._id;
        }
    }

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

    var today = new Date();

    var ticketId1 = mongoose.Types.ObjectId();
    var ticketId2 = mongoose.Types.ObjectId();
    var ticketId3 = mongoose.Types.ObjectId();

    fixtures.save('Ticket', {
        Ticket: [
            {
                "_id": ticketId1,
                "departure":  { "station": "A", "date": today.setHours(8, 0, 0)},
                "arrival": { "station": "MS", "date": today.setHours(9, 45, 0)},
                "duration": new Date(6300000), //1h45min in ms.
                "seats": ["98", "44"],
                "price": "12.5",
                "_passenger": p1Id,
                "_trips": [t1Id, t7Id],
                "connection": {
                    "trip": t7Id,
                    "waitingtime": {
                        "hours": "0",
                        "minutes": "15",
                        "seconds": "0"
                    }
                },
                "signature": keyManager.getSign(ticketId1)
            },
            {
                "_id": ticketId2,
                "departure":  { "station": "B", "date": today.setHours(8, 0, 0)},
                "arrival":  { "station": "A", "date": today.setHours(11, 30, 0)},
                "duration": new Date(12600000),
                "seats": ["28"],
                "price": "12.5",
                "_passenger": p2Id,
                "_trips": [t2Id],
                "signature": keyManager.getSign(ticketId2)
            },
            {
                "_id": ticketId3,
                "departure":   { "station": "A", "date": today.setHours(13, 0, 0)},
                "arrival":  { "station": "B", "date": today.setHours(16, 30, 0)},
                "duration": new Date(12600000),
                "seats": ["11"],
                "price": "12.5",
                "_passenger": p3Id,
                "_trips": [t3Id],
                "signature": keyManager.getSign(ticketId3)
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