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
        .exec(function (err, inspector) {
            if (err) {
                res.send(err);
            }
            var trips = inspector._trips;
            var ret = [];
            for(var i = 0; i < trips.length; i++){
                var timeDurDate = new Date(new Date(trips[i].stops[trips[i].stops.length-1].date) - new Date(trips[i].stops[0].date));
                var timeDurObj = {
                    "tripDuration": {
                        "hours": timeDurDate.getHours(),
                        "minutes": timeDurDate.getMinutes(),
                        "seconds": timeDurDate.getSeconds()
                    }
                };
                var retObj = [];
                retObj.push(trips[i]);
                retObj.push(timeDurObj);
                ret.push(retObj);
            }
            res.send(ret);
        });
};


