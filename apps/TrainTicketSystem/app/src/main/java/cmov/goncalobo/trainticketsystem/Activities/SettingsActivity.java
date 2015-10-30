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
                if (!etValidity.getText().toString().matches(Utils.VALIDITY_PATTERN)) {
                    Utils.toast("Please use the standard YYYY/MM input for card validity.", c);
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
/*
 * Function to display cards *

        ArrayList<String> cardsArray = new ArrayList<String>();
        for(int i = 0; i < u.getCards().size(); i++)
            cardsArray.add(u.getCards().get(i).display(1));


        final Spinner spinner = (Spinner) findViewById(R.id.spCards);
        ArrayAdapter<String> cardsArrayAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, cardsArray); //selected item will look like a spinner set from XML
        cardsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(cardsArrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                for(int i = 0; i < u.getCards().size();i++)
                    if(u.getCards().get(i).getValidity().equals("true"))
                        u.getCards().get(i).setValidity("false");

                int activeCardID = (int) spinner.getSelectedItemId();
                u.getCards().get(activeCardID).setValidity("true");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        }); */
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
                        Utils._("Unknown error occured while adding a new card.", c);
                        return;
                    }
                    if(result.indexOf("success")>0){
                        Utils._("Unknown error occured during sign up.",c);
                        return;
                    }

                    Utils.toast("Card added successfully.",c);

                    break;
                default: Utils._("Warning, unknown state!", context);
            }
        }

    }


}
