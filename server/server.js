var express = require('express');                        
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var passport = require('passport');
var routes = require('./routes');
var config = require('./config');

var app = express();
var port = process.env.PORT || 1337;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(passport.initialize());
app.use('/api', routes);
app.listen(port);
console.log('Magic happens on port ' + port);

var MongoDB = mongoose.connect(config.database).connection;
MongoDB.on('error', function(err) { console.log(err.message); });
MongoDB.once('open', function() {
  console.log("MongoDB connection open");
  require('./test/fixtures');
});

