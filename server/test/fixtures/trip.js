mongoose = require('mongoose');

module.exports = function(fixtures) {
    var today = new Date();
    var tomorrow = new Date(); tomorrow.setDate(today.getDate() + 1);
    var aftertomorrow = new Date(); aftertomorrow.setDate(tomorrow.getDate() + 1);

    fixtures.save('Trip', {
        Trip: [
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(8, 0, 0),
                "arrival": today.setHours(9, 45, 0),
                "capacity": "119",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(9, 45, 0),
                "arrival": today.setHours(11, 30, 0),
                "capacity": "119",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(8, 30, 0),
                "arrival": today.setHours(10, 15, 0),
                "capacity": "120",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(10, 15, 0),
                "arrival": today.setHours(12, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(12, 0, 0),
                "arrival": today.setHours(13, 45, 0),
                "capacity": "120",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(13, 45, 0),
                "arrival": today.setHours(15, 30, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(12, 30, 0),
                "arrival": today.setHours(14, 15, 0),
                "capacity": "120",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(14, 15, 0),
                "arrival": today.setHours(16, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(16, 0, 0),
                "arrival": today.setHours(17, 45, 0),
                "capacity": "120",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(17, 45, 0),
                "arrival": today.setHours(19, 30, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(16, 30, 0),
                "arrival": today.setHours(18, 15, 0),
                "capacity": "118",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(18, 15, 0),
                "arrival":today.setHours(20, 0, 0),
                "capacity": "118",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(10, 0, 0),
                "arrival": today.setHours(11, 0, 0),
                "capacity": "119",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure":today.setHours(11, 0, 0),
                "arrival": today.setHours(12, 0, 0),
                "capacity": "119",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(14, 0, 0),
                "arrival": today.setHours(15, 0, 0),
                "capacity": "120",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(15, 0, 0),
                "arrival": today.setHours(16, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(16, 0, 0),
                "arrival": today.setHours(17, 0, 0),
                "capacity": "120",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": today.setHours(17, 0, 0),
                "arrival": today.setHours(18, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(8, 0, 0),
                "arrival": tomorrow.setHours(9, 45, 0),
                "capacity": "120",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(9, 45, 0),
                "arrival": tomorrow.setHours(11, 30, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(8, 30, 0),
                "arrival": tomorrow.setHours(10, 15, 0),
                "capacity": "120",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(10, 15, 0),
                "arrival": tomorrow.setHours(12, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(12, 0, 0),
                "arrival": tomorrow.setHours(13, 45, 0),
                "capacity": "120",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(13, 45, 0),
                "arrival": tomorrow.setHours(15, 30, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(12, 30, 0),
                "arrival": tomorrow.setHours(14, 15, 0),
                "capacity": "120",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(14, 15, 0),
                "arrival": tomorrow.setHours(16, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(16, 0, 0),
                "arrival": tomorrow.setHours(17, 45, 0),
                "capacity": "120",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(17, 45, 0),
                "arrival": tomorrow.setHours(19, 30, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(16, 30, 0),
                "arrival": tomorrow.setHours(18, 15, 0),
                "capacity": "120",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(18, 15, 0),
                "arrival":tomorrow.setHours(20, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(10, 0, 0),
                "arrival": tomorrow.setHours(11, 0, 0),
                "capacity": "120",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure":tomorrow.setHours(11, 0, 0),
                "arrival": tomorrow.setHours(12, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(14, 0, 0),
                "arrival": tomorrow.setHours(15, 0, 0),
                "capacity": "120",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(15, 0, 0),
                "arrival": tomorrow.setHours(16, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(16, 0, 0),
                "arrival": tomorrow.setHours(17, 0, 0),
                "capacity": "120",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": tomorrow.setHours(17, 0, 0),
                "arrival": tomorrow.setHours(18, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(8, 0, 0),
                "arrival": aftertomorrow.setHours(9, 45, 0),
                "capacity": "120",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(9, 45, 0),
                "arrival": aftertomorrow.setHours(11, 30, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(8, 30, 0),
                "arrival": aftertomorrow.setHours(10, 15, 0),
                "capacity": "120",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(10, 15, 0),
                "arrival": aftertomorrow.setHours(12, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(12, 0, 0),
                "arrival": aftertomorrow.setHours(13, 45, 0),
                "capacity": "120",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(13, 45, 0),
                "arrival": aftertomorrow.setHours(15, 30, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(12, 30, 0),
                "arrival": aftertomorrow.setHours(14, 15, 0),
                "capacity": "120",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(14, 15, 0),
                "arrival": aftertomorrow.setHours(16, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(16, 0, 0),
                "arrival": aftertomorrow.setHours(17, 45, 0),
                "capacity": "120",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(17, 45, 0),
                "arrival": aftertomorrow.setHours(19, 30, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(16, 30, 0),
                "arrival": aftertomorrow.setHours(18, 15, 0),
                "capacity": "120",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(18, 15, 0),
                "arrival":aftertomorrow.setHours(20, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(10, 0, 0),
                "arrival": aftertomorrow.setHours(11, 0, 0),
                "capacity": "120",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure":aftertomorrow.setHours(11, 0, 0),
                "arrival": aftertomorrow.setHours(12, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(14, 0, 0),
                "arrival": aftertomorrow.setHours(15, 0, 0),
                "capacity": "120",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(15, 0, 0),
                "arrival": aftertomorrow.setHours(16, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(16, 0, 0),
                "arrival": aftertomorrow.setHours(17, 0, 0),
                "capacity": "120",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "departure": aftertomorrow.setHours(17, 0, 0),
                "arrival": aftertomorrow.setHours(18, 0, 0),
                "capacity": "120",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            }
        ]
    });

    fixtures('Trip', function(err, data) {
        if(err) {
            console.log(err);
        } else {
            console.log('Loaded Trip fixtures.');
        }
    });
};