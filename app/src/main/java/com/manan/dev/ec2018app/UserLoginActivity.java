package com.manan.dev.ec2018app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.newton.NewtonCradleLoading;

public class UserLoginActivity extends AppCompatActivity {
    TextView ECText, ContinueText, GuestText, ReadyText;
    Button LoginButton;
    ImageView ECLogo, backImage;
    View lineView;
    LinearLayout guestLogin;

    private NewtonCradleLoading newtonCradleLoading;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private TextView[] dots;
    private MyViewPagerAdapter myViewPagerAdapter;

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
            Toast.makeText(UserLoginActivity.this, "Hello", Toast.LENGTH_SHORT).show();
        } else if (restoredText != null) {
            startActivity(new Intent(getApplicationContext(), ContentActivity.class));
            finish();
        } else if (getIntent().getBooleanExtra("logout", false)) {
           // postAnimation();
        } else {

        }

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, RegisterActivity.class));
            }
        });

        guestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, ContentActivity.class));
            }
        });

    }

//    private void postAnimation() {
//
//        backImage.setVisibility(View.VISIBLE);
//        backImage.setAlpha(1.0f);
//        lineView.setVisibility(View.VISIBLE);
//        lineView.setAlpha(1.0f);
//        ECText.setVisibility(View.VISIBLE);
//        ECText.setAlpha(1.0f);
//        ContinueText.setVisibility(View.VISIBLE);
//        ContinueText.setAlpha(1.0f);
//        GuestText.setVisibility(View.VISIBLE);
//        GuestText.setAlpha(1.0f);
//        ReadyText.setVisibility(View.VISIBLE);
//        ReadyText.setAlpha(1.0f);
//        LoginButton.setVisibility(View.VISIBLE);
//        LoginButton.setAlpha(1.0f);
//        ECLogo.setVisibility(View.VISIBLE);
//        ECLogo.setAlpha(1.0f);
//
//    }

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
            
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
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
}


