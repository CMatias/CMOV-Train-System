package cmov.goncalobo.trainticketsystem.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cmov.goncalobo.trainticketsystem.Entities.Ticket;
import cmov.goncalobo.trainticketsystem.R;
import cmov.goncalobo.trainticketsystem.Entities.User;
import cmov.goncalobo.trainticketsystem.Others.Utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class ActiveTripActivity extends Activity {

    Context c;
    User u;

    String tickets;
    ArrayList<String> validTickets;
    String trip_id;

    TextView tvScanned, tvValid, tvTotal, tvNoShow;

    Button bEndTrip;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = this;
        Utils._("ActiveTripActivity Born!", c);

        setContentView(R.layout.activity_active_trip);
        validTickets = new ArrayList<String>();

        Bundle bundle = getIntent().getExtras();
        u = (User) bundle.get("activeUser");
        trip_id = (String) bundle.get("tripID");

        final ArrayList<Ticket> jsonTickets;
        tickets = (String) bundle.get("tickets");
        jsonTickets = parseJsonTickets(tickets);

/*        for(int i=0;i<jsonTickets.size();i++)
                Utils._(jsonTickets.get(i).display(4), c);*/

        int lastIndex = 0;
        int totalTickets = 0;

        while(lastIndex != -1){
            lastIndex = tickets.indexOf("signature",lastIndex);
            if(lastIndex != -1){
                totalTickets ++;
                lastIndex += ("signature").length();
            }
        }

        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvScanned = (TextView) findViewById(R.id.tvScanned);
        tvValid = (TextView) findViewById(R.id.tvValid);
        tvNoShow = (TextView) findViewById(R.id.tvNoShow);

        bEndTrip = (Button) findViewById(R.id.bEndTrip);

        if(savedInstanceState!=null)
        {
            tvTotal.setText(savedInstanceState.getString("tvTotal"));
            tvScanned.setText(savedInstanceState.getString("tvScanned"));
            tvValid.setText(savedInstanceState.getString("tvValid"));
            tvNoShow.setText(savedInstanceState.getString("tvNoShow"));

            String vTickets = savedInstanceState.getString("vTickets");
            if(!vTickets.equals("")){
                String validTArray[] = vTickets.split(":");
                for(int i=0;i<validTArray.length;i++)
                    validTickets.add(validTArray[i]);
            }


        } else {
            tvTotal.setText("" + totalTickets);
            tvNoShow.setText("" + totalTickets);
        }

        bEndTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonValidTickets;

                if(validTickets.size()>0) {
                    jsonValidTickets = "{\"tickets\":[\"" + validTickets.get(0) + "\"";
                    if (validTickets.size() > 1)
                        for (int i = 1; i < validTickets.size(); i++) {
                            jsonValidTickets += ",\"" + validTickets.get(i) + "\"";
                        }
                    jsonValidTickets += "]}";
                } else {
                    jsonValidTickets = "{\"tickets\":[]}";
                }

                getResponse res = new getResponse(c, Utils.STATE_END_TRIP);
                res.execute(Utils.ROUTE_ENDTRIP, jsonValidTickets);
            }
        });

    }



    @Override
    protected void onSaveInstanceState (Bundle outState)
    {
        outState.putString("tvScanned", tvScanned.getText().toString());
        outState.putString("tvValid", tvValid.getText().toString());
        outState.putString("tvTotal",   tvTotal.getText().toString());
        outState.putString("tvNoShow", tvNoShow.getText().toString());

        String vTickets = "";
        if(validTickets.size()>0) {
            vTickets += validTickets.get(0);
            for (int i = 1; i < validTickets.size(); i++)
                vTickets += ":" + validTickets.get(i);
        }

        outState.putString("vTickets",vTickets);

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
                    String output = args[1];

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
                    Utils._(result,c);
                    Utils.toast("Trip ended successfully.",c);
                    intent = new Intent(ActiveTripActivity.this, LoggedActivity.class);
                    intent.putExtra("activeUser", u);
                    startActivity(intent);
                    finish();
            }
        }

    public void scanQR(View v) {
        if(!tvValid.getText().toString().equals(tvTotal.getText().toString())) {
            try {
                Intent intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException anfe) {
                showDialog(ActiveTripActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
            }
        }else
        Utils.toast("All tickets for this trip have been scanned.",c);
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                if(contents.contains("ID") && contents.contains("Signature")) {
                    String scan_id = contents.substring(contents.indexOf("ID:") + 3, contents.indexOf("#"));
                    String scan_t_id = contents.substring(contents.indexOf("T_ID:") + 5, contents.indexOf("@"));
                    String scan_signature = contents.substring(contents.indexOf("Signature:") + 10, contents.length());

                    try {

                        //Id do ticket, é o que é usado para verificar.
                        String id = scan_id;

                        String t_id = scan_t_id;

                        if(!trip_id.contains(t_id)){
                            Utils.toast("ERROR: This ticket is for another trip.", c);
                            tvScanned.setText("" + (Integer.parseInt(tvScanned.getText().toString()) + 1));
                            return;
                        }

                        //Assinatura do bilhete.
                        String signature = scan_signature;

                        Utils._("qrscanner\nid :"+id+"\nsig:"+signature,c);

                        //Chave publica.
                        String pubKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJpaSy5vuX1j6uJc7qq+9C3B5om+OZfi\nkUasGIBzGfwE15jnwhu/6gpbsrJ+z6uGzoTxBdvBJXbrTgq6hWE6FwkCAwEAAQ==";
                        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(pubKey, Base64.DEFAULT));
                        KeyFactory kf = KeyFactory.getInstance("RSA");

                        PublicKey key = kf.generatePublic(spec);

                        Signature signer = Signature.getInstance("SHA1withRSA");
                        signer.initVerify(key);
                        signer.update(id.getBytes());

                        if (signer.verify(Base64.decode(signature, Base64.DEFAULT))) {
                            if(!validTickets.contains(scan_id)){
                                Utils.toast("Valid ticket.", c);
                                validTickets.add(scan_id);
                                tvNoShow.setText("" + (Integer.parseInt(tvNoShow.getText().toString()) - 1));
                                tvValid.setText("" + (Integer.parseInt(tvValid.getText().toString()) + 1));}
                            else Utils.toast("Ticket already scanned.",c);
                        } else {
                            Utils.toast("Invalid ticket. Might be counterfeited.", c);
                        }
                        tvScanned.setText("" + (Integer.parseInt(tvScanned.getText().toString()) + 1));
                    } catch (InvalidKeySpecException | InvalidKeyException | SignatureException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Utils.toast("Invalid ticket.", c);
                    tvScanned.setText("" + (Integer.parseInt(tvScanned.getText().toString()) + 1));
                }
            }
        }
    }

    public ArrayList<Ticket> parseJsonTickets(String t){

        ArrayList<Ticket> tickets = new ArrayList<Ticket>();

        String[] trip = t.substring(4,t.length()).split("\\u007D\\u007D,\\u007B");


        Ticket newTicket;
        for(int i = 0 ; i < trip.length ; i++) {
            newTicket = new Ticket();
            trip[i] += ",";
            //Utils._(trip[i], c);

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
            ).replace("\"", "").replace("]",""));

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
                    trip[i].indexOf(":", trip[i].indexOf("_id")) + 1,
                    trip[i].indexOf(",", trip[i].indexOf(":", trip[i].indexOf("_id")))
            ).replace("\"", ""));

            tickets.add(newTicket);
        }
        return tickets;
    }



}




