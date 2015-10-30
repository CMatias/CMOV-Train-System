var express = require('express');
var router = express.Router();
var jwt = require('jsonwebtoken');

var authController = require('../controllers/auth');
var passengerController = require('../controllers/passenger');
var inspectorController = require('../controllers/inspector');
var ticketController = require('../controllers/ticket');
var tripController = require('../controllers/trip');


router.route('/passenger/authenticate')
    .post(authController.authPassenger);

router.route('/inspector/authenticate')
    .post(authController.authInspector);

router.route('/passenger')
    .post(passengerController.postPassenger);

router.use(function(req, res, next) {
    var token = req.body.token || req.query.token || req.headers['x-access-token'];

    if (token) {
        // verifies secret and checks exp
        jwt.verify(token, req.app.get('superSecret'), function(err, decoded) {
            if (err) {
                return res.json({ success: false, message: 'Failed to authenticate token.' });
            } else {
                req.decoded = decoded;
                next();
            }
        });

    } else {
        return res.status(403).send({
            success: false,
            message: 'No token provided.'
        });
    }
});

router.get('/', function (req, res) {
    res.json({message: 'Welcome to CMOV TrainTicketSystem API!'});
});

//Passenger Routes
router.route('/passengers')
    .get(passengerController.getPassengers);

router.route('/passenger')
    .get(passengerController.getPassenger)
    .put(passengerController.putPassenger)
    .delete(passengerController.deletePassenger);

router.route('/passenger/creditcards')
    .get(passengerController.getCreditCards)
    .post(passengerController.postCreditCards);


//Inspector Routes
router.route('/inspectors')
    .get(inspectorController.getInspectors);

router.route('/inspector/trips')
    .get(inspectorController.getTrips);

//Ticket Routes
router.route('/tickets')
    .get(ticketController.getTickets)
    .post(ticketController.postTicket);

router.route('/tickets/:trip_id')
    .get(ticketController.getTicketsByTrip);

router.route('/tickets/uploadinfo')
    .post(tripController.putTripInfo);

//Trip Routes
router.route('/trips')
    .get(tripController.getTrips);

router.route('/trips/:year/:month/:day')
    .get(tripController.getTripsByDate);

router.route('/trips/:year/:month/:day/:departure/:arrival')
    .get(tripController.getTripsByDateAndStations);

router.route('/trip/:trip_id')
    .get(tripController.getTrip);

router.route('/trip/seats/:trip_id')
    .get(tripController.getTripSeats);

module.exports = router;