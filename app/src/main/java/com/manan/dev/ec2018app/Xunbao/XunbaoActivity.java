package com.manan.dev.ec2018app.Xunbao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.manan.dev.ec2018app.Fragments.FragmentFbLogin;
import com.manan.dev.ec2018app.LoginActivity;
import com.manan.dev.ec2018app.R;

public class XunbaoActivity extends FragmentActivity implements FragmentFbLogin.fbLoginButton {

    BottomNavigationView navBar;
    ViewPager viewPagerXunbao;
    private String phoneNumber;
    private FragmentFbLogin fbLogin;
    private XunbaoTabsPagerAdapter adapter;
    private String userFbId;
    private boolean flag = false;
    private ImageView backbutton_xun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunbao);

        backbutton_xun=findViewById(R.id.xunbao_back_button);
        navBar = (BottomNavigationView) findViewById(R.id.xunbao_nav_bar);
        navBar.getMenu().getItem(1).setChecked(true);


        backbutton_xun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                viewPagerXunbao.setCurrentItem(item.getOrder() - 1);
                if (viewPagerXunbao.getAdapter().getPageTitle(item.getOrder() - 1).toString().equals("QUESTIONS")) {
                    loadQuestionFragment fragment = (loadQuestionFragment) adapter.instantiateItem(viewPagerXunbao, item.getOrder() - 1);
                    if (AccessToken.getCurrentAccessToken() != null) {

                        fragment.makeQuestionVisible(userFbId);
                    } else {
                        fragment.makeQuestionVisible("notLoggedIn");
                    }
                }
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
                if (viewPagerXunbao.getAdapter().getPageTitle(position).toString().equals("QUESTIONS")) {
                    loadQuestionFragment fragment = (loadQuestionFragment) adapter.instantiateItem(viewPagerXunbao, position);
                    if (AccessToken.getCurrentAccessToken() != null) {
                        fragment.makeQuestionVisible(userFbId);
                    } else {
                        fragment.makeQuestionVisible("notLoggedIn");
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpViewPager() {
        adapter = new XunbaoTabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutFragment(), "ABOUT");
        adapter.addFragment(new QuestionFragment(), "QUESTIONS");
        adapter.addFragment(new LeaderboardFragment(), "LEADERBOARD");

        if (AccessToken.getCurrentAccessToken() == null) {
            viewPagerXunbao.post(new Runnable() {
                @Override
                public void run() {
                    viewPagerXunbao.setCurrentItem(0);
                }
            });
            navBar.getMenu().getItem(0).setChecked(true);
        } else {
            viewPagerXunbao.post(new Runnable() {
                @Override
                public void run() {
                    viewPagerXunbao.setCurrentItem(1);
                }
            });
            navBar.getMenu().getItem(1).setChecked(true);
        }
        viewPagerXunbao.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AccessToken.getCurrentAccessToken() != null) {
            userFbId = AccessToken.getCurrentAccessToken().getUserId();
        } else {
            userFbId = "notLoggedIn";
        }
        setUpViewPager();
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
                    flag = true;
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
            if (!flag) {
                if (AccessToken.getCurrentAccessToken() == null) {
                    FragmentManager fm = getFragmentManager();
                    fbLogin = new FragmentFbLogin();
                    fbLogin.show(fm, "fbLoginFragment");
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.removeFragment(0);
        adapter.removeFragment(0);
        adapter.removeFragment(0);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void fbStatus(final Boolean status, String userId) {
        if (status) {
            userFbId = userId;
        }
    }

    public interface loadQuestionFragment {
        void makeQuestionVisible(String fbId);
    }

}
