var express = require('express');                        
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var User = require('./models/user');
var routes = require('./routes');

var app = express(); 

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 1337;        

app.use(routes);

app.listen(port);
console.log('Magic happens on port ' + port);

mongoose.connect('mongodb://admin:password@ds033754.mongolab.com:33754/cmov-p1');