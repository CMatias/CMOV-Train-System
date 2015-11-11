package cmov.goncalobo.trainticketsystem.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.User;
import cmov.goncalobo.trainticketsystem.Others.Utils;

public class AuthActivity extends Activity {

    String username, password;

    Context c;

    String data;
    private String file = "mydata";

    EditText etUsername, etPassword;
    CheckBox cbRememberMe;
    TextView tvSignUp;
    Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        c = this;
        Utils._("AuthActivity Born!", c);

        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);

        bLogin = (Button) findViewById(R.id.bLogin);

        try{
            FileInputStream fin = openFileInput(file);
            int i;
            String temp="";

            while( (i = fin.read()) != -1){
                temp = temp + Character.toString((char)i);
            }
            int separatorPos = temp.indexOf(":");
            etUsername.setText(temp.substring(0,separatorPos));
            etPassword.setText(temp.substring(separatorPos+1,temp.length()));
            cbRememberMe.setChecked(true);
            Utils._(temp.substring(0, separatorPos) + "//" + temp.substring(separatorPos+1,temp.length()),c);
        }
        catch(Exception e){
        }

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils._("Login button hit!", c);

                if(cbRememberMe.isChecked()){
                    data=etUsername.getText().toString()+":"+etPassword.getText().toString();

                    try {
                        FileOutputStream fOut = openFileOutput(file,MODE_WORLD_READABLE);
                        fOut.write(data.getBytes());
                        fOut.close();
                        Utils._("User Remembered.",c);
                    }

                    catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    data="";

                    try {
                        FileOutputStream fOut = openFileOutput(file,MODE_WORLD_READABLE);
                        fOut.write(data.getBytes());
                        fOut.close();
                        Utils._("User forgotten.",c);
                    }

                    catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (username.length() == 0 || password.length() == 0) {
                    Utils.toast("Username and password cannot be blank!", c);
                    return;
                }
                    getResponse res = new getResponse(c, Utils.STATE_LOGIN);
                    res.execute(Utils.ROUTE_LOGIN, username, password);
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
            if (args.length > 0) {
                try {
                    URL url = new URL(args[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.connect();

                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    String output = "{\"username\":\"" + args[1] + "\",\"password\":\"" + args[2] + "\"}";
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

                } catch (JSONException e) {
                    //this.e = e;
                } catch (IOException e) {
                    //this.e = e;
                } finally {
                    urlConnection.disconnect();
                }
                if (response != null)
                    return response.toString();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

            Intent intent;
            switch (state) {
                case Utils.STATE_LOGIN:
                    Utils._(result, context);
                    if (result.equals("")) {
                        Utils.toast("Please connect to the internet.", c);
                        return;
                    }
                    int success = result.indexOf("true");

                    if (success > 0) {

                        User u = new User();
                        String token = result.replace("\"", "").replace("}", "");
                        token = token.substring(token.indexOf("token:") + 6, token.length()).replace(",success:true","");
                        Utils._(token,c);
                        u.setToken(token);
                        u.setUsername(username);
                        u.setPassword(password);
                        intent = new Intent(AuthActivity.this, LoggedActivity.class);
                        intent.putExtra("activeUser", u);
                        startActivity(intent);
                        finish();
                    } else {
                        Utils.toast("Incorrect login information.", c);
                        return;
                    }
                    break;
            }

        }

    }


}
