package com.manan.dev.ec2018app;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Fragments.QRCodeActivity;
import com.manan.dev.ec2018app.Models.Coordinators;
import com.manan.dev.ec2018app.Models.EventDetails;
import com.manan.dev.ec2018app.Models.QRTicketModel;
import com.manan.dev.ec2018app.NavMenuViews.MapsActivity;
import com.manan.dev.ec2018app.Utilities.ConnectivityReciever;
import com.manan.dev.ec2018app.Utilities.MyApplication;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.manan.dev.ec2018app.Utilities.ConnectivityReciever.isConnected;

public class SingleEventActivity extends AppCompatActivity implements ConnectivityReciever.ConnectivityReceiverListener {

    Button registerButton;
    TextView eventDateTextView, eventNameView, eventStartTimeTextView, locationTextView, eventEndTimeTextView, feesTextView,
            typeOfEventTextView, firstPrizeTextView, secondPrizeTextView, thirdPrizeTextView, descriptionTextView,
            rulesTextView, coordsHeading, eventCategoryTextView;
    RelativeLayout dateTimeRelativeLayout, prizesRelativeLayout;
    LinearLayout coordsLinearLayout, locationRelativeLayout;
    View line1, line2, line3, line4;
    ImageView categoryEventImageView;
    private String phoneNumber;
    private DatabaseController getEventDetails;
    private EventDetails eventDetails;
    private int coordCount = 0, prizeCount = 0;
    private RelativeLayout eventImageLinearLayout;
    private ImageView backbutton, sharebutton;
    private QRTicketModel TicketModel;
    //    private ArrayList<Long> coordsPhoneList;
    private String eventId;
    private ProgressBar barEventImage, barViewTicket;
    private DatabaseController databaseController;
    private boolean checkloadedvar = false;
    private Drawable default_image;
    private RelativeLayout container_se_view;
    private boolean NO_DEEP_LINK_FLAG = true;
    private Intent phoneIntent;
    private QRCodeActivity fragobj;
    String startTime, endTime, formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);
        databaseController = new DatabaseController(this);
        default_image = getResources().getDrawable(R.drawable.default_image_1);


        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            String revStr = new StringBuilder(appLinkData.toString()).reverse().toString();
            int i;
            for (i = 0; revStr.charAt(i) != 35; i++) {
            }
            revStr = revStr.substring(0, i);
            String eventName = new StringBuilder(revStr).reverse().toString().toUpperCase();
            eventName = eventName.replace("%20", " ");
            Log.v("deeplink", eventName);
            eventId = databaseController.retrieveEventIdByName(eventName);
            if (eventId.equals("wrong")) {
                NO_DEEP_LINK_FLAG = false;
                MDToast.makeText(SingleEventActivity.this, "There's no such event.", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
                finish();
                startActivity(new Intent(this, SplashScreen.class));
            }
        } else {
            //  Log.v("nodeeplink", appLinkData + "");
            eventId = getIntent().getStringExtra("eventId");
        }
        Log.e("TAG", "onCreate: " + eventId );
        container_se_view = findViewById(R.id.contaner_se);

        getEventDetails = new DatabaseController(SingleEventActivity.this);
        eventDetails = new EventDetails();

        registerButton = (Button) findViewById(R.id.btn_register);
        barViewTicket = (ProgressBar) findViewById(R.id.pb_view_ticket);
        barViewTicket.setVisibility(View.GONE);
        fragobj = new QRCodeActivity();

        barEventImage = (ProgressBar) findViewById(R.id.pb_event_image);
        eventDateTextView = (TextView) findViewById(R.id.tv_event_date);
        eventStartTimeTextView = (TextView) findViewById(R.id.tv_event_start_time);
        locationTextView = (TextView) findViewById(R.id.tv_event_location);
        eventEndTimeTextView = (TextView) findViewById(R.id.tv_event_end_time);
        // hostClubTextView = (TextView) findViewById(R.id.tv_host);
        feesTextView = (TextView) findViewById(R.id.tv_fees);
        typeOfEventTextView = (TextView) findViewById(R.id.tv_type_of_event);
        firstPrizeTextView = (TextView) findViewById(R.id.tv_prize_first);
        secondPrizeTextView = (TextView) findViewById(R.id.tv_prize_second);
        thirdPrizeTextView = (TextView) findViewById(R.id.tv_prize_third);
        descriptionTextView = (TextView) findViewById(R.id.tv_description);
        rulesTextView = (TextView) findViewById(R.id.tv_rules);
        eventNameView = (TextView) findViewById(R.id.tv_event_name);
        prizesRelativeLayout = (RelativeLayout) findViewById(R.id.rl_prizes);
        eventCategoryTextView = findViewById(R.id.tv_cat_name);
        // goingLinearLayout = (LinearLayout) findViewById(R.id.ll_people_going);
        sharebutton = findViewById(R.id.btn_share);
        categoryEventImageView = findViewById(R.id.iv_type_of_event);
        eventImageLinearLayout = (RelativeLayout) findViewById(R.id.ll_btn_register);

        line1 = (View) findViewById(R.id.line1);
        // line2 = (View) findViewById(R.id.line4);
        line3 = (View) findViewById(R.id.line5);
        line4 = (View) findViewById(R.id.line3);
        line4.setVisibility(View.GONE);
        //  goingLinearLayout.setVisibility(View.GONE);


        dateTimeRelativeLayout = (RelativeLayout) findViewById(R.id.rl_time_date);
        locationRelativeLayout = (LinearLayout) findViewById(R.id.ll_location);
        coordsLinearLayout = (LinearLayout) findViewById(R.id.ll_coordinators);
        coordsHeading = (TextView) findViewById(R.id.tv_coords_heading);
        backbutton = (ImageView) findViewById(R.id.tv_back_button);

        if (NO_DEEP_LINK_FLAG) {
            eventDetails = getEventDetails.retreiveEventsByID(eventId);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(eventDetails.getmStartTime());

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            formattedDate = sdf.format(cal.getTime());
            eventDateTextView.setText(formattedDate);

            SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
            Calendar endTimeMilis = Calendar.getInstance();
            endTimeMilis.setTimeInMillis(eventDetails.getmEndTime());

            startTime = sdf1.format(cal.getTime());
            endTime = sdf1.format(endTimeMilis.getTime());

            eventStartTimeTextView.setText(startTime);
            eventEndTimeTextView.setText(endTime);

            backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            eventNameView.setText(eventDetails.getmName());

            if (eventDetails.getmCategory().equals("solo")) {
                categoryEventImageView.setImageDrawable(getResources().getDrawable(R.drawable.vector_single));
            }
            eventCategoryTextView.setText(eventDetails.getmCategory());

            locationTextView.setText(eventDetails.getmVenue());
            // hostClubTextView.setText(eventDetails.getmClubname());
            final Long fees = eventDetails.getmFees();
            if (fees == 0) {
                feesTextView.setText("Free");
            } else {
                feesTextView.setText(String.valueOf(fees));
            }

            if (eventDetails.getmEventTeamSize().equals("NA")) {
                registerButton.setVisibility(View.GONE);
                //  line1.setVisibility(View.GONE);
                typeOfEventTextView.setText("Presentation Event");
            } else {
                typeOfEventTextView.setText(eventDetails.getmEventTeamSize());
            }

            for (final Coordinators coord : eventDetails.getmCoordinators()) {
                if (!coord.getmCoordName().equals("")) {
                    View view = View.inflate(getApplicationContext(), R.layout.single_layout_event_coords, null);
                    TextView coordNameView = (TextView) view.findViewById(R.id.tv_coords_name);
                    TextView coordPhoneView = (TextView) view.findViewById(R.id.tv_coords_phone_no);

                    coordNameView.setText(coord.getmCoordName());
                    coordPhoneView.setText(String.valueOf(coord.getmCoordPhone()));
//                coordsPhoneList.add(coord.getmCoordPhone());
                    coordCount++;
                    coordsLinearLayout.addView(view);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + coord.getmCoordPhone()));
                            if (ActivityCompat.checkSelfPermission(SingleEventActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                final String[] PERMISSIONS_CALL = {Manifest.permission.CALL_PHONE};
                                //Asking request Permissions
                                ActivityCompat.requestPermissions(SingleEventActivity.this, PERMISSIONS_CALL, 9);
                            } else {
                                startActivity(phoneIntent);
                            }

                        }
                    });
                }
            }
            if (coordCount == 0) {
                coordsLinearLayout.setVisibility(View.GONE);
                coordsHeading.setVisibility(View.GONE);
                //  line2.setVisibility(View.GONE);
            }

            firstPrizeTextView.setVisibility(View.GONE);
            secondPrizeTextView.setVisibility(View.GONE);
            thirdPrizeTextView.setVisibility(View.GONE);
            prizesRelativeLayout.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);

            for (String prizes : eventDetails.getmPrizes()) {
                if (!prizes.equals("") && !prizes.equals("null") && prizeCount == 0) {
                    line3.setVisibility(View.VISIBLE);
                    firstPrizeTextView.setVisibility(View.VISIBLE);
                    firstPrizeTextView.setText("First Prize: Rs " + prizes);
                    prizesRelativeLayout.setVisibility(View.VISIBLE);
                    prizeCount++;
                } else if (!prizes.equals("") && !prizes.equals("null") && prizeCount == 1) {
                    secondPrizeTextView.setVisibility(View.VISIBLE);
                    secondPrizeTextView.setText("Second Prize: Rs " + prizes);
                    prizeCount++;
                } else if (!prizes.equals("") && !prizes.equals("null") && prizeCount == 2) {
                    thirdPrizeTextView.setVisibility(View.VISIBLE);
                    thirdPrizeTextView.setText("Third Prize: Rs " + prizes);
                    prizeCount++;
                }

            }
            descriptionTextView.setText(eventDetails.getmDesc());
            rulesTextView.setText(eventDetails.getmRules());
            dateTimeRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MDToast.makeText(SingleEventActivity.this, "Set a reminder!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();

                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.EventsEntity.CONTENT_URI)
                            .setType("vnd.android.cursor.item/event")
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, eventDetails.getmStartTime())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, eventDetails.getmEndTime())
                            .putExtra(CalendarContract.EventsEntity.TITLE, eventDetails.getmName())
                            .putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
                            .putExtra(CalendarContract.Reminders.MINUTES, 5);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                }
            });

            locationRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MDToast.makeText(SingleEventActivity.this, "Search on Maps!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
                    Intent intentMap = new Intent(SingleEventActivity.this, MapsActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentMap);
                }
            });
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    barViewTicket.setVisibility(View.VISIBLE);
                    if (registerButton.getText().toString().equals("View Ticket")) {
                        FragmentManager fm = getFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putString("qrcodestring", TicketModel.getQRcode());
                        bundle.putString("eventid", eventId);
                        bundle.putInt("activity", 0);
                        bundle.putInt("paymentStatus", TicketModel.getPaymentStatus());
                        bundle.putInt("arrivalStatus", TicketModel.getArrivalStatus());

                        fragobj.setArguments(bundle);
                        fragobj.show(fm, "hiiiii");
                    } else {
                        startActivity(new Intent(SingleEventActivity.this, EventRegister.class)
                                .putExtra("eventName", eventDetails.getmName())
                                .putExtra("eventId", eventDetails.getmEventId())
                                .putExtra("eventType", eventDetails.getmEventTeamSize()));
                    }
                }
            });


            sharebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String baseUrl = "http://elementsculmyca.com/event/";
                    String parsedUrl = baseUrl + "#" + eventDetails.getmName().toString().replaceAll(" ", "%20");

                    Log.e("TAG", "onClick: " + parsedUrl );
                    String message = "Elements Culmyca 2018:" + eventDetails.getmName().toString() + " View the event clicking the link: " + parsedUrl;
                    shareEventMessage(message);

                }
            });
            showSnack(isConnected());

        }
    }

    private void shareEventMessage(String msg) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(i);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        Log.v("permission", "permission" + permissionGranted);
        if (permissionGranted) {
            startActivity(phoneIntent);
        } else {
            MDToast.makeText(SingleEventActivity.this, "You didn't assign permission.", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_event_deeplinking, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share_event:
                Log.e("TAG", "onOptionsItemSelected: " + item.getTitle() );
                break;
            default:
                Log.e("TAG", "onOptionsItemSelected: " + "Invalid Selection!");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayTickets(String eventId) {
        TicketModel = databaseController.retrieveTicketsByID(eventId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyApplication.getInstance().setConnectivityListener(SingleEventActivity.this);
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        phoneNumber = prefs.getString("Phone", null);
        if (databaseController.checkIfValueExists1(eventId)) {
            registerButton.setText("View Ticket");
            displayTickets(eventId);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            if (!isloaded()) {
                String baseUrl = "https://manan-ymca.github.io/ElementsCulmyca2018Website/assets/";
                String parseUrl = baseUrl +
                        eventDetails.getmName().toString().toUpperCase().replaceAll(" ", "%20") +
                        ".jpg";

                Uri imageuri = Uri.parse(parseUrl);
                barEventImage.setVisibility(View.VISIBLE);
                new LoadBackground(parseUrl,
                        "CurrentImage").execute();
            } else {
                barEventImage.setVisibility(View.GONE);
                checkloadedvar = true;

            }
//            message = "Connected";
//            Snackbar.make(container_se_view,message,Snackbar.LENGTH_SHORT).show();
//            color = Color.WHITE;
        } else {
            message = "Get a hotspot Buddy";
            Snackbar.make(container_se_view, message, Snackbar.LENGTH_SHORT).show();

        }
    }

    private boolean isloaded() {
        return checkloadedvar;
    }

    private class LoadBackground extends AsyncTask<String, Void, Drawable> {

        private String imageUrl, imageName;

        public LoadBackground(String url, String file_name) {
            this.imageUrl = url;
            this.imageName = file_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(String... urls) {

            try {
                InputStream is = (InputStream) this.fetch(this.imageUrl);
                Drawable d = Drawable.createFromStream(is, this.imageName);
                return d;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return default_image;
            } catch (IOException e) {
                e.printStackTrace();
                return default_image;
            }
        }

        private Object fetch(String address) throws MalformedURLException, IOException {
            URL url = new URL(address);
            Object content = url.getContent();
            return content;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            barEventImage.setVisibility(View.GONE);
            eventImageLinearLayout.setBackground(result);
        }
    }
}
