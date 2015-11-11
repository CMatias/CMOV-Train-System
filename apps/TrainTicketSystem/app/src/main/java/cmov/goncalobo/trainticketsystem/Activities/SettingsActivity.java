package cmov.goncalobo.trainticketsystem.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.User;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class SettingsActivity extends Activity {

    Context c;

    String type, cardnumber, validity;

    User u;

    EditText etCardNumber, etValidity;
    RadioGroup rbType;
    Button bAddCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;
        Utils._("SettingsActivity Born!", c);

        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras();
        u = (User) bundle.get("activeUser");

        etCardNumber = (EditText) findViewById(R.id.etCardNumber);
        etValidity = (EditText) findViewById(R.id.etValidity);
        rbType = (RadioGroup) findViewById(R.id.rgHolder);

        bAddCard = (Button) findViewById(R.id.bAddCard);

        bAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( etCardNumber.length() == 0 || etValidity.length() == 0) {
                    Utils.toast("All fields are required!", c);
                    return;
                }
                if(!etCardNumber.getText().toString().matches(Utils.CARD_NUMBER_PATTERN)){
                    Utils.toast("Card number consists of a sequence of 16 digits.",c);
                    return;
                }

                String validity = etValidity.getText().toString();

                if (!validity.matches(Utils.VALIDITY_PATTERN)) {
                    Utils.toast("Please use the standard YYYY/MM input for card validity.", c);
                    return;
                }
                // current year and month
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int month = Calendar.getInstance().get(Calendar.MONTH);

                if(Integer.parseInt(validity.substring(0, validity.indexOf("/")))>=year){
                if(Integer.parseInt(validity.substring(0, validity.indexOf("/")))==year)
                    if(Integer.parseInt(validity.substring(validity.indexOf("/")+1,validity.length()))<=month){
                        Utils.toast("Validity can't be a date in the past", c);
                        return;
                    }
                    if(Integer.parseInt(validity.substring(validity.indexOf("/")+1,validity.length()))>12){
                        Utils.toast("Invalid month number.", c);
                        return;
                    }
                }
                else {
                    Utils.toast("Validity can't be a date in the past", c);
                    return;
                }



                cardnumber = etCardNumber.getText().toString();
                validity = etValidity.getText().toString().replace("/", "-");

                int t = rbType.indexOfChild(findViewById(rbType.getCheckedRadioButtonId()));
                if (t == Utils.CREDIT_TYPE)
                    type = "Credit";
                else type = "Debit";

                getResponse res = new getResponse(c, Utils.STATE_ADDCARD);
                res.execute(Utils.ROUTE_ADDCARDS, type, cardnumber, validity);

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
            JSONObject response = null;
            if(args.length>0) {
                try {
                    URL url = new URL(args[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("x-access-token", u.getToken());
                    urlConnection.connect();

                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    String output = "{"+
                            "\"type\":\""+args[1]+"\",\"" +
                            "number\":\""+args[2]+"\",\"" +
                            "validity\":\""+args[3]+"-31T05:00:00.000Z\"" +
                            "}";

                    Utils._(output,c);
                    writer.write(output);
                    writer.flush();
                    writer.close();

                    InputStream input = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    response = new JSONObject(result.toString());

                    Utils._(response.toString(),c);

                } catch (JSONException e) {
                    //this.e = e;
                } catch (IOException e) {
                    //this.e = e;
                } finally {
                    urlConnection.disconnect();
                }
                if(response!=null)
                    return response.toString();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent;
            switch (state){
                case Utils.STATE_ADDCARD:
                    if(result.equals("")) {
                        Utils.toast("Please connect to the internet.", c);
                        return;
                    }
                    if(result.indexOf("Error")>0){
                        Utils._("Unknown error occurred while adding a new card.", c);
                        return;
                    }
                    if(result.indexOf("success")>0){
                        Utils._("Unknown error occurred during sign up.",c);
                        return;
                    }
                    Utils.toast("Card added successfully.",c);
                    intent = new Intent(SettingsActivity.this, LoggedActivity.class);
                    intent.putExtra("activeUser", u);
                    startActivity(intent);
                    finish();
                    break;
                default: Utils._("Warning, unknown state!", context);
            }
        }

    }


}
