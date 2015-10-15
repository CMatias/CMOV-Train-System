var express = require('express');
var router = express.Router();

/*
Using middleware like this can be very powerful. 
We can do validations to make sure that everything coming from a request is safe and sound. 
We can throw errors here in case something is wrong. 
We can do some extra logging for analytics or any statistics we’d like to keep.
*/
router.use(function(req, res, next) {
    console.log('Request Received.');
    next();
});

router.get('/', function(req, res) {
    res.json({ message: 'hooray! welcome to our api!' });   
});


router.use('/users', require('./users'));

module.exports = router