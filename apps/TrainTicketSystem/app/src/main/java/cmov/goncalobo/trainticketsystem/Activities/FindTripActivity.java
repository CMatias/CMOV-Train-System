package cmov.goncalobo.trainticketsystem.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.User;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class FindTripActivity extends Activity {

    Context c;
    User u;
    Calendar myCalendar = Calendar.getInstance();
    Button bSelectDate,bFind;
    Spinner spFrom, spTo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;
        Utils._("FindTripActivity Born!", c);

        setContentView(R.layout.activity_find_trip);

        Bundle bundle = getIntent().getExtras();
        u = (User) bundle.get("activeUser");

        bSelectDate = (Button) findViewById(R.id.bSelectDate);
        spFrom = (Spinner) findViewById(R.id.spFrom);
        spTo = (Spinner) findViewById(R.id.spTo);
        bFind = (Button) findViewById(R.id.bFind);

        //bFind.setText("\uD83D\uDD0E");

        bSelectDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(FindTripActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // FROM SPINNER
        final ArrayAdapter<CharSequence> adapterFrom = ArrayAdapter.createFromResource(this, R.array.fromStations, R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterTo = ArrayAdapter.createFromResource(this, R.array.toStations, R.layout.spinner_item);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFrom.setAdapter(adapterFrom);
        spTo.setAdapter(adapterTo);
        spTo.setSelection(Utils.MAIN_STATION);

        int from = -1, to = -1;
        String date = null;

        if(savedInstanceState!=null)
        {
            from = Integer.parseInt(savedInstanceState.getString("from"));
            to   = Integer.parseInt(savedInstanceState.getString("to"));
            date = savedInstanceState.getString("date");
        }

        if(from != -1)
            spFrom.setSelection(from);
        if(to != -1)
            spTo.setSelection(to);
        if(date != null)
            bSelectDate.setText(date);





        bFind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if( bSelectDate.getText().equals("SELECT DATE"))
                {
                    Utils.toast("Please select a date.",c);
                    return;
                } else
                if( myCalendar.getTimeInMillis() <  Calendar.getInstance().getTimeInMillis()-(1000*60*60*24))
                {
                    Utils.toast("The selected date is invalid, it belongs in the past.",c);
                    return;
                } else
                if(spFrom.getSelectedItem().toString().equals(spTo.getSelectedItem().toString()))
                {
                    Utils.toast("Departure station can't be the same as arrival.", c);
                    return;
                } else {
                    Utils.toast("Searching for tickets, please wait...", c);
                    Utils._("Find Trips button hit!", c);

                    String jsonDateFormat = "yyyy/MM/dd"; //In which you need put here
                    SimpleDateFormat jsonSDF = new SimpleDateFormat(jsonDateFormat, Locale.US);

                    getResponse res = new getResponse(c);
                    res.execute(Utils.ROUTE_FINDTRIPS, jsonSDF.format(myCalendar.getTime()), spFrom.getSelectedItem().toString().replace("Main Station","MS"), spTo.getSelectedItem().toString().replace("Main Station","MS"));
                }
            }
        });

    }


    @Override
    protected void onSaveInstanceState (Bundle outState)
    {
        outState.putString("from", ""+spFrom.getSelectedItemPosition());
        outState.putString("to",   ""+spTo.getSelectedItemPosition());
        outState.putString("date", bSelectDate.getText().toString());
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };



    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        bSelectDate.setText("DATE: " + sdf.format(myCalendar.getTime()));
    }

    public class getResponse extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;
        Context context;
        int state;

        public getResponse(Context c) {
            context = c;
        }

        @Override
        protected String doInBackground(String... args) {
            StringBuilder result = new StringBuilder();
            if(args.length!=0) {
                String urlString = (args[0]+"/"+args[1]+"/"+args[2]+"/"+args[3]).replace(" ","%20");
                Utils._(urlString,c);
                Utils._(u.getToken(),c);
                try {
                    URL url = new URL(urlString);
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
            if(result.equals("")) {
                Utils.toast("Please connect to the internet.", c);
                return;
            }
            if(!result.equals("[]")) {
                Intent intent;
                intent = new Intent(FindTripActivity.this, FindResultsActivity.class);
                intent.putExtra("tickets", result);
                startActivity(intent);
            } else Utils.toast("No tickets available for the selected date.",c);
            return;
        }

    }


}




