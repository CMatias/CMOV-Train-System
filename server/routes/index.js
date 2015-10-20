var express = require('express');
var router = express.Router();

var authController = require('../controllers/auth');
var passengerController = require('../controllers/passenger');
var ticketController = require('../controllers/ticket');
var tripController = require('../controllers/trip');
var stationController = require('../controllers/station');

router.use(function (req, res, next) {
    console.log('Request received.');
    next();
});

router.get('/', authController.isAuthenticated, function (req, res) {
    res.json(authController.isAuthenticated, {message: 'Welcome to CMOV TrainTicketSystem API!'});
});

//Passenger Routes
router.route('/passengers')
    .get(passengerController.getPassengers)
    .post(passengerController.postPassenger);

router.route('/passengers/:passenger_id')
    .get(authController.isAuthenticated, passengerController.getPassenger)
    .put(authController.isAuthenticated, passengerController.putPassenger)
    .delete(authController.isAuthenticated, passengerController.deletePassenger);

//Ticket Routes
router.route('/ticket')
    .get(ticketController.getTickets)
    .post(ticketController.postTicket);

router.route('/ticket/:ticket_id')
    .get(authController.isAuthenticated, ticketController.getTicket)
    .put(authController.isAuthenticated, ticketController.putTicket)
    .delete(authController.isAuthenticated, ticketController.deleteTicket);

//Trip Routes
router.route('/trip')
    .get(tripController.getTrips)
    .post(tripController.postTrip);

router.route('/trip/:trip_id')
    .get(authController.isAuthenticated, tripController.getTrip)
    .put(authController.isAuthenticated, tripController.putTrip)
    .delete(authController.isAuthenticated, tripController.deleteTrip);

//Station Routes
router.route('/station')
    .get(stationController.getStations)
    .post(stationController.postStation);

router.route('/station/:station_id')
    .get(authController.isAuthenticated, stationController.getStation)
    .put(authController.isAuthenticated, stationController.putStation)
    .delete(authController.isAuthenticated, stationController.deleteStation);

module.exports = router;