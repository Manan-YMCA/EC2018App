package com.manan.dev.ec2018app.Xunbao;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manan.dev.ec2018app.R;

public class XunbaoActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private XunbaoTabsPagerAdapter XAdapter;
    private ActionBar actionBar;
    private String[] tabs = {"About", "Question", "Leaderboard"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Xunbao");
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunbao);

        viewPager = (ViewPager) findViewById(R.id.pager);


        TextView tv = new TextView(getApplicationContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);

        // Set text to display in TextView
        tv.setText("Xunbao");

        tv.setTextSize(25);
        // Set the text color of TextView
        tv.setTextColor(Color.parseColor("#ffffff"));

        // Set TextView text alignment to center
        tv.setGravity(Gravity.CENTER);


        actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);

        XAdapter = new XunbaoTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(XAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(XunbaoActivity.this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        viewPager.setCurrentItem(1);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

    }
}
