mongoose = require('mongoose');

module.exports = function(fixtures) {
    var today = new Date();
    var tomorrow = new Date(); tomorrow.setDate(today.getDate() + 1);

    fixtures.save('Trip', {
        Trip: [
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": today.setHours(8, 0, 0),
                "arrival": today.setHours(9, 45, 0),
                "currentCapacity": "119",
                "maxCapacity": "120",
                "train": "A-CS-1",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": today.setHours(9, 45, 0),
                "arrival": today.setHours(11, 30, 0),
                "currentCapacity": "119",
                "maxCapacity": "120",
                "train": "CS-B-1",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": today.setHours(8, 30, 0),
                "arrival": today.setHours(10, 15, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "B-CS-1",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": today.setHours(10, 15, 0),
                "arrival": today.setHours(12, 0, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "CS-A-1",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": today.setHours(12, 0, 0),
                "arrival": today.setHours(13, 45, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "A-CS-2",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": today.setHours(13, 45, 0),
                "arrival": today.setHours(15, 30, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "CS-B-2",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": today.setHours(12, 30, 0),
                "arrival": today.setHours(14, 15, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "B-CS-2",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": today.setHours(14, 15, 0),
                "arrival": today.setHours(16, 0, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "CS-A-2",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": today.setHours(16, 0, 0),
                "arrival": today.setHours(17, 45, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "A-CS-3",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": today.setHours(17, 45, 0),
                "arrival": today.setHours(19, 30, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "CS-B-3",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": today.setHours(16, 30, 0),
                "arrival": today.setHours(18, 15, 0),
                "currentCapacity": "118",
                "maxCapacity": "120",
                "train": "B-CS-3",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": today.setHours(18, 15, 0),
                "arrival":today.setHours(20, 0, 0),
                "currentCapacity": "118",
                "maxCapacity": "120",
                "train": "CS-A-3",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": today.setHours(10, 0, 0),
                "arrival": today.setHours(11, 0, 0),
                "currentCapacity": "69",
                "maxCapacity": "70",
                "train": "C-CS-1",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure":today.setHours(11, 0, 0),
                "arrival": today.setHours(12, 0, 0),
                "currentCapacity": "69",
                "maxCapacity": "70",
                "train": "CS-C-1",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": today.setHours(14, 0, 0),
                "arrival": today.setHours(15, 0, 0),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-2",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": today.setHours(15, 0, 0),
                "arrival": today.setHours(16, 0, 0),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-2",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": today.setHours(16, 0, 0),
                "arrival": today.setHours(17, 0, 0),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-3",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": today.setHours(17, 0, 0),
                "arrival": today.setHours(18, 0, 0),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-3",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": tomorrow.setHours(8, 0, 0),
                "arrival": tomorrow.setHours(9, 45, 0),
                "currentCapacity": "119",
                "maxCapacity": "120",
                "train": "A-CS-1",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": tomorrow.setHours(9, 45, 0),
                "arrival": tomorrow.setHours(11, 30, 0),
                "currentCapacity": "119",
                "maxCapacity": "120",
                "train": "CS-B-1",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": tomorrow.setHours(8, 30, 0),
                "arrival": tomorrow.setHours(10, 15, 0),
                "currentCapacity": "119",
                "maxCapacity": "120",
                "train": "B-CS-1",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": tomorrow.setHours(10, 15, 0),
                "arrival": tomorrow.setHours(12, 0, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "CS-A-1",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": tomorrow.setHours(12, 0, 0),
                "arrival": tomorrow.setHours(13, 45, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "A-CS-2",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": tomorrow.setHours(13, 45, 0),
                "arrival": tomorrow.setHours(15, 30, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "CS-B-2",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": tomorrow.setHours(12, 30, 0),
                "arrival": tomorrow.setHours(14, 15, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "B-CS-2",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": tomorrow.setHours(14, 15, 0),
                "arrival": tomorrow.setHours(16, 0, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "CS-A-2",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": tomorrow.setHours(16, 0, 0),
                "arrival": tomorrow.setHours(17, 45, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "A-CS-3",
                "departureStation": "A",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": tomorrow.setHours(17, 45, 0),
                "arrival": tomorrow.setHours(19, 30, 0),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "CS-B-3",
                "departureStation": "Central Station",
                "arrivalStation": "B"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "6.00",
                "departure": tomorrow.setHours(16, 30, 0),
                "arrival": tomorrow.setHours(18, 15, 0),
                "currentCapacity": "118",
                "maxCapacity": "120",
                "train": "B-CS-3",
                "departureStation": "B",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "5.00",
                "departure": tomorrow.setHours(18, 15, 0),
                "arrival":tomorrow.setHours(20, 0, 0),
                "currentCapacity": "118",
                "maxCapacity": "120",
                "train": "CS-A-3",
                "departureStation": "Central Station",
                "arrivalStation": "A"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": tomorrow.setHours(10, 0, 0),
                "arrival": tomorrow.setHours(11, 0, 0),
                "currentCapacity": "69",
                "maxCapacity": "70",
                "train": "C-CS-1",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure":tomorrow.setHours(11, 0, 0),
                "arrival": tomorrow.setHours(12, 0, 0),
                "currentCapacity": "69",
                "maxCapacity": "70",
                "train": "CS-C-1",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": tomorrow.setHours(14, 0, 0),
                "arrival": tomorrow.setHours(15, 0, 0),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-2",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": tomorrow.setHours(15, 0, 0),
                "arrival": tomorrow.setHours(16, 0, 0),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-2",
                "departureStation": "Central Station",
                "arrivalStation": "C"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": tomorrow.setHours(16, 0, 0),
                "arrival": tomorrow.setHours(17, 0, 0),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-3",
                "departureStation": "C",
                "arrivalStation": "Central Station"
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "price": "4.00",
                "departure": tomorrow.setHours(17, 0, 0),
                "arrival": tomorrow.setHours(18, 0, 0),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-3",
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