mongoose = require('mongoose');

module.exports = function(fixtures) {

    var today = new Date();
    var tomorrow = new Date(); tomorrow.setDate(today.getDate() + 1);

    fixtures.save('Trip', {
        Trip: [
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "118",
                "maxCapacity": "120",
                "train": "A-B-1",
                "stops": [
                    { station: "A", date: today.setHours(8, 0, 0)},
                    { station: "A-CS", date: today.setHours(8, 52, 30)},
                    { station: "CS", date: today.setHours(9, 45, 0)},
                    { station: "B-CS", date: today.setHours(10, 37, 5)},
                    { station: "B", date: today.setHours(11, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "119",
                "maxCapacity": "120",
                "train": "B-A-1",
                "stops": [
                    { station: "B", date: today.setHours(8, 0, 0)},
                    { station: "B-CS", date: today.setHours(8, 52, 30)},
                    { station: "CS", date: today.setHours(9, 45, 0)},
                    { station: "A-CS", date: today.setHours(10, 37, 5)},
                    { station: "A", date: today.setHours(11, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "119",
                "maxCapacity": "120",
                "train": "A-B-2",
                "stops": [
                    { station: "A", date: today.setHours(13, 0, 0)},
                    { station: "A-CS", date: today.setHours(13, 52, 30)},
                    { station: "CS", date: today.setHours(14, 45, 0)},
                    { station: "B-CS", date: today.setHours(15, 37, 5)},
                    { station: "B", date: today.setHours(16, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "B-A-2",
                "stops": [
                    { station: "B", date: today.setHours(13, 0, 0)},
                    { station: "B-CS", date: today.setHours(13, 52, 30)},
                    { station: "CS", date: today.setHours(14, 45, 0)},
                    { station: "A-CS", date: today.setHours(15, 37, 5)},
                    { station: "A", date: today.setHours(16, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "A-B-3",
                "stops": [
                    { station: "A", date: today.setHours(17, 0, 0)},
                    { station: "A-CS", date: today.setHours(17, 52, 30)},
                    { station: "CS", date: today.setHours(18, 45, 0)},
                    { station: "B-CS", date: today.setHours(19, 37, 5)},
                    { station: "B", date: today.setHours(20, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "B-A-3",
                "stops": [
                    { station: "B", date: today.setHours(17, 0, 0)},
                    { station: "B-CS", date: today.setHours(17, 52, 30)},
                    { station: "CS", date: today.setHours(18, 45, 0)},
                    { station: "A-CS", date: today.setHours(19, 37, 5)},
                    { station: "A", date: today.setHours(20, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-1",
                "stops": [
                    { station: "C", date: today.setHours(10, 0, 0)},
                    { station: "C-CS", date: today.setHours(10, 30, 0)},
                    { station: "CS", date: today.setHours(11, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-1",
                "stops": [
                    { station: "CS", date: today.setHours(10, 0, 0)},
                    { station: "C-CS", date: today.setHours(10, 30, 0)},
                    { station: "C", date: today.setHours(11, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-2",
                "stops": [
                    { station: "C", date: today.setHours(15, 0, 0)},
                    { station: "C-CS", date: today.setHours(15, 30, 0)},
                    { station: "CS", date: today.setHours(16, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-2",
                "stops": [
                    { station: "CS", date: today.setHours(15, 0, 0)},
                    { station: "C-CS", date: today.setHours(15, 30, 0)},
                    { station: "C", date: today.setHours(16, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-3",
                "stops": [
                    { station: "C", date: today.setHours(20, 0, 0)},
                    { station: "C-CS", date: today.setHours(20, 30, 0)},
                    { station: "CS", date: today.setHours(21, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-3",
                "stops": [
                    { station: "CS", date: today.setHours(20, 0, 0)},
                    { station: "C-CS", date: today.setHours(20, 30, 0)},
                    { station: "C", date: today.setHours(21, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "A-B-1",
                "stops": [
                    { station: "A", date: tomorrow.setHours(8, 0, 0)},
                    { station: "A-CS", date: tomorrow.setHours(8, 52, 30)},
                    { station: "CS", date: tomorrow.setHours(9, 45, 0)},
                    { station: "B-CS", date: tomorrow.setHours(10, 37, 5)},
                    { station: "B", date: tomorrow.setHours(11, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "B-A-1",
                "stops": [
                    { station: "B", date: tomorrow.setHours(8, 0, 0)},
                    { station: "B-CS", date: tomorrow.setHours(8, 52, 30)},
                    { station: "CS", date: tomorrow.setHours(9, 45, 0)},
                    { station: "A-CS", date: tomorrow.setHours(10, 37, 5)},
                    { station: "A", date: tomorrow.setHours(11, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "A-B-2",
                "stops": [
                    { station: "A", date: tomorrow.setHours(13, 0, 0)},
                    { station: "A-CS", date: tomorrow.setHours(13, 52, 30)},
                    { station: "CS", date: tomorrow.setHours(14, 45, 0)},
                    { station: "B-CS", date: tomorrow.setHours(15, 37, 5)},
                    { station: "B", date: tomorrow.setHours(16, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "B-A-2",
                "stops": [
                    { station: "B", date: tomorrow.setHours(13, 0, 0)},
                    { station: "B-CS", date: tomorrow.setHours(13, 52, 30)},
                    { station: "CS", date: tomorrow.setHours(14, 45, 0)},
                    { station: "A-CS", date: tomorrow.setHours(15, 37, 5)},
                    { station: "A", date: tomorrow.setHours(16, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "A-B-3",
                "stops": [
                    { station: "A", date: tomorrow.setHours(17, 0, 0)},
                    { station: "A-CS", date: tomorrow.setHours(17, 52, 30)},
                    { station: "CS", date: tomorrow.setHours(18, 45, 0)},
                    { station: "B-CS", date: tomorrow.setHours(19, 37, 5)},
                    { station: "B", date: tomorrow.setHours(20, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "120",
                "maxCapacity": "120",
                "train": "B-A-3",
                "stops": [
                    { station: "B", date: tomorrow.setHours(17, 0, 0)},
                    { station: "B-CS", date: tomorrow.setHours(17, 52, 30)},
                    { station: "CS", date: tomorrow.setHours(18, 45, 0)},
                    { station: "A-CS", date: tomorrow.setHours(19, 37, 5)},
                    { station: "A", date: tomorrow.setHours(20, 30, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-1",
                "stops": [
                    { station: "C", date: tomorrow.setHours(10, 0, 0)},
                    { station: "C-CS", date: tomorrow.setHours(10, 30, 0)},
                    { station: "CS", date: tomorrow.setHours(11, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-1",
                "stops": [
                    { station: "CS", date: tomorrow.setHours(10, 0, 0)},
                    { station: "C-CS", date: tomorrow.setHours(10, 30, 0)},
                    { station: "C", date: tomorrow.setHours(11, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-2",
                "stops": [
                    { station: "C", date: tomorrow.setHours(15, 0, 0)},
                    { station: "C-CS", date: tomorrow.setHours(15, 30, 0)},
                    { station: "CS", date: tomorrow.setHours(16, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-2",
                "stops": [
                    { station: "CS", date: tomorrow.setHours(15, 0, 0)},
                    { station: "C-CS", date: tomorrow.setHours(15, 30, 0)},
                    { station: "C", date: tomorrow.setHours(16, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "C-CS-3",
                "stops": [
                    { station: "C", date: tomorrow.setHours(20, 0, 0)},
                    { station: "C-CS", date: tomorrow.setHours(20, 30, 0)},
                    { station: "CS", date: tomorrow.setHours(21, 0, 0)}
                ]
            },
            {
                "_id": mongoose.Types.ObjectId(),
                "currentCapacity": "70",
                "maxCapacity": "70",
                "train": "CS-C-3",
                "stops": [
                    { station: "CS", date: tomorrow.setHours(20, 0, 0)},
                    { station: "C-CS", date: tomorrow.setHours(20, 30, 0)},
                    { station: "C", date: tomorrow.setHours(21, 0, 0)}
                ]
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