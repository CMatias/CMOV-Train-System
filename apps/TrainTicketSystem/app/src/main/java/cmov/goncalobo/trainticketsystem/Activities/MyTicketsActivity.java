package cmov.goncalobo.trainticketsystem.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.Ticket;
import cmov.goncalobo.trainticketsystem.Others.TicketAdapter;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class MyTicketsActivity extends Activity {

    String data;

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

        if(((String) bundle.get("offline")).equals("false")) {
            data = (String) bundle.get("tickets");

            try {
                FileOutputStream fOut = openFileOutput(Utils.OFFLINE_TICKETS_FILE, MODE_WORLD_READABLE);
                fOut.write(data.getBytes());
                fOut.close();
                Utils.toast("Tickets stored for offline mode.", c);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        lv=(ListView) findViewById(R.id.listView);

        lv.setAdapter(new TicketAdapter(this, jsonTickets,Utils.STATE_MY_TICKETS));

        }

    public ArrayList<Ticket> parseJsonTickets(String t){

        ArrayList<Ticket> tickets = new ArrayList<Ticket>();

        String[] trip = t.substring(4,t.length()).split("\\u007D\\u007D,\\u007B");


        Ticket newTicket;
        for(int i = 0 ; i < trip.length ; i++) {
            newTicket = new Ticket();
            trip[i] += ",";
            Utils._(trip[i], c);

            String stopsAux;
            String[] stops;
            if (trip[i].indexOf("stops") > 0)
            {
                stopsAux = trip[i].substring(trip[i].indexOf("stops"), trip[i].lastIndexOf("]"));
                //Utils._(stopsAux, c);
                stops = stopsAux.substring(9, stopsAux.length()).split("\\u007D,\\u007B");
                //Utils._(stops[0], c);
            }



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
                    trip[i].indexOf(":", trip[i].indexOf("seats")) + 2,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("seats")))
            ).replace("\"", "").replace("]", ""));

            newTicket.setSignature(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("signature")) + 2,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("signature")))
            ).replace("\"", "").replace("]", ""));

            newTicket.setDuration(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("duration")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("duration")))
            ).replace("\"", ""));

            String priceArray[] = new String[2];
            priceArray[0] = trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("price")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("price")))
            ).replace("\"", "");
            newTicket.setPrice(priceArray);

            newTicket.setIsValid(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("used")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("used")))
            ).replace("\"", ""));

            String tripIDArray[] = new String[2];
            tripIDArray[0] = trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("_id", trip[i].indexOf("trips"))) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("_id", trip[i].indexOf("trips"))))
            ).replace("\"", "");
            newTicket.setTripID(tripIDArray);

            newTicket.setID(trip[i].substring(
                    trip[i].indexOf(":", trip[i].indexOf("id")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("id")))
            ).replace("\"", ""));


            tickets.add(newTicket);
        }
        return tickets;
    }

}
