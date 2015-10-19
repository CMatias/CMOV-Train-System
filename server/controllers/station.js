var Station = require('../models/station');

exports.getStations = function(req, res) {
    Station.find(function (err, stations) {
        if (err) {
            res.send(err);
        }
        res.json(stations);
    });
};

exports.postStation = function(req, res) {

    var station = new Station();

    station.name = req.body.name;
    station.timetable = req.body.timetable;

    station.save(function(err) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Station created.' });
    })
};

exports.getStation = function(req, res) {
    Station.findById(req.params.station_id, function(err, station) {
        if (err) {
            res.send(err);
        }
        res.json(station);
    })
};

exports.putStation = function(req, res) {
    Station.findById(req.params.station_id, function(err, station) {
        if (err) {
            res.send(err);
        }

        station.name = req.body.name;
        station.timetable = req.body.timetable;

        station.save(function(err) {
            if (err) {
                res.send(err);
            }
            res.json({ message: 'Station updated!' });
        })
    })
};

exports.deleteStation = function(req, res) {
    Station.remove({
        _id: req.params.station_id
    }, function(err, station) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Station successfully deleted.' });
    });
};

