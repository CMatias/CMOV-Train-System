package cmov.goncalobo.trainticketsystem.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import cmov.goncalobo.trainticketsystem.Entities.Ticket;
import cmov.goncalobo.trainticketsystem.Entities.Trip;
import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.User;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class LoggedActivity extends AuthActivity {

    TextView tvWelcome;
    Context c;

    ArrayList<Trip> inspectorTrips;

    User u;
    Spinner tripsSpinner;

    Button bConfirmTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;
        Utils._("LoggedActivity Born!", c);

        setContentView(R.layout.activity_logged);

        Bundle bundle = getIntent().getExtras();
        u = (User) bundle.get("activeUser");

        inspectorTrips = new ArrayList<Trip>();


        getResponse res = new getResponse(c,Utils.STATE_LOAD_TRIPS);
        res.execute(Utils.ROUTE_GETTRIPS);



        tripsSpinner = (Spinner) findViewById(R.id.spTrips);


        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome back " + u.getUsername() + "!");



        bConfirmTrip = (Button) findViewById(R.id.bConfirmTrip);

        bConfirmTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils._(" button hit!", c);
                getResponse res = new getResponse(c,Utils.STATE_CONFIRM_TRIP);
                res.execute( Utils.ROUTE_GETTICKETS, inspectorTrips.get((int)tripsSpinner.getSelectedItemId()).getId());
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
                if(args[0].equals(Utils.ROUTE_GETTICKETS)){
                    try {
                        URL url = new URL(args[0]+args[1]);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
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
                }
                else {
                    try {
                        URL url = new URL(args[0]);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
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
                }

                return result.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent;
            switch (state){
                case Utils.STATE_CONFIRM_TRIP:

                    intent = new Intent(LoggedActivity.this, ActiveTripActivity.class);
                    intent.putExtra("activeUser", u);
                    intent.putExtra("tickets", result);
                    intent.putExtra("tripID", inspectorTrips.get((int)tripsSpinner.getSelectedItemPosition()).getId());
                    startActivity(intent);
                    finish();
                    break;
                case Utils.STATE_LOAD_TRIPS:
                    Utils._(result, c);
                    if(result.equals("[]")) {
                        Utils.toast("You have no trips to inspect.", c);
                        return;
                    }
                    if(result.length()>1) {
                        Utils.toast("Loading trips...", c);
                        String trips[] = result.split("\\u005D,\\u005B");
//                          }}],[{   \u007D\u007D\u005D,\u007B\u007B
                        Trip newTrip;

                        for (int i = 0; i < trips.length; i++) {
                            newTrip = new Trip();
                            Utils._(trips[i], c);
                            Utils._("" + trips.length, c);
                            newTrip.setId(trips[i].substring(
                                    trips[i].indexOf(":", trips[i].indexOf("_id")) + 2,
                                    trips[i].indexOf(",", trips[i].indexOf(":", trips[i].indexOf("_id")))
                            ).replace("\"", "").replace("]", ""));

                            newTrip.setTrain(trips[i].substring(
                                    trips[i].indexOf(":", trips[i].indexOf("train")) + 2,
                                    trips[i].indexOf(",", trips[i].indexOf(":", trips[i].indexOf("train")))
                            ).replace("\"", "").replace("]", ""));

                            newTrip.setDate(trips[i].substring(
                                    trips[i].indexOf(":", trips[i].indexOf("date")) + 2,
                                    trips[i].indexOf(",", trips[i].indexOf(":", trips[i].indexOf("date")))
                            ).replace("\"", "").replace("]", ""));

                            inspectorTrips.add(newTrip);
                            Utils._(newTrip.display(), c);
                        }

                        ArrayList<String> tripsArray = new ArrayList<String>();

                        for (int i = 0; i < inspectorTrips.size(); i++)
                            tripsArray.add(inspectorTrips.get(i).display());

                        ArrayAdapter<String> tripsArrayAdapter = new ArrayAdapter<String>(c, R.layout.spinner_item, tripsArray); //selected item will look like a spinner set from XML
                        tripsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        tripsSpinner.setAdapter(tripsArrayAdapter);
                    } else {
                    Utils.toast("Please connect to the internet.", c);
                    return;
                      }
                    break;
                default: Utils._("Warning, unknown state!", context);
            }
        }

    }

}
