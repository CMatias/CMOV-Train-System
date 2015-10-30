package cmov.goncalobo.trainticketsystem.Others;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class Utils {

    // CONFIGURATION
    private static final boolean DEBUGGER = true;

    // API ROUTES
    public static final String ROUTE_LOGIN      = "http://146.185.184.229:1337/api/authenticate";
    public static final String ROUTE_GETTICKETS = "http://beta.json-generator.com/api/json/get/N10iEVyZe";
    public static final String ROUTE_FINDTRIPS  = "http://146.185.184.229:1337/api/trips";

    // STATES
    public static final int STATE_LOGIN          = 1;
    public static final int STATE_CONFIRM_TRIP   = 2;


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

