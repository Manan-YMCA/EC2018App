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
    private ImageView aboutBackButton,aboutChangeLeft,aboutChangeRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutBackButton = findViewById(R.id.about_back_button);
        abtviewPager = (ViewPager) findViewById(R.id.about_viewpager);
        abtViewPagerAdapter = new AboutAdapter(getSupportFragmentManager());
        abtviewPager.setAdapter(abtViewPagerAdapter);
        abtviewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        aboutChangeRight=findViewById(R.id.about_change_button_right);
        aboutChangeLeft=findViewById(R.id.about_change_button_left);

        check();
        aboutBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        aboutChangeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         abtviewPager.setCurrentItem(0,true);

            }
        });
        aboutChangeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               abtviewPager.setCurrentItem(1,true);
            }
        });
    }
    private int getCurrItem() {
        return abtviewPager.getCurrentItem();
    }


    private void check() {
        if(abtviewPager.getCurrentItem()==0)
        {
            aboutChangeLeft.setVisibility(View.GONE);
            aboutChangeRight.setVisibility(View.VISIBLE);
        }
        else if(abtviewPager.getCurrentItem()==1)
        {
            aboutChangeRight.setVisibility(View.GONE);
            aboutChangeLeft.setVisibility(View.VISIBLE);
        }



    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            check();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

}