package com.manan.dev.ec2018app.Xunbao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.manan.dev.ec2018app.Fragments.FragmentFbLogin;
import com.manan.dev.ec2018app.LoginActivity;
import com.manan.dev.ec2018app.ProfileActivity;
import com.manan.dev.ec2018app.R;

public class XunbaoActivity extends FragmentActivity implements FragmentFbLogin.fbLoginButton {

    BottomNavigationView navBar;
    ViewPager viewPagerXunbao;
    private String phoneNumber;
    private FragmentFbLogin fbLogin;

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

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            viewPagerXunbao.setCurrentItem(0);
            navBar.getMenu().getItem(0).setChecked(true);
        } else {
            viewPagerXunbao.setCurrentItem(1);
            navBar.getMenu().getItem(1).setChecked(true);
        }
        viewPagerXunbao.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        phoneNumber = prefs.getString("Phone", null);
        if (phoneNumber == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(XunbaoActivity.this);
            builder.setTitle("Login Required!");
            builder.setMessage("To continue, you must login with facebook");
            builder.setPositiveButton("Continue", new Dialog.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent in = new Intent(XunbaoActivity.this, LoginActivity.class);
                    in.putExtra("parent", "xunbao");
                    startActivity(in);

                }
            });
            builder.setNegativeButton("Cancel", new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            final AlertDialog dialog = builder.create();
            dialog.show();

        } else {

            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                //Toast.makeText(this, "shit just happened again", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                fbLogin = new FragmentFbLogin();
                fbLogin.show(fm, "fbLoginFragment");

            }
        }

        setUpViewPager();

    }

    @Override
    protected void onPause() {
        Log.d("pauser", "activity paused");
        super.onPause();
    }

    @Override
    public void fbStatus(final Boolean status, String userId) {

    }
}
