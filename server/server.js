var express = require('express');                        
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var passport = require('passport');

var app = express(); 

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 1337;

// Use the passport package in our application
app.use(passport.initialize());

app.use('/api', require('./routes'));

app.listen(port);
console.log('Magic happens on port ' + port);

//var mongoURI = 'mongodb://admin:password@ds033744.mongolab.com:33744/cmov-p1';
var mongoURI = 'mongodb://localhost:27017/test';
var MongoDB = mongoose.connect(mongoURI).connection;
MongoDB.on('error', function(err) { console.log(err.message); });
MongoDB.once('open', function() {
  console.log("mongodb connection open");
});
