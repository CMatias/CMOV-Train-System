package cmov.goncalobo.trainticketsystem.Others;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class Utils {

    // CONFIGURATION
    private static final boolean DEBUGGER = false;

    // API ROUTES
    public static final String ROUTE_LOGIN      = "http://146.185.184.229:1337/api/inspector/authenticate";
    public static final String ROUTE_GETTRIPS   = "http://146.185.184.229:1337/api/inspector/trips";
    public static final String ROUTE_GETTICKETS = "http://146.185.184.229:1337/api/tickets/";
    public static final String ROUTE_ENDTRIP    = "http://146.185.184.229:1337/api/tickets/uploadinfo";


    // STATES
    public static final int STATE_LOGIN          = 1;
    public static final int STATE_CONFIRM_TRIP   = 2;
    public static final int STATE_LOAD_TRIPS     = 3;
    public static final int STATE_END_TRIP       = 4;


    // SPINNER VALS
    public static final int STATION_A        = 0;
    public static final int STATION_B        = 1;
    public static final int STATION_C        = 2;
    public static final int CENTRAL_STATION  = 3;

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

