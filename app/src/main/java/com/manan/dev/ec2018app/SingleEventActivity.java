package com.manan.dev.ec2018app;

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

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class SingleEventActivity extends AppCompatActivity {

    Button registerButton;
    TextView eventDateTextView, eventTimeTextView, locationTextView, locationFullTextView, hostClubTextView, feesTextView,
            typeOfEventTextView, firstPrizeTextView, secondPrizeTextView, thirdPrizeTextView, descriptionTextView,
            rulesTextView;
    RelativeLayout dateTimeRelativeLayout, locationRelativeLayout, coordsRelativeLayout;
    LinearLayout coordsLinearLayout;
    SmoothProgressBar spb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        String eventId = getIntent().getStringExtra("eventId");
        Toast.makeText(this, eventId, Toast.LENGTH_SHORT).show();

        spb = (SmoothProgressBar) findViewById(R.id.progress_bar_google_now);
        spb.setVisibility(View.INVISIBLE);

        registerButton = (Button) findViewById(R.id.btn_register);

        eventDateTextView = (TextView) findViewById(R.id.tv_event_date);
        eventTimeTextView = (TextView) findViewById(R.id.tv_event_time);
        locationTextView = (TextView) findViewById(R.id.tv_event_location);
        locationFullTextView = (TextView) findViewById(R.id.tv_event_location_full);
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
                Toast.makeText(SingleEventActivity.this, "Mujhe Event pe Register karna hai", Toast.LENGTH_SHORT).show();
                spb.setVisibility(View.VISIBLE);
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
