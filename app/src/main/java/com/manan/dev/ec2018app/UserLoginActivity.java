package com.manan.dev.ec2018app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class UserLoginActivity extends AppCompatActivity {
    TextView ContinueText, GuestText, ReadyText;
    Button LoginButton;
    LinearLayout guestLogin;

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private TextView[] dots;
    private MyViewPagerAdapter myViewPagerAdapter;
    private Timer timer;

    private Runnable Update;
    private Handler handler;
    private static boolean offline = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_user_login);

        guestLogin = (LinearLayout) this.findViewById(R.id.guest_user_ll);

        ContinueText = (TextView) this.findViewById(R.id.ContinueText);
        GuestText = (TextView) this.findViewById(R.id.GuestText);
        ReadyText = (TextView) this.findViewById(R.id.ReadyText);
        LoginButton = (Button) this.findViewById(R.id.LoginButton);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);


        layouts = new int[]{
                R.layout.start_slide,
                R.layout.start_slide2,
                R.layout.start_slide3,
        };
        addBottomDots(0);
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        String restoredText = prefs.getString("Phone", null);

        if (getIntent().getBooleanExtra("closeApp", false)) {
            finish();
        } else if (restoredText != null) {
            startActivity(new Intent(getApplicationContext(), ContentActivity.class));
            finish();
        }

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserLoginActivity.this, RegisterActivity.class)
                        .putExtra("parent", "normal"));
            }
        });

        guestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserLoginActivity.this, ContentActivity.class));
            }
        });

//changeslide();

    }

    @Override
    protected void onPause() {
        super.onPause();

        timer.cancel();
        handler.removeCallbacks(Update);

    }

    @Override
    protected void onResume() {
        super.onResume();
        changeslide();
    }


    void changeslide() {
        // Toast.makeText(UserLoginActivity.this,"Timer Started",Toast.LENGTH_SHORT).show();
        handler = new Handler();
        Update = new Runnable() {
            public void run() {
                int current = getItem(+1);
                if (current == layouts.length) {
                    current = 0;
                }
                viewPager.setCurrentItem(current++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }


        }, 5000, 5000);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // Toast.makeText(UserLoginActivity.this,"Timer Ended",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            timer.cancel();
            handler.removeCallbacks(Update);

            changeslide();


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

    };


    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    public void onBackPressed() {
        UserLoginActivity.this.finish();
        System.exit(0);
    }
}


