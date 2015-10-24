var express = require('express');
var router = express.Router();

var authController = require('../controllers/auth');
var passengerController = require('../controllers/passenger');
var inspectorController = require('../controllers/inspector');
var ticketController = require('../controllers/ticket');
var tripController = require('../controllers/trip');

router.use(function (req, res, next) {
    console.log('Request received.');
    next();
});

router.get('/', function (req, res) {
    res.json({message: 'Welcome to CMOV TrainTicketSystem API!'});
});

//Passenger Routes
router.route('/authenticate')
    .post(authController.authPassenger);

router.route('/passengers')
    .get(passengerController.getPassengers)
    .post(passengerController.postPassenger);

router.route('/passengers/:passenger_id')
    .get(passengerController.getPassenger)
    .put(passengerController.putPassenger)
    .delete(passengerController.deletePassenger);

//Inspector Routes
router.route('/inspectors')
    .get(inspectorController.getInspectors)
    .post(inspectorController.postInspector);

router.route('/inspectors/:inspector_id')
    .get(inspectorController.getInspector)
    .put(inspectorController.putInspector)
    .delete(inspectorController.deleteInspector);

//Ticket Routes
router.route('/tickets')
    .get(ticketController.getTickets)
    .post(ticketController.postTicket);

router.route('/passengertickets')
    .get(ticketController.getTicketsByUser);

//Trip Routes
router.route('/trips')
    .get(tripController.getTrips)
    .post(tripController.postTrip);

router.route('/trips/:year/:month/:day')
    .get(tripController.getTripsByDate);

router.route('/trips/:year/:month/:day/:departure/:arrival')
    .get(tripController.getTripsByDateAndStations);

router.route('/trip/:trip_id')
    .get(tripController.getTrip);

module.exports = router;