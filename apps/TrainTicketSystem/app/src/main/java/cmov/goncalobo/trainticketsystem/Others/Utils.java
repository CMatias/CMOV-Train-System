package cmov.goncalobo.trainticketsystem.Others;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class Utils {

    // CONFIGURATION
    private static final boolean DEBUGGER = false;

    // API ROUTES
    public static final String ROUTE_LOGIN      = "http://146.185.184.229:1337/api/passenger/authenticate";
    public static final String ROUTE_SIGNUP     = "http://146.185.184.229:1337/api/passenger";
    public static final String ROUTE_GETTICKETS = "http://146.185.184.229:1337/api/tickets";
    public static final String ROUTE_FINDTRIPS  = "http://146.185.184.229:1337/api/trips";
    public static final String ROUTE_PROFILE    = "http://146.185.184.229:1337/api/passenger";
    public static final String ROUTE_ADDCARDS   = "http://146.185.184.229:1337/api/passenger/creditcards";
    public static final String ROUTE_BUY_TICKET = "http://146.185.184.229:1337/api/tickets";
    public static final String ROUTE_GET_SEATS  = "http://146.185.184.229:1337/api/trip/seats";

    // INTERNAL STORAGE FILES
    public static final  String REMEMBER_ME_FILE = "remember_me";
    public static final  String OFFLINE_TICKETS_FILE = "offline_tickets";


    // STATES
    public static final int STATE_LOGIN          = 1;
    public static final int STATE_SIGNUP         = 2;
    public static final int STATE_FIND_TRIP      = 3;
    public static final int STATE_MY_TICKETS     = 4;
    public static final int STATE_SETTINGS       = 5;
    public static final int STATE_FIND_RESULTS   = 6;
    public static final int STATE_ADDCARD        = 7;
    public static final int STATE_LOAD_PROFILE   = 8;
    public static final int STATE_BUY_TICKET     = 9;
    public static final int STATE_GET_SEATS      = 10;


    // REGEX PATTERNS
    public static final String EMAIL_PATTERN     = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String VALIDITY_PATTERN  = "[0-9][0-9][0-9][0-9]/[0-9][0-9]";
    public static final String CARD_NUMBER_PATTERN  = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";

    // SPINNER VALS
    public static final int STATION_A        = 0;
    public static final int STATION_B        = 1;
    public static final int STATION_C        = 2;
    public static final int MAIN_STATION  = 3;

    // CARD TYPE VALS
    public static final int CREDIT_TYPE  = 0;
    public static final int DEBIT_TYPE   = 1;

    // AUX FUNCTIONS
    public static void _(String s, Context c) {
        if(DEBUGGER)
            Log.i("TrainTicketSystem", " \n" + c.getClass().getSimpleName().toString() + "\n ->  " + s + "\n\n ");
    }

    public static void toast(String s, Context c) {
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }
}

