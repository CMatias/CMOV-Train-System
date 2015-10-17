var express = require('express');
var router = express.Router();
var authController = require('./auth');

/*
Using middleware like this can be very powerful.
We can do validations to make sure that everything coming from a request is safe and sound.
We can throw errors here in case something is wrong.
We can do some extra logging for analytics or any statistics we’d like to keep.
*/
router.use(authController.isAuthenticated, function(req, res, next) {
    console.log('Request received.');
    next();
});

router.get('/', authController.isAuthenticated, function(req, res) {
    res.json({ message: 'hooray! welcome to our api!' });
});


router.use('/passengers', require('./passengers'));

module.exports = router;