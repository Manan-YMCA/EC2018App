package com.manan.dev.ec2018app.NavMenuViews;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.manan.dev.ec2018app.Adapters.AboutAdapter;
import com.manan.dev.ec2018app.R;

public class AboutActivity extends AppCompatActivity {

    private ViewPager abtviewPager;
    private AboutAdapter abtViewPagerAdapter;
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener;
    private ImageView aboutBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutBackButton = findViewById(R.id.about_back_button);
        abtviewPager = (ViewPager) findViewById(R.id.about_viewpager);
        abtViewPagerAdapter = new AboutAdapter(getSupportFragmentManager());
        abtviewPager.setAdapter(abtViewPagerAdapter);
        abtviewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        aboutBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}