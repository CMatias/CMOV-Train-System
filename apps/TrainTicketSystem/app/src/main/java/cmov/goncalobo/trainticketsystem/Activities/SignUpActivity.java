package cmov.goncalobo.trainticketsystem.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.User;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class SignUpActivity extends Activity {

    String username, email, password, type, cardnumber, validity;

    Context c;

    EditText etUsername, etEmail, etPassword, etCPassword, etCardNumber, etValidity;
    RadioGroup rbType;
    Button bSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;
        Utils._("SignUpActivity Born!", c);


        setContentView(R.layout.activity_sign_up);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etCPassword = (EditText) findViewById(R.id.etCPassword);
        etCardNumber = (EditText) findViewById(R.id.etCardNumber);
        etValidity = (EditText) findViewById(R.id.etValidity);
        rbType = (RadioGroup) findViewById(R.id.rgPayment);

        bSignUp = (Button) findViewById(R.id.bSignUp);

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.length() == 0 || etPassword.length() == 0 || etCardNumber.length() == 0 || etEmail.length() == 0 || etValidity.length() == 0) {
                    Utils.toast("All fields are required!", c);
                    return;
                }
                if (!etPassword.getText().toString().equals(etCPassword.getText().toString())){
                    Utils.toast("Passwords don't match.",c);
                    return;
                }
                if(!etCardNumber.getText().toString().matches(Utils.CARD_NUMBER_PATTERN)){
                    Utils.toast("Card number consists of a sequence of 16 digits.",c);
                    return;
                }
                if(!etValidity.getText().toString().matches(Utils.VALIDITY_PATTERN)){
                    Utils.toast("Please use the standard YYYY/MM input for card validity.",c);
                    return;
                }

                if(!etEmail.getText().toString().matches(Utils.EMAIL_PATTERN)){
                    Utils.toast("Incorrect input for email.",c);
                    return;
                }

                username = etUsername.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                cardnumber = etCardNumber.getText().toString();
                validity = etValidity.getText().toString().replace("/","-");

                int t = rbType.indexOfChild(findViewById(rbType.getCheckedRadioButtonId()));
                if(t==Utils.CREDIT_TYPE)
                    type = "Credit";
                else type = "Debit";

                getResponse res = new getResponse(c, Utils.STATE_SIGNUP);
                res.execute(Utils.ROUTE_SIGNUP, username, email, password, type, cardnumber, validity);

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
                    urlConnection.connect();

                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    String output = "{\"username\":\""+args[1]+"\",\"" +
                                    "email\":\""+args[2]+"\",\"" +
                                    "password\":\""+args[3]+"\",\"" +
                                    "creditcards\":[{" +
                                        "\"type\":\""+args[4]+"\",\"" +
                                        "number\":\""+args[5]+"\",\"" +
                                        "validity\":\""+args[6]+"-31T05:00:00.000Z\"" +
                                    "}]"+
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
                case Utils.STATE_SIGNUP:
                    if(result.equals("")) {
                        Utils.toast("Please connect to the internet.", c);
                        return;
                    }
                    if(result.indexOf("Error")>0){
                        Utils._("Unknown error occured during sign up.",c);
                        return;
                    }
                    Utils._(result,context);
                    User u = new User();
                    u.setUsername(username);
                    u.setPassword(password);
                    intent = new Intent(SignUpActivity.this, LoggedActivity.class);
                    intent.putExtra("activeUser",u);
                    startActivity(intent);
                    finish();
                    break;
                default: Utils._("Warning, unknown state!", context);
            }
        }

    }


}
