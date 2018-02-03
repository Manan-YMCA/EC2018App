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

public class SingleEventActivity extends AppCompatActivity {

    Button registerButton;
    TextView eventDateTextView, eventTimeTextView, locationTextView, locationFullTextView, hostClubTextView, feesTextView,
            typeOfEventTextView, firstPrizeTextView, secondPrizeTextView, thirdPrizeTextView, descriptionTextView,
            rulesTextView;
    RelativeLayout dateTimeRelativeLayout, locationRelativeLayout, coordsRelativeLayout;
    LinearLayout coordsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        registerButton = findViewById(R.id.btn_register);

        eventDateTextView = findViewById(R.id.tv_event_date);
        eventTimeTextView = findViewById(R.id.tv_event_time);
        locationTextView = findViewById(R.id.tv_event_location);
        locationFullTextView = findViewById(R.id.tv_event_location_full);
        hostClubTextView = findViewById(R.id.tv_host);
        feesTextView = findViewById(R.id.tv_fees);
        typeOfEventTextView = findViewById(R.id.tv_type_of_event);
        firstPrizeTextView = findViewById(R.id.tv_prize_first);
        secondPrizeTextView = findViewById(R.id.tv_prize_second);
        thirdPrizeTextView = findViewById(R.id.tv_prize_third);
        descriptionTextView = findViewById(R.id.tv_description);
        rulesTextView = findViewById(R.id.tv_rules);

        dateTimeRelativeLayout = findViewById(R.id.rl_time_date);
        locationRelativeLayout = findViewById(R.id.rl_location);
        coordsLinearLayout = findViewById(R.id.ll_coordinators);

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
