package cmov.goncalobo.trainticketsystem.Others;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

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
                            float dp_w = (metrics.widthPixels)*0.5f;//197f;
                            float dp_h = metrics.heightPixels/2;//280f;
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
                int totalseats = result.get(position).getMaxCapacity();
                final int carriageCapacity = 20;
                int totalCarriages = Math.abs(totalseats/carriageCapacity);

                final int takenseats[] = {10,20,21,33,45,70,75,110};

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

                seatsDialog.setContentView(R.layout.seat_selector);

                holderResults.img.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {/*
                        if(holderResults.tv3.getText().equals("Pick\nSeat")) {
                            Utils.toast("Pick a seat first please.",context);
                            return;
                        }*/
                        TextView t = new TextView(context);
                        t.setText("sadasda");
                        ArrayList<String> cardsArray = new ArrayList<String>();
                        for(int i = 0; i < LoggedActivity.u.getCards().size(); i++)
                            cardsArray.add(LoggedActivity.u.getCards().get(i).display(1));


                        Spinner cardSpinner = new Spinner(context);
                        ArrayAdapter<String> cardsArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, cardsArray); //selected item will look like a spinner set from XML
                        cardsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cardSpinner.setAdapter(cardsArrayAdapter);



                        buyDialog.setContentView(cardSpinner);
                        buyDialog.show();

                    }
                });

                holderResults.tv3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        seatsDialog.show();
                    }
                });

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        for(int i = 0;i<seats.size();i++){
                            seats.get(i).setBackgroundResource(R.drawable.freeseat);
                        }
                        int carriage = Integer.parseInt(spinner.getSelectedItem().toString());
                        for(int j=0;j<takenseats.length;j++) {
                            for (int i = carriage * carriageCapacity; i > carriage * carriageCapacity - carriageCapacity; i--)
                                if (takenseats[j] == i) {
                                    int seatNo = takenseats[j];
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

/*
    private void saveImageToSD(Canvas canvas) {

    *//*--- this method will save your downloaded image to SD card ---*//*

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    *//*--- you can select your preferred CompressFormat and quality.
     * I'm going to use JPEG and 100% quality ---*//*
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    *//*--- create a new file on SD card ---*//*

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + ticketID + ".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    *//*--- create a new FileOutputStream and write bytes to file ---*//*
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bytes.toByteArray());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    public static Bitmap getBitmapFromURL(String link) {
    /*--- this method downloads an Image from the given URL,
     *  then decodes and returns a Bitmap object
     ---*/
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("getBmpFromUrl error: ", e.getMessage().toString());
            return null;
        }
    }


}