package com.manan.dev.ec2018app.Xunbao;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.R;

public class XunbaoActivity extends FragmentActivity {

    BottomNavigationView navBar;
    ViewPager viewPagerXunbao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunbao);

        navBar = (BottomNavigationView) findViewById(R.id.xunbao_nav_bar);
        navBar.getMenu().getItem(1).setChecked(true);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                Toast.makeText(XunbaoActivity.this, Integer.toString(item.getOrder()), Toast.LENGTH_SHORT).show();
                viewPagerXunbao.setCurrentItem(item.getOrder() - 1);
                return false;
            }
        });

        viewPagerXunbao = (ViewPager) findViewById(R.id.xunbao_view_pager);
        setUpViewPager();
        viewPagerXunbao.setCurrentItem(1);

        viewPagerXunbao.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                navBar.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpViewPager() {
        XunbaoTabsPagerAdapter adapter = new XunbaoTabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutFragment(), "ABOUT");
        adapter.addFragment(new QuestionFragment(), "QUESTIONS");
        adapter.addFragment(new LeaderboardFragment(), "LEADERBOARD");
        viewPagerXunbao.setAdapter(adapter);
    }


}
