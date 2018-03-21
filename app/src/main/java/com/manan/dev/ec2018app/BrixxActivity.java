package com.manan.dev.ec2018app;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.manan.dev.ec2018app.Adapters.BrixxAdapter;

public class BrixxActivity extends AppCompatActivity {

    private BrixxAdapter iViewPagerAdapter;
    private ViewPager iViewpager;
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener;
    private ImageView next_btn, pre_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_brixx);

        iViewpager = (ViewPager) findViewById(R.id.ivpPager);
        iViewPagerAdapter = new BrixxAdapter(getSupportFragmentManager());
        iViewpager.setAdapter(iViewPagerAdapter);
        iViewpager.addOnPageChangeListener(viewPagerPageChangeListener);

        next_btn = findViewById(R.id.next);
        pre_btn = findViewById(R.id.pre);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iViewpager.setCurrentItem(getCurrItem() + 1);
            }
        });

        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iViewpager.setCurrentItem(getCurrItem() - 1);
            }
        });
    }

    private int getCurrItem() {
        return iViewpager.getCurrentItem();
    }
}
