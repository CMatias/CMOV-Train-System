package cmov.goncalobo.trainticketsystem.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.Ticket;
import cmov.goncalobo.trainticketsystem.Others.TicketAdapter;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class MyTicketsActivity extends Activity {

    Context c;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;
        ArrayList<Ticket> jsonTickets;

        Utils._("MyTicketActivity Born!", c);

        setContentView(R.layout.activity_my_tickets);

        Bundle bundle = getIntent().getExtras();
        jsonTickets = parseJsonTickets((String) bundle.get("tickets"));

        lv=(ListView) findViewById(R.id.listView);

        lv.setAdapter(new TicketAdapter(this, jsonTickets,Utils.STATE_MY_TICKETS));

        }

    public ArrayList<Ticket> parseJsonTickets(String t){

        ArrayList<Ticket> tickets = new ArrayList<Ticket>();

        String[] trip = t.substring(4,t.length()).split("\\u007D,\\u007B\"_");


        Ticket newTicket;
        for(int i = 0 ; i < trip.length ; i++) {
            newTicket = new Ticket();
            trip[i] += ",";
            Utils._(trip[i],c);
            String stopsAux = trip[i].substring(trip[i].indexOf("stops"), trip[i].lastIndexOf("]"));
            Utils._(stopsAux, c);
            String[] stops = stopsAux.substring(9,stopsAux.length()).split("\\u007D,\\u007B");
            Utils._(stops[0], c);

            newTicket.setFrom(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("station", trip[i].indexOf("departure"))) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("station", trip[i].indexOf("departure"))))
            ).replace("\"", ""));

            newTicket.setTo(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("station", trip[i].indexOf("arrival"))) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("station", trip[i].indexOf("arrival"))))
            ).replace("\"", ""));

            newTicket.setDeparture(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("date", trip[i].indexOf("departure"))) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("date", trip[i].indexOf("departure"))))
            ).replace("\"", ""));

            newTicket.setArrival(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("date", trip[i].indexOf("arrival"))) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("date", trip[i].indexOf("arrival"))))
            ).replace("\"", ""));

            newTicket.setSeat(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("seat")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("seat")))
            ).replace("\"", ""));

            newTicket.setDuration(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("duration")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("duration")))
            ).replace("\"", ""));

            newTicket.setPrice(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("price")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("price")))
            ).replace("\"", ""));

            newTicket.setIsValid(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("used")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("used")))
            ).replace("\"", ""));

            newTicket.setID(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("id")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("id")))
            ).replace("\"", ""));

            tickets.add(newTicket);
        }
        return tickets;
    }

}
