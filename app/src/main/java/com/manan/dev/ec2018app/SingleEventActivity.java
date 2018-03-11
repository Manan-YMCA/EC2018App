package com.manan.dev.ec2018app;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Fragments.QRCodeActivity;
import com.manan.dev.ec2018app.Models.Coordinators;
import com.manan.dev.ec2018app.Models.EventDetails;
import com.manan.dev.ec2018app.Models.QRTicketModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SingleEventActivity extends AppCompatActivity {

    Button registerButton;
    TextView eventDateTextView, eventNameView, eventStartTimeTextView, locationTextView, eventEndTimeTextView, hostClubTextView, feesTextView,
            typeOfEventTextView, firstPrizeTextView, secondPrizeTextView, thirdPrizeTextView, descriptionTextView,
            rulesTextView, coordsHeading;
    RelativeLayout dateTimeRelativeLayout, locationRelativeLayout, prizesRelativeLayout;
    LinearLayout coordsLinearLayout, goingLinearLayout;
    View line1, line2, line3, line4;
    private String phoneNumber;
    private DatabaseController getEventDetails;
    private EventDetails eventDetails;
    private int coordCount = 0, prizeCount = 0;
    private QRTicketModel TicketModel;

    private String eventId;
    private DatabaseController databaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);
        databaseController = new DatabaseController(this);
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        Log.v("deeplink", appLinkData + "");
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            String revStr = new StringBuilder(appLinkData.toString()).reverse().toString();
            int i;
            for(i=0;revStr.charAt(i)!=35;i++){
            }
            revStr = revStr.substring(0,i);
            String eventName = new StringBuilder(revStr).reverse().toString().toUpperCase();
            eventName = eventName.replace("%20"," ");
            Log.v("deeplink", eventName);
            Toast.makeText(this,"deeplink:"+eventName,Toast.LENGTH_SHORT).show();
            eventId = databaseController.retrieveEventIdByName(eventName);
            if(eventId.equals("wrong")){
                Toast.makeText(this, "There's no such event.", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this,SplashScreen.class));
            }
        } else {
            Toast.makeText(this,"nodeeplink:",Toast.LENGTH_SHORT).show();
            Log.v("nodeeplink", appLinkData + "");
            eventId = getIntent().getStringExtra("eventId");
        }
        Toast.makeText(this, eventId, Toast.LENGTH_SHORT).show();
        getEventDetails = new DatabaseController(SingleEventActivity.this);
        eventDetails = new EventDetails();
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName) ,MODE_PRIVATE);
        phoneNumber = prefs.getString("Phone", null);
        if(phoneNumber!=null)
        displayTickets(phoneNumber);
        registerButton = (Button) findViewById(R.id.btn_register);

        eventDateTextView = (TextView) findViewById(R.id.tv_event_date);
        eventStartTimeTextView = (TextView) findViewById(R.id.tv_event_start_time);
        locationTextView = (TextView) findViewById(R.id.tv_event_location);
        eventEndTimeTextView = (TextView) findViewById(R.id.tv_event_end_time);
        hostClubTextView = (TextView) findViewById(R.id.tv_host);
        feesTextView = (TextView) findViewById(R.id.tv_fees);
        typeOfEventTextView = (TextView) findViewById(R.id.tv_type_of_event);
        firstPrizeTextView = (TextView) findViewById(R.id.tv_prize_first);
        secondPrizeTextView = (TextView) findViewById(R.id.tv_prize_second);
        thirdPrizeTextView = (TextView) findViewById(R.id.tv_prize_third);
        descriptionTextView = (TextView) findViewById(R.id.tv_description);
        rulesTextView = (TextView) findViewById(R.id.tv_rules);
        eventNameView = (TextView) findViewById(R.id.tv_event_name);
        prizesRelativeLayout = (RelativeLayout) findViewById(R.id.rl_prizes);
        goingLinearLayout = (LinearLayout) findViewById(R.id.ll_people_going);
        line1 = (View) findViewById(R.id.line1);
        line2 = (View) findViewById(R.id.line4);
        line3 = (View) findViewById(R.id.line5);
        line4 = (View) findViewById(R.id.line3);
        line4.setVisibility(View.GONE);
        goingLinearLayout.setVisibility(View.GONE);

        dateTimeRelativeLayout = (RelativeLayout) findViewById(R.id.rl_time_date);
        locationRelativeLayout = (RelativeLayout) findViewById(R.id.rl_location);
        coordsLinearLayout = (LinearLayout) findViewById(R.id.ll_coordinators);
        coordsHeading = (TextView) findViewById(R.id.tv_coords_heading);
        eventDetails = getEventDetails.retreiveEventsByID(eventId);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(eventDetails.getmStartTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(cal.getTime());
        eventDateTextView.setText(formattedDate);

        SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
        Calendar endTimeMilis = Calendar.getInstance();
        endTimeMilis.setTimeInMillis(eventDetails.getmEndTime());

        String startTime = sdf1.format(cal.getTime());
        String endTime = sdf1.format(endTimeMilis.getTime());

        eventStartTimeTextView.setText(startTime);
        eventEndTimeTextView.setText(endTime);

        eventNameView.setText(eventDetails.getmName());

        locationTextView.setText(eventDetails.getmVenue());
        hostClubTextView.setText(eventDetails.getmClubname());
        Long fees = eventDetails.getmFees();
        if (fees == 0) {
            feesTextView.setText("Free");
        } else {
            feesTextView.setText(String.valueOf(fees));
        }

        if (eventDetails.getmEventTeamSize().equals("NA")) {
            registerButton.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
            typeOfEventTextView.setText("Presentation Event");
        } else {
            typeOfEventTextView.setText(eventDetails.getmEventTeamSize());
        }
        for (Coordinators coord : eventDetails.getmCoordinators()) {
            if (!coord.getmCoordName().equals("")) {
                View view = View.inflate(getApplicationContext(), R.layout.single_layout_event_coords, null);
                TextView coordNameView = (TextView) view.findViewById(R.id.tv_coords_name);
                TextView coordPhoneView = (TextView) view.findViewById(R.id.tv_coords_phone_no);

                coordNameView.setText(coord.getmCoordName());
                coordPhoneView.setText(String.valueOf(coord.getmCoordPhone()));
                coordCount++;
                coordsLinearLayout.addView(view);
            }
        }
        if (coordCount == 0) {
            coordsLinearLayout.setVisibility(View.GONE);
            coordsHeading.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
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
                firstPrizeTextView.setText(prizes);
                prizesRelativeLayout.setVisibility(View.VISIBLE);
                prizeCount++;
            } else if (!prizes.equals("") && !prizes.equals("null") && prizeCount == 1) {
                secondPrizeTextView.setVisibility(View.VISIBLE);
                secondPrizeTextView.setText(prizes);
                prizeCount++;
            } else if (!prizes.equals("") && !prizes.equals("null") && prizeCount == 2) {
                thirdPrizeTextView.setVisibility(View.VISIBLE);
                thirdPrizeTextView.setText(prizes);
                prizeCount++;
            }

        }
        descriptionTextView.setText(eventDetails.getmDesc());
        rulesTextView.setText(eventDetails.getmRules());
        dateTimeRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SingleEventActivity.this, "Calender pe reminder set karna hai!", Toast.LENGTH_SHORT).show();
            }
        });

        locationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SingleEventActivity.this, "Google ke rastha dekhna hai!", Toast.LENGTH_SHORT).show();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registerButton.getText().toString().equals("View Ticket")){

                    FragmentManager fm = getFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putString("qrcodestring", TicketModel.getQRcode());
                    bundle.putString("eventid",eventId);

                    QRCodeActivity fragobj = new QRCodeActivity();
                    fragobj.setArguments(bundle);
                    fragobj.show(fm,"drff");
                }
                else {
                    if (phoneNumber!=null) {
                        startActivity(new Intent(SingleEventActivity.this, EventRegister.class)
                                .putExtra("eventName", eventDetails.getmName())
                                .putExtra("eventId", eventDetails.getmEventId())
                                .putExtra("eventType", eventDetails.getmEventTeamSize()));
                        finish();
                    }
                    else
                         startActivity(new Intent(SingleEventActivity.this,RegisterActivity.class));
                }
            }
        });

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
                Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(getBaseContext(), "Invalid Selection!", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void displayTickets(String phoneNumber) {

        String url = getResources().getString(R.string.get_events_qr_code);
        url += phoneNumber;
        Toast.makeText(this, "url: " + url, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("My success", "" + response);
                try {
                    JSONObject obj1 = new JSONObject(response);
                    JSONArray ticketDetails = obj1.getJSONArray("data");
                    for (int i = 0; i < ticketDetails.length(); i++) {
                        JSONObject obj2 = ticketDetails.getJSONObject(i);
                        if(obj2.getString("eventid").equals(eventId)) {
                            TicketModel = new QRTicketModel();

                            TicketModel.setPaymentStatus(obj2.getInt("paymentstatus"));
                            TicketModel.setArrivalStatus(obj2.getInt("arrived"));
                            TicketModel.setQRcode(obj2.getString("qrcode"));
                            TicketModel.setEventID(obj2.getString("eventid"));
                            TicketModel.setTimeStamp(obj2.getLong("timestamp"));
                            registerButton.setText("View Ticket");
                        }

                    }
                }
                // Try and catch are included to handle any errors due to JSON
                catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Tickets", e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Tickets", "" + error);
            }
        });
        queue.add(request);
    }

}
