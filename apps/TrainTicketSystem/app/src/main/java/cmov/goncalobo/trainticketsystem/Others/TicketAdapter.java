package cmov.goncalobo.trainticketsystem.Others;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cmov.goncalobo.trainticketsystem.Activities.LoggedActivity;
import cmov.goncalobo.trainticketsystem.Entities.Ticket;
import cmov.goncalobo.trainticketsystem.R;

@SuppressWarnings( "deprecation" )
public class TicketAdapter extends BaseAdapter{
    ArrayList<Ticket> result;
    Context context;
    public ArrayList<Integer> seatsTaken;
    int state;
    private static Bitmap bmp;
    ProgressBar p;
    private FileOutputStream fos;
    private static LayoutInflater inflater=null;
    public TicketAdapter(Activity mainActivity, ArrayList<Ticket> r, int state) {
        result=r;
        this.state=state;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv1,tv2,tv3;
        ImageView img;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        seatsTaken = new ArrayList<Integer>();
        switch (state) {
            case Utils.STATE_MY_TICKETS:
                Holder holder = new Holder();
                final View rowView;
                rowView = inflater.inflate(R.layout.ticket_list, null);
                holder.tv1 = (TextView) rowView.findViewById(R.id.tvFromTo);
                holder.tv2 = (TextView) rowView.findViewById(R.id.tvPriceDuration);
                holder.tv3 = (TextView) rowView.findViewById(R.id.tvSeat);
                holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
                holder.tv1.setText(result.get(position).display(1));
                holder.tv2.setText(result.get(position).display(2));
                holder.tv3.setText(result.get(position).display(3));
                if (result.get(position).isValid())
                    holder.img.setImageResource(R.drawable.downloadqr);
                else holder.img.setImageResource(R.drawable.notdownloadqr);
                rowView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result.get(position).isValid()) {
                            Utils.toast("Loading ticket...", context);
/*                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        bmp = getBitmapFromURL(result.get(position).getQrcode());
                                        saveImageToSD(result.get(position).display(4));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread.start();
                            Utils.toast("Ticket saved in your pictures folder.", context);*/

                            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                            float dp_w = 197f;
                            float dp_h = 280f;
                            int pixels_w = (int) (metrics.density * dp_w + 0.5f);
                            int pixels_h = (int) (metrics.density * dp_h + 0.5f);

                            QRCodeGenerator view = new QRCodeGenerator(context,result.get(position));
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setView(view);
                            builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            alertDialog.getWindow().setLayout(pixels_w, pixels_h);
                            alertDialog.getWindow().setBackgroundDrawable(view.getResources().getDrawable(R.drawable.button_bg_blured));

                            Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            if(b != null) {
                                b.setWidth(pixels_w);
                                b.setTextColor(Color.BLACK);
                                b.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.button_bg_blured_close));
                            }

                        } else Utils.toast("This ticket has expired.", context);
                    }
                });
                return rowView;
            case Utils.STATE_FIND_RESULTS:
                final View rowViewResults;
                rowViewResults = inflater.inflate(R.layout.result_list, null);
                final Dialog seatsDialog = new Dialog(context);
                final Dialog buyDialog = new Dialog(context);
                final Holder holderResults = new Holder();
                holderResults.tv1 = (TextView) rowViewResults.findViewById(R.id.tvFromTo);
                holderResults.tv2 = (TextView) rowViewResults.findViewById(R.id.tvPriceDuration);
                holderResults.tv3 = (TextView) rowViewResults.findViewById(R.id.tvSeat);
                holderResults.img = (ImageView) rowViewResults.findViewById(R.id.ivBuyTicket);
                holderResults.tv1.setText(result.get(position).display(1));
                holderResults.tv2.setText(result.get(position).display(2));

                // ------- SEAT SELECTOR ------------ //
                Utils._(""+result.get(position).getMaxCapacity(),context);
                int totalseats = result.get(position).getMaxCapacity();
                final int carriageCapacity = 20;
                int totalCarriages = Math.abs(totalseats/carriageCapacity);

                seatsDialog.setContentView(R.layout.seat_selector);

                GridLayout g = (GridLayout) seatsDialog.findViewById(R.id.seatsHolder);

                ArrayList<String> carriagesArray = new ArrayList<String>();
                for(int i = 0; i < totalCarriages; i++)
                    carriagesArray.add(""+(i+1));

                final Spinner spinner = (Spinner) seatsDialog.findViewById(R.id.spCarriage);
                ArrayAdapter<String> carriagesArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, carriagesArray); //selected item will look like a spinner set from XML
                carriagesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(carriagesArrayAdapter);

                final ArrayList<ImageButton> seats = new ArrayList<ImageButton>();
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat01));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat02));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat03));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat04));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat05));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat06));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat07));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat08));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat09));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat10));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat11));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat12));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat13));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat14));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat15));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat16));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat17));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat18));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat19));
                seats.add((ImageButton) seatsDialog.findViewById(R.id.ibSeat20));



                // ----- BUY DIALOG  ------ //


                holderResults.img.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holderResults.tv3.getText().equals("Pick\nSeat")) {
                            Utils.toast("Pick a seat first please.",context);
                            return;
                        }
                        buyDialog.setContentView(R.layout.buy_dialog);
                        ArrayList<String> cardsArray = new ArrayList<String>();
                        for(int i = 0; i < LoggedActivity.u.getCards().size(); i++)
                            cardsArray.add(LoggedActivity.u.getCards().get(i).display(1));

                        buyDialog.setTitle("Select Payment Card:");

                        Spinner cardSpinner = (Spinner) buyDialog.findViewById(R.id.spCard);
                        ArrayAdapter<String> cardsArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, cardsArray); //selected item will look like a spinner set from XML
                        cardsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cardSpinner.setAdapter(cardsArrayAdapter);

                        TextView warning = (TextView) buyDialog.findViewById(R.id.tvAttention);

                        if(result.get(position).hasWaitingTime())
                            warning.setText("Attention! This trip has a waiting\ntime of " +result.get(position).getWaitingTime()+" at the Main Station!");

                        final String jsonBuy = "" +
                                "{"  +
                                "\"departure\":"+
                                "{"  +
                                    "\"station\":\"" + result.get(position).getFrom().replace("M. Station","MS") + "\"," +
                                    "\"date\":\"" + result.get(position).getDeparture(1)+"T"+result.get(position).getDeparture(2).replace("h",":") + ":00.973Z\"" +
                                "}," +
                                "\"arrival\":" +
                                "{"  +
                                    "\"station\":\"" + result.get(position).getTo().replace("M. Station","MS") + "\"," +
                                    "\"date\":\""    + result.get(position).getArrival(1)+"T"+result.get(position).getArrival(2).replace("h",":") + ":00.973Z\"" +
                                "}," +
                                "\"duration\":\"1970-01-01" +"T"+ result.get(position).getDuration().replace("h",":") + ":00.000Z\"," +
                                "\"seats\":[\""+holderResults.tv3.getText().toString().replace("Seat\n","")+"\"]," +
                                "\"price\":[\""+result.get(position).getPrice(1).replace("€","")+"\"]," +
                                "\"trips\":[\"" + result.get(position).getTripID(1) + "\"]" +
                                "}";


                        buyDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.button_bg_notblured));
                        buyDialog.show();

                        final Button bConfirm = (Button) buyDialog.findViewById(R.id.bConfirm);
                        Button bCancel  = (Button) buyDialog.findViewById(R.id.bCancel);

                        bConfirm.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getResponse res = new getResponse(context, Utils.STATE_BUY_TICKET);
                                res.execute(Utils.ROUTE_BUY_TICKET, jsonBuy);
                            }
                        });

                        bCancel.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buyDialog.cancel();
                            }
                        });


                    }
                });

                holderResults.tv3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getResponse res = new getResponse(context, Utils.STATE_GET_SEATS);
                        res.execute(Utils.ROUTE_GET_SEATS, result.get(position).getTripID(Utils.STATE_GET_SEATS));
                        seatsDialog.show();
                    }
                });

                seatsDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        seatsTaken.clear();
                    }
                });

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        for(int i = 0;i<seats.size();i++){
                            seats.get(i).setBackgroundResource(R.drawable.freeseat);
                        }
                        int carriage = Integer.parseInt(spinner.getSelectedItem().toString());
                        if(seatsTaken.isEmpty()){}else
                        for(int j=0;j<seatsTaken.size();j++) {
                            for (int i = carriage * carriageCapacity; i > carriage * carriageCapacity - carriageCapacity; i--)
                                if (seatsTaken.get(j) == i) {
                                    int seatNo = seatsTaken.get(j);
                                    while (seatNo > carriageCapacity)
                                        seatNo -= carriageCapacity;
                                    seats.get(seatNo - 1).setBackgroundResource(R.drawable.busyseat);
                                    seats.get(seatNo - 1).setClickable(false);

                                }
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });

                for(int i = 0;i<seats.size();i++){
                    final int seatCount = i;
                    seats.get(i).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int seatNo = (seatCount+1) + (Integer.parseInt(spinner.getSelectedItem().toString())-1)*carriageCapacity;
                            holderResults.tv3.setText("Seat\n" + seatNo);
                            seatsDialog.dismiss();

                        }
                    });
                }

                return rowViewResults;

            default: return null;
        }

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
            if(args.length>0) {
                boolean searchingSeats = args[0].equals(Utils.ROUTE_GET_SEATS);
                if (searchingSeats) {
                    StringBuilder result = new StringBuilder();
                    try {
                        URL url = new URL(args[0] + "/" + args[1]);
                        Utils._(url.toString(),context);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestProperty("Content-Type", "application/json");
                        urlConnection.setRequestProperty("x-access-token", LoggedActivity.u.getToken());

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
                } else {
                    JSONObject response = null;

                    try {
                        URL url;

                        url = new URL(args[0]);

                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setDoOutput(true);
                        urlConnection.setDoInput(true);
                        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setRequestProperty("x-access-token", LoggedActivity.u.getToken());
                        urlConnection.connect();

                        OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());

                        String output = args[1];

                        Utils._(output, context);
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

                        Utils._(response.toString(), context);

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
            }
            return "";
        }

        @Override
        public void onPostExecute(String result) {
            switch (state) {
                case Utils.STATE_GET_SEATS:
                    Utils._(result, context);
                    result = result.replace("[","").replace("]","");
                    if(result.equals("")) {
                        seatsTaken.add(-1);
                    }
                    else
                    {
                        String[] aux = result.split(",");
                        for(int i=0;i<aux.length;i++)
                            seatsTaken.add(Integer.parseInt(aux[i]));
                    }
                    break;
                case Utils.STATE_BUY_TICKET:
                    Utils._(result, context);
                    if(result.indexOf("Denied")>0) {
                        Utils.toast("Invalid payment card.", context);
                        break;
                    }
                    Utils.toast("Thank you for your purchase.", context);
                    ((Activity)context).finish();


                    break;
                default:
                    Utils._("Warning, unknown state!", context);
            }
        }
    }


}