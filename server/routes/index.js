var authController = require('../controllers/auth');
var passengerController = require('../controllers/passenger');
var express = require('express');
var router = express.Router();

router.use(function (req, res, next) {
    console.log('Request received.');
    next();
});

router.get('/', authController.isAuthenticated, function (req, res) {
    res.json(authController.isAuthenticated, {message: 'hooray! welcome to our api!'});
});

router.route('/passengers')
    .get(passengerController.getPassengers)
    .post(passengerController.postPassenger);

router.route('/passengers/:passenger_id')
    .get(authController.isAuthenticated, passengerController.getPassenger)
    .put(authController.isAuthenticated, passengerController.putPassenger)
    .delete(authController.isAuthenticated, passengerController.deletePassenger);

module.exports = router;