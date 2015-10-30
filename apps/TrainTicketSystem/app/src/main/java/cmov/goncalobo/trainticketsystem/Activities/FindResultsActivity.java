package cmov.goncalobo.trainticketsystem.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;

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
        /*if(t.indexOf("waitingTime")>0)
            trip = t.substring(4,t.length()).split("\\u005B,\\u005D");*/

        trip = t.substring(4,t.length()).split("\\u007D,\\u007B\"_");

        Ticket newTicket;
        for(int i = 0 ; i < trip.length ; i++) {
            newTicket = new Ticket();
            trip[i] += ",";
            Utils._(trip[i],c);
            String stopsAux = trip[i].substring(trip[i].indexOf("stops"), trip[i].lastIndexOf("]"));
            Utils._(stopsAux, c);
            String[] stops = stopsAux.substring(9,stopsAux.length()).split("\\u007D,\\u007B");
            Utils._(stops[0], c);

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

            newTicket.setPrice(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("price")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("price")))
            ).replace("\"", "").replace("}]",""));

            newTicket.setIsValid(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("used")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("used")))
            ).replace("\"", ""));

            newTicket.setID(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("id")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("id")))
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

}
