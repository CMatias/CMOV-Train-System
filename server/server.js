var express = require('express');                        
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var morgan = require('morgan');
var routes = require('./routes');
var config = require('./config');

var app = express();
var port = process.env.PORT || 1337;

app.set('superSecret', config.secret);
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(morgan('dev'));

var MongoDB = mongoose.connect(config.database).connection;
MongoDB.on('error', function(err) { console.log(err.message); });
MongoDB.once('open', function() {
  console.log("MongoDB connection open");
  require('./test/fixtures');
});

app.use('/api', routes);
app.listen(port);
console.log('Magic happens on port ' + port);