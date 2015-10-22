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

exports.postInspector = function(req, res) {

    var inspector = new Inspector();

    inspector.username = req.body.username;
    inspector.email = req.body.email;
    inspector.password = req.body.password;
    inspector._trips = req.body.tripids;

    inspector.save(function(err) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Inspector created with username: ' + req.body.username + '.' });
    })
};

exports.getInspector = function(req, res) {
    Inspector
        .findById(req.params.inspector_id)
        .populate("_trips")
        .exec(function(err, inspector) {
        if (err) {
            res.send(err);
        }
        res.json(inspector);
    })
};

exports.putInspector = function(req, res) {
    Inspector.findById(req.params.inspector_id, function(err, inspector) {
        if (err) {
            res.send(err);
        }

        inspector.username = req.body.username;
        inspector.email = req.body.email;
        inspector.password = req.body.password;
        inspector._trips = req.body.tripids;

        inspector.save(function(err) {
            if (err) {
                res.send(err);
            }
            res.json({ message: 'Inspector ' + red.body.username + ' updated!' });
        })
    })
};

exports.deleteInspector = function(req, res) {
    Inspector.remove({
        _id: req.params.inspector_id
    }, function(err, inspector) {
        if (err) {
            res.send(err);
        }
        res.json({ message: 'Inspector successfully deleted.' });
    });
};

