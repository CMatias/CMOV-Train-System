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

import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.User;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class LoggedActivity extends AuthActivity {

    TextView tvWelcome;
    Context c;

    User u;

    Button bConfirmTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;
        Utils._("LoggedActivity Born!", c);

        setContentView(R.layout.activity_logged);

        Bundle bundle = getIntent().getExtras();
        u = (User) bundle.get("activeUser");

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome back " + u.getUsername() + "!");

        bConfirmTrip = (Button) findViewById(R.id.bConfirmTrip);

        bConfirmTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils._(" button hit!", c);
                getResponse res = new getResponse(c,Utils.STATE_CONFIRM_TRIP);
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
                case Utils.STATE_CONFIRM_TRIP:
                    intent = new Intent(LoggedActivity.this, ActiveTripActivity.class);
                    intent.putExtra("activeUser", u);
                    startActivity(intent);
                    break;
                default: Utils._("Warning, unknown state!", context);
            }
        }

    }

}
