package com.manan.dev.ec2018app.NavMenuViews;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.manan.dev.ec2018app.Adapters.DevelopersPagerAdapter;
import com.manan.dev.ec2018app.R;

public class DevelopersActivity extends AppCompatActivity {

    private ViewPager devviewPager;
    private DevelopersPagerAdapter mydevViewPagerAdapter;
    private ImageView devbut, crebut,splbut ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        devbut = findViewById(R.id.but_dev);
        crebut = findViewById(R.id.but_cre);
        splbut = findViewById(R.id.but_spl);

        devviewPager = (ViewPager) findViewById(R.id.vpPager);
        mydevViewPagerAdapter = new DevelopersPagerAdapter(getSupportFragmentManager());
        devviewPager.setAdapter(mydevViewPagerAdapter);
        devviewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        devbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devviewPager.setCurrentItem(0);
            }
        });
        devbut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.baseDark), PorterDuff.Mode.MULTIPLY);

        crebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devviewPager.setCurrentItem(1, true);
            }
        });

        splbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devviewPager.setCurrentItem(2, true);
            }
        });

        findViewById(R.id.dev_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                devbut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.baseDark), PorterDuff.Mode.MULTIPLY);
                crebut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.White), PorterDuff.Mode.MULTIPLY);
                splbut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.White), PorterDuff.Mode.MULTIPLY);
            }
            if (position == 1) {
                devbut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.White), PorterDuff.Mode.MULTIPLY);
                crebut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.baseDark), PorterDuff.Mode.MULTIPLY);
                splbut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.White), PorterDuff.Mode.MULTIPLY);
            }
            if (position == 2) {
                devbut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.White), PorterDuff.Mode.MULTIPLY);
                crebut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.White), PorterDuff.Mode.MULTIPLY);
                splbut.setColorFilter(ContextCompat.getColor(DevelopersActivity.this, R.color.baseDark), PorterDuff.Mode.MULTIPLY);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
}
