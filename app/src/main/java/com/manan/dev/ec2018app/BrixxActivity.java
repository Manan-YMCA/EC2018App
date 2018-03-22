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
    private ImageView next_btn, pre_btn;
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener;


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

        check();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iViewpager.getCurrentItem()==3)
                {
                    iViewpager.setCurrentItem(0);

                }
                else
                {
                iViewpager.setCurrentItem(getCurrItem() + 1);}
                check();
            }

        });

        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(iViewpager.getCurrentItem()==0)
               {
                   iViewpager.setCurrentItem(3);
               }

               else {
                   iViewpager.setCurrentItem(getCurrItem() - 1);
               }
                check();
            }
        });



    }

    private void check() {
        if(iViewpager.getCurrentItem()==0)
        {
            pre_btn.setVisibility(View.GONE);
        }
        else if(iViewpager.getCurrentItem()==3)
        {
            next_btn.setVisibility(View.GONE);
        }
        else
        {
            next_btn.setVisibility(View.VISIBLE);
            pre_btn.setVisibility(View.VISIBLE);
        }


    }


    private int getCurrItem() {
        return iViewpager.getCurrentItem();
    }






}
