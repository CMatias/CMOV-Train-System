package cmov.goncalobo.trainticketsystem.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.Ticket;
import cmov.goncalobo.trainticketsystem.Others.TicketAdapter;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class FindResultsActivity extends Activity {

    Context c;
    ListView lv;
    ScrollView svResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;
        ArrayList<Ticket> jsonTickets;

        Utils._("FindResultsActivity Born!", c);

        setContentView(R.layout.activity_find_results);

        Bundle bundle = getIntent().getExtras();

        jsonTickets = parseJsonTickets((String) bundle.get("tickets"));

        lv=(ListView) findViewById(R.id.lvResults);

        lv.setAdapter(new TicketAdapter(this, jsonTickets, Utils.STATE_FIND_RESULTS));

    }



    public ArrayList<Ticket> parseJsonTickets(String t){

        ArrayList<Ticket> tickets = new ArrayList<Ticket>();

        String[] trip;

        boolean multitrip = false;

        if(t.indexOf("waitingTime")>0) {
            multitrip = true;
            return parseJsonMultiTickets(t);
        }
        else  trip = t.substring(4,t.length()).split("\\u007D\\u007D\\u005D,\\u005B\\u007B");

        Ticket newTicket;
        for(int i = 0 ; i < trip.length ; i++) {
            newTicket = new Ticket();

            trip[i] += ",";
            Utils._(trip[i], c);
            String stopsAux = trip[i].substring(trip[i].indexOf("stops"), trip[i].lastIndexOf("price"));
            Utils._(stopsAux, c);

            String[] stops = stopsAux.substring(9,stopsAux.length()).split("\\u007D,\\u007B");
            for(int j=0;j<stops.length;j++)
                Utils._(j+" - " + stops[j], c);

            newTicket.setFrom(stops[0].substring(
                    stops[0].indexOf(":", stops[0].indexOf("station")) + 1,
                    stops[0].indexOf(",", stops[0].indexOf(":", stops[0].indexOf("station")))
            ).replace("\"", ""));

            newTicket.setTo(stops[stops.length - 1].substring(
                    stops[stops.length - 1].indexOf(":", stops[stops.length - 1].indexOf("station")) + 1,
                    stops[stops.length - 1].indexOf(",", stops[stops.length - 1].indexOf(":", stops[stops.length - 1].indexOf("station")))
            ).replace("\"", ""));

            newTicket.setDeparture(stops[0].substring(
                    stops[0].indexOf(":", stops[0].indexOf("date")) + 1,
                    stops[0].indexOf(",", stops[0].indexOf(":", stops[0].indexOf("date")))
            ).replace("\"", ""));


            newTicket.setArrival(stops[stops.length - 1].substring(
                    stops[stops.length - 1].indexOf(":", stops[stops.length - 1].indexOf("date")) + 1,
                    stops[stops.length - 1].indexOf(",", stops[stops.length - 1].indexOf(":", stops[stops.length - 1].indexOf("date")))
            ).replace("\"", ""));

            newTicket.setDepartureMS(stops[1].substring(
                    stops[1].indexOf(":", stops[1].indexOf("date")) + 1,
                    stops[1].indexOf(",", stops[1].indexOf(":", stops[1].indexOf("date")))
            ).replace("\"", ""));

            newTicket.setArrivalMS(stops[2].substring(
                    stops[2].indexOf(":", stops[2].indexOf("date")) + 1,
                    stops[2].indexOf(",", stops[2].indexOf(":", stops[2].indexOf("date")))
            ).replace("\"", ""));

            newTicket.setSeat(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("seat")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("seat")))
            ).replace("\"", ""));


            String priceArray[] = new String[2];
            priceArray[0] = trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("price")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("price")))
            ).replace("\"", "").replace("}","").replace("]","");

            newTicket.setPrice(priceArray);

            newTicket.setIsValid(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("used")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("used")))
            ).replace("\"", ""));


            String tripIDArray[] = new String[2];
            tripIDArray[0] = trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("id")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("id")))
            ).replace("\"", "");
            newTicket.setTripID(tripIDArray);

            newTicket.setDuration(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("hours", trip[i].indexOf("tripDuration"))) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("hours", trip[i].indexOf("tripDuration"))))
            ).replace("\"", "") + "h" +
                    trip[i].substring(
                            trip[i].indexOf(":", trip[i].indexOf("minutes", trip[i].indexOf("tripDuration"))) + 1,
                            trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("minutes", trip[i].indexOf("tripDuration"))))
                    ).replace("\"", ""));


            newTicket.setMaxCapacity(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("maxCapacity")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("maxCapacity")))
            ).replace("\"", ""));

            newTicket.setMaxCapacity(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("currentCapacity")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("currentCapacity")))
            ).replace("\"", ""));

            tickets.add(newTicket);
        }
        return tickets;
    }

    private ArrayList<Ticket> parseJsonMultiTickets(String t) {

        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        String[] trip = t.substring(4,t.length()).split("\\u007D\\u007D\\u005D,\\u005B\\u007B\"");

        Ticket newTicket;
        for(int i = 0 ; i < trip.length ; i++) {
            newTicket = new Ticket();

            String[] separateTripFromInfo  = trip[i].split("\"order\":\"2\"");
            String[] multiTrip  = separateTripFromInfo[0].split("\"order\":\"1\"");

            String stopsAux;
            String[] firstStops;
            String[] secondStops;

            stopsAux = multiTrip[0].substring(multiTrip[0].indexOf("stops"), multiTrip[0].lastIndexOf("]"));
            firstStops = stopsAux.substring(9, stopsAux.length()).split("\\u007D,\\u007B");
            stopsAux = multiTrip[1].substring(multiTrip[1].indexOf("stops"), multiTrip[1].lastIndexOf("]"));
            secondStops = stopsAux.substring(9, stopsAux.length()).split("\\u007D,\\u007B");


            newTicket.setFrom(firstStops[0].substring(
                    firstStops[0].indexOf(":", firstStops[0].indexOf("station")) + 1,
                    firstStops[0].indexOf(",", firstStops[0].indexOf(":", firstStops[0].indexOf("station")))
            ).replace("\"", ""));

            newTicket.setTo(secondStops[2].substring(
                    secondStops[2].indexOf(":", secondStops[2].indexOf("station")) + 1,
                    secondStops[2].indexOf(",", secondStops[2].indexOf(":", secondStops[2].indexOf("station")))
            ).replace("\"", ""));

            newTicket.setDeparture(firstStops[0].substring(
                    firstStops[0].indexOf(":", firstStops[0].indexOf("date")) + 1,
                    firstStops[0].indexOf(",", firstStops[0].indexOf(":", firstStops[0].indexOf("date")))
            ).replace("\"", ""));

            newTicket.setArrival(secondStops[2].substring(
                    secondStops[2].indexOf(":", secondStops[2].indexOf("date")) + 1,
                    secondStops[2].indexOf(",", secondStops[2].indexOf(":", secondStops[2].indexOf("date")))
            ).replace("\"", ""));

            newTicket.setArrivalMS(firstStops[0].substring(
                    firstStops[2].indexOf(":", firstStops[2].indexOf("date")) + 1,
                    firstStops[2].indexOf(",", firstStops[2].indexOf(":", firstStops[0].indexOf("date")))
            ).replace("\"", ""));

            newTicket.setDepartureMS(secondStops[0].substring(
                    secondStops[0].indexOf(":", secondStops[0].indexOf("date")) + 1,
                    secondStops[0].indexOf(",", secondStops[0].indexOf(":", secondStops[0].indexOf("date")))
            ).replace("\"", ""));

            newTicket.setDuration(separateTripFromInfo[1].substring(
                    separateTripFromInfo[1].indexOf(":", separateTripFromInfo[1].indexOf("hours", separateTripFromInfo[1].indexOf("tripDuration"))) + 1,
                    separateTripFromInfo[1].indexOf(",", separateTripFromInfo[1].indexOf(":", separateTripFromInfo[1].indexOf("hours", separateTripFromInfo[1].indexOf("tripDuration"))))
            ).replace("\"", "") + "h" +
                    separateTripFromInfo[1].substring(
                            separateTripFromInfo[1].indexOf(":", separateTripFromInfo[1].indexOf("minutes", separateTripFromInfo[1].indexOf("tripDuration"))) + 1,
                            separateTripFromInfo[1].indexOf(",", separateTripFromInfo[1].indexOf(":", separateTripFromInfo[1].indexOf("minutes", separateTripFromInfo[1].indexOf("tripDuration"))))
                    ).replace("\"", ""));

            newTicket.setWaitingTime(separateTripFromInfo[1].substring(
                            separateTripFromInfo[1].indexOf(":", separateTripFromInfo[1].indexOf("hours", separateTripFromInfo[1].indexOf("waitingTime"))) + 1,
                            separateTripFromInfo[1].indexOf(",", separateTripFromInfo[1].indexOf(":", separateTripFromInfo[1].indexOf("hours", separateTripFromInfo[1].indexOf("waitingTime"))))
                    ).replace("\"", ""),
                    separateTripFromInfo[1].substring(
                            separateTripFromInfo[1].indexOf(":", separateTripFromInfo[1].indexOf("minutes", separateTripFromInfo[1].indexOf("waitingTime"))) + 1,
                            separateTripFromInfo[1].indexOf(",", separateTripFromInfo[1].indexOf(":", separateTripFromInfo[1].indexOf("minutes", separateTripFromInfo[1].indexOf("waitingTime"))))
                    ).replace("\"", ""));


            String priceArray[] = new String[2];
            priceArray[0] = multiTrip[0].substring(
                    multiTrip[0].indexOf(":",multiTrip[0].indexOf("price")) + 1,
                    multiTrip[0].indexOf(",",multiTrip[0].indexOf(":", multiTrip[0].indexOf("price")))
            ).replace("\"", "");
            priceArray[1] =  multiTrip[1].substring(
                    multiTrip[1].indexOf(":",multiTrip[1].indexOf("price")) + 1,
                    multiTrip[1].indexOf(",",multiTrip[1].indexOf(":", multiTrip[1].indexOf("price")))
            ).replace("\"", "");
            newTicket.setPrice(priceArray);

            String tripIDArray[] = new String[2];
            tripIDArray[0] = multiTrip[0].substring(
                    multiTrip[0].indexOf(":", multiTrip[0].indexOf("_id")) + 1,
                    multiTrip[0].indexOf(",", multiTrip[0].indexOf(":", multiTrip[0].indexOf("_id")))
            ).replace("\"", "");
            tripIDArray[1] = multiTrip[1].substring(
                    multiTrip[1].indexOf(":", multiTrip[1].indexOf("_id")) + 1,
                    multiTrip[1].indexOf(",", multiTrip[1].indexOf(":", multiTrip[1].indexOf("_id")))
            ).replace("\"", "");
            newTicket.setTripID(tripIDArray);

            newTicket.setMaxCapacity(multiTrip[0].substring(
                    multiTrip[0].indexOf(":", multiTrip[0].indexOf("maxCapacity")) + 1,
                    multiTrip[0].indexOf(",", multiTrip[0].indexOf(":", multiTrip[0].indexOf("maxCapacity")))
            ).replace("\"", ""));

            newTicket.setCapacity(multiTrip[0].substring(
                    multiTrip[0].indexOf(":", multiTrip[0].indexOf("currentCapacity")) + 1,
                    multiTrip[0].indexOf(",", multiTrip[0].indexOf(":", multiTrip[0].indexOf("currentCapacity")))
            ).replace("\"", ""));

            tickets.add(newTicket);
            Utils._(newTicket.display(1),c);
        }
        return tickets;

    }



}
