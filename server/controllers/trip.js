var Trip = require('../models/trip');
var Ticket =  require('../models/ticket');

exports.getTrips = function(req, res) {
    Trip.find({"currentCapacity": {$gt: 0}}, function (err, trips) {
        if (err) {
            res.send(err);
        }
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

exports.getTripsByDate = function(req, res) {

    var bDate = new Date();
    bDate.setFullYear(req.params.year, req.params.month-1, req.params.day);
    bDate.setHours(0, 0, 0);
    var aDate = new Date();
    aDate.setDate(bDate.getDate() + 1);
    aDate.setHours(0, 0, 0);

    Trip.find({"stops.date": {$gt: bDate, $lt: aDate }, "currentCapacity": {$gt: 0}}, function (err, trips) {
        if (err) {
            res.send(err);
        }
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


exports.getTripsByDateAndStations = function(req, res) {

    var bDate = new Date();
    bDate.setFullYear(req.params.year, req.params.month-1, req.params.day);
    bDate.setHours(0, 0, 0);
    var aDate = new Date();
    aDate.setDate(bDate.getDate() + 1);
    aDate.setHours(0, 0, 0);

    var ret = [];
    var depStation = req.params.departure;
    var arrStation = req.params.arrival;

    //Check for multi ticket scenario
    if( (depStation.indexOf("C") > -1 && (arrStation.indexOf("B") >-1 || arrStation.indexOf("A") > -1)) ||
        (arrStation.indexOf("C") > -1 && (depStation.indexOf("B") >-1 || depStation.indexOf("A") > -1)) ){
        Trip.find({"stops.date": {$gt: bDate, $lt: aDate }}, function (err, trips) {
            if (err) {
                res.send(err);
            }

            for(var i = 0; i < trips.length; i++){
                var stops = trips[i].stops;
                var foundDep = -1;
                var foundCentral = -1;
                for(var j = 0; j < stops.length; j++){
                    if(stops[j].station == depStation){
                        foundDep = j;
                    } else if(stops[j].station == "MS"){
                        foundCentral = j;
                    }
                    if( (stops[j].station == "MS" && foundDep > -1)){
                        trips[i].stops = stops.slice(foundDep, j+1);
                        var temp = trips[i].toObject();
                        var traveledStation = j+1-foundDep;
                        temp.price = traveledStation * 2.50;
                        temp.order = "1";
                        trips[i] = temp;
                        ret.push(trips[i]);
                    } else if (stops[j].station == arrStation && foundCentral > -1){
                        trips[i].stops = stops.slice(foundCentral, j+1);
                        var temp = trips[i].toObject();
                        var traveledStation = j+1-foundCentral;
                        temp.price = traveledStation * 2.50;
                        temp.order = "2";
                        trips[i] = temp;
                        ret.push(trips[i]);
                    }
                }
            }
            prepareRes(null, res, ret);
        });

    } else {
        Trip.find({"stops.date": {$gt: bDate, $lt: aDate }}, function (err, trips) {
            if (err) {
                res.send(err);
            }

            for(var i = 0; i < trips.length; i++){
                var stops = trips[i].stops;
                var foundDep = -1;
                for(var j = 0; j < stops.length; j++){
                    if(stops[j].station == depStation){
                        foundDep = j;
                    }
                    if(stops[j].station == arrStation && foundDep > -1){
                        trips[i].stops = stops.slice(foundDep, j+1);
                        var temp = trips[i].toObject();
                        var traveledStation = j+1-foundDep;
                        temp.price = traveledStation * 2.50;
                        trips[i] = temp;
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
                }
            }

           res.json(ret);
        });
    }
};

var prepareRes = function(err, res, data){

    if(err) {
        throw err;
    }
    else{
        var ret = [];
        for(var i = 0; i < data.length; i++){
            if(data[i].order == "1"){
                for(var j = 0; j < data.length; j++){
                    if(data[j].order == "2"){
                        var retObj = [];
                        var depAtDep =  data[i].stops[0].date;
                        var arrAtMs = data[i].stops[data[i].stops.length-1].date;
                        var arrAtArr =  data[j].stops[data[j].stops.length-1].date;
                        var depAtMs = data[j].stops[0].date;
                        var timeDiff = new Date(depAtMs) - new Date(arrAtMs);
                        var timeDur = new Date(arrAtArr) - new Date(depAtDep);
                        //Check if waiting time less than 5 hours and positive.
                        if(timeDiff > 0 && timeDiff < 18000000) {
                            var timeDiffDate = new Date(timeDiff);;
                            var timeDiffObj = {
                                "waitingTime": {
                                    "hours": timeDiffDate.getHours(),
                                    "minutes": timeDiffDate.getMinutes(),
                                    "seconds": timeDiffDate.getSeconds()
                                }
                            };
                            var timeDurDate = new Date(timeDur);
                            var timeDurObj = {
                                "tripDuration": {
                                    "hours": timeDurDate.getHours(),
                                    "minutes": timeDurDate.getMinutes(),
                                    "seconds": timeDurDate.getSeconds()
                                }
                            };
                            retObj.push(data[i]);
                            retObj.push(data[j]);
                            retObj.push(timeDiffObj);
                            retObj.push(timeDurObj);
                            ret.push(retObj);
                        }
                    }
                }
            }
        }
        res.json(ret);
    }
};


exports.getTrip = function(req, res) {
    Trip.findById(req.params.trip_id, function(err, trip) {
        if (err) {
            res.send(err);
        }
        res.json(trip);
    })
};

exports.reduceCapacity = function(tripId) {
    Trip.findById(tripId, function(err, trip) {
        if (err) {
            throw err;
        }
        trip.currentCapacity = trip.currentCapacity - 1;
        trip.save(function(err) {
            if (err) {
                throw err;
            }
        })
    })
};




