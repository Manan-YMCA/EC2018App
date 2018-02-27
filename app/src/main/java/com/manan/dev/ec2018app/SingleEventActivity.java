package com.manan.dev.ec2018app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Models.EventDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class SingleEventActivity extends AppCompatActivity {

    Button registerButton;
    TextView eventDateTextView, eventTimeTextView, locationTextView, locationFullTextView, hostClubTextView, feesTextView,
            typeOfEventTextView, firstPrizeTextView, secondPrizeTextView, thirdPrizeTextView, descriptionTextView,
            rulesTextView,coordsHeading;
    RelativeLayout dateTimeRelativeLayout, locationRelativeLayout, coordsRelativeLayout;
    LinearLayout coordsLinearLayout;
    SmoothProgressBar spb;
    private DatabaseController getEventDetails;
    private EventDetails eventDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        String eventId = getIntent().getStringExtra("eventId");
        Toast.makeText(this, eventId, Toast.LENGTH_SHORT).show();
        getEventDetails = new DatabaseController(SingleEventActivity.this);
        eventDetails = new EventDetails();
        spb = (SmoothProgressBar) findViewById(R.id.progress_bar_google_now);
        spb.setVisibility(View.INVISIBLE);

        registerButton = (Button) findViewById(R.id.btn_register);

        eventDateTextView = (TextView) findViewById(R.id.tv_event_date);
        eventTimeTextView = (TextView) findViewById(R.id.tv_event_time);
        locationTextView = (TextView) findViewById(R.id.tv_event_location);

        hostClubTextView = (TextView) findViewById(R.id.tv_host);
        feesTextView = (TextView) findViewById(R.id.tv_fees);
        typeOfEventTextView = (TextView) findViewById(R.id.tv_type_of_event);
        firstPrizeTextView = (TextView) findViewById(R.id.tv_prize_first);
        secondPrizeTextView = (TextView) findViewById(R.id.tv_prize_second);
        thirdPrizeTextView = (TextView) findViewById(R.id.tv_prize_third);
        descriptionTextView = (TextView) findViewById(R.id.tv_description);
        rulesTextView = (TextView) findViewById(R.id.tv_rules);

        dateTimeRelativeLayout = (RelativeLayout) findViewById(R.id.rl_time_date);
        locationRelativeLayout = (RelativeLayout) findViewById(R.id.rl_location);
        coordsLinearLayout = (LinearLayout) findViewById(R.id.ll_coordinators);
        coordsHeading =(TextView)findViewById(R.id.tv_coords_heading);
        eventDetails = getEventDetails.retreiveEventsByID(eventId);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("DD MM YY", Locale.ENGLISH);

        locationTextView.setText(eventDetails.getmVenue());
        hostClubTextView.setText(eventDetails.getmClubname());
        Long fees = eventDetails.getmFees();
        feesTextView.setText(Long.toString(fees));
        typeOfEventTextView.setText(eventDetails.getmEventTeamSize());
        if(eventDetails.getmEventTeamSize().equals("NA")){
            typeOfEventTextView.setVisibility(View.GONE);
        }
        if(eventDetails.getmCoordinators().equals("NULL")){
            coordsLinearLayout.setVisibility(View.GONE);
            coordsHeading.setVisibility(View.GONE);
        }
        descriptionTextView.setText(eventDetails.getmDesc());
        rulesTextView.setText(eventDetails.getmRules());
        dateTimeRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
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
                Toast.makeText(SingleEventActivity.this, "Mujhe Event pe Register karna hai", Toast.LENGTH_SHORT).show();
                spb.setVisibility(View.VISIBLE);
                startActivity(new Intent(SingleEventActivity.this, EventRegister.class));
            }
        });

        coordsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SingleEventActivity.this, "Linear Layout ko inflate(not relative one) kar kar ke Coords display karna hai and single layout event coords xml ka bhi use hai!", Toast.LENGTH_LONG).show();
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
}
