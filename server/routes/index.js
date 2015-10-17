var authController = require('../controllers/auth');
var passengerController = require('../controllers/passenger');
var express = require('express');
var router = express.Router();

router.use(function (req, res, next) {
    console.log('Request received.');
    next();
});

router.get('/', authController.isAuthenticated, function (req, res) {
    res.json({message: 'hooray! welcome to our api!'});
});

router.route('/passenger')
    .get(passengerController.getPassengers)
    .post(passengerController.postPassenger);

router.route('/passenger/:passenger_id')
    .get(passengerController.getPassenger)
    .put(passengerController.putPassenger)
    .delete(passengerController.deletePassenger);

module.exports = router;