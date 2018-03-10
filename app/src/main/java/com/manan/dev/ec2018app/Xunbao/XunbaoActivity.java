package com.manan.dev.ec2018app.Xunbao;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.Toast;

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
            }

            @Override
            public void onPageSelected(int position) {
                navBar.getMenu().getItem(position).setChecked(true);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
