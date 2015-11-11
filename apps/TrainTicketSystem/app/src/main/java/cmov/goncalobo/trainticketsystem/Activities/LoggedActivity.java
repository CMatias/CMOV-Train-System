package cmov.goncalobo.trainticketsystem.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cmov.goncalobo.trainticketsystem.Entities.PaymentCard;
import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.User;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class LoggedActivity extends AuthActivity {

    TextView tvWelcome;
    Context c;

    public static User u;

    Button bFindTrip;
    Button bMyTickets;
    Button bSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;
        Utils._("LoggedActivity Born!", c);

        setContentView(R.layout.activity_logged);

        Bundle bundle = getIntent().getExtras();
        u = (User) bundle.get("activeUser");

        getResponse res = new getResponse(c,Utils.STATE_LOAD_PROFILE);
        res.execute(Utils.ROUTE_PROFILE);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome back " + u.getUsername() + "!");

        bFindTrip = (Button) findViewById(R.id.bFindTrip);
        bMyTickets = (Button) findViewById(R.id.bMyTickets);
        bSettings = (Button) findViewById(R.id.bSettings);

        bFindTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils._("Find Ticket button hit!", c);
                getResponse res = new getResponse(c,Utils.STATE_FIND_TRIP);
                res.execute();
            }
        });

        bMyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils._("My Tickets button hit!", c);
                getResponse res = new getResponse(c,Utils.STATE_MY_TICKETS);
                res.execute(Utils.ROUTE_GETTICKETS);
            }
        });

        bSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils._("Settings button hit!", c);
                getResponse res = new getResponse(c,Utils.STATE_SETTINGS);
                res.execute();
            }
        });


    }

    public class getResponse extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;
        Context context;
        int state;

        public getResponse(Context c, int s) {
            context = c;
            state = s;
        }

        @Override
        protected String doInBackground(String... args) {
            StringBuilder result = new StringBuilder();
            if(args.length!=0) {
                Utils._(u.getToken(),c);
                try {
                    URL url = new URL(args[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("x-access-token", u.getToken());

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

                return result.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent;
            switch (state){
                case Utils.STATE_FIND_TRIP:
                    intent = new Intent(LoggedActivity.this, FindTripActivity.class);
                    intent.putExtra("activeUser", u);
                    startActivity(intent);
                    break;
                case Utils.STATE_MY_TICKETS:
                    intent = new Intent(LoggedActivity.this, MyTicketsActivity.class);
                    Utils._(result, c);
                    if(!result.equals("[]")) {
                        intent.putExtra("tickets", result);
                        //intent.putExtra("activeUser", u);
                        intent.putExtra("offline", "false");
                        startActivity(intent);
                    } else {
                        Utils.toast("You have no tickets.",c);
                    }
                    break;
                case Utils.STATE_SETTINGS:
                    intent = new Intent(LoggedActivity.this, SettingsActivity.class);
                    intent.putExtra("activeUser", u);
                    startActivity(intent);
                    break;
                case Utils.STATE_LOAD_PROFILE:
                    Utils._(result,c);
                    parseUserInfo(result);
                    break;
                default: Utils._("Warning, unknown state!", context);
            }
        }

    }

    private void parseUserInfo(String t) {

            ArrayList<PaymentCard> cards = new ArrayList<PaymentCard>();
            u.clearCards();
        u.setID(t.substring(
                t.indexOf(":", t.indexOf("_id")) + 1,
                t.indexOf(",", t.indexOf(":", t.indexOf("_id")))
        ).replace("\"", ""));

            String[] aux = t.substring(t.indexOf("["), t.indexOf("]")).split("\\u007D,\\u007B");

            Utils._(aux[0],c);

            PaymentCard newCard;
            for(int i = 0 ; i < aux.length ; i++) {
                newCard = new PaymentCard();
                aux[i] += ",";
                newCard.setType(aux[i].substring(
                        aux[i].indexOf(":", aux[i].indexOf("type")) + 1,
                        aux[i].indexOf(",", aux[i].indexOf(":", aux[i].indexOf("type")))
                ).replace("\"", ""));

                newCard.setNumber(aux[i].substring(
                        aux[i].indexOf(":", aux[i].indexOf("number")) + 1,
                        aux[i].indexOf(",", aux[i].indexOf(":", aux[i].indexOf("number")))
                ).replace("\"", ""));

                newCard.setValidity(aux[i].substring(
                        aux[i].indexOf(":", aux[i].indexOf("validity")) + 1,
                        aux[i].indexOf(",", aux[i].indexOf(":", aux[i].indexOf("validity")))
                ).replace("\"", ""));

                newCard.setID(aux[i].substring(
                        aux[i].indexOf(":", aux[i].indexOf("_id")) + 1,
                        aux[i].indexOf(",", aux[i].indexOf(":", aux[i].indexOf("_id")))
                ).replace("\"", ""));

                u.addCard(newCard);
                Utils._(newCard.display(1),c);
            }
    }

}
