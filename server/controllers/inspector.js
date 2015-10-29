var Inspector = require('../models/inspector');

exports.getInspectors = function(req, res) {
    Inspector
        .find()
        .populate("_trips")
        .exec(function (err, inspectors) {
        if (err) {
            res.send(err);
        }
        res.json(inspectors);
    });
};

exports.getTrips = function(req, res) {
    Inspector
        .findById(req.decoded._id)
        .populate("_trips")
        .exec(function (err, inspectors) {
            if (err) {
                res.send(err);
            }
            res.json(inspectors._trips);
        });
};


