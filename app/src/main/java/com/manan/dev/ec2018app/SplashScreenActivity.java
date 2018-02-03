package com.manan.dev.ec2018app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.saeid.fabloading.LoadingView;

public class SplashScreenActivity extends AppCompatActivity {
    LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        int marvel_1 =  R.raw.manan;
        int marvel_2 =R.raw.srijan1;
        int marvel_3 =  R.raw.jhalak;
        int marvel_4 = R.raw.microbird;

        mLoadingView.addAnimation(Color.parseColor("#ff0000"), marvel_1,
                LoadingView.FROM_LEFT);
        mLoadingView.addAnimation(Color.parseColor("#ff0000"), marvel_2,
                LoadingView.FROM_TOP);
        mLoadingView.addAnimation(Color.parseColor("#ff0000"), marvel_3,
                LoadingView.FROM_RIGHT);
        mLoadingView.addAnimation(Color.parseColor("#ff0000"), marvel_4,
                LoadingView.FROM_BOTTOM);

        mLoadingView.addListener(new LoadingView.LoadingListener() {
            @Override public void onAnimationStart(int currentItemPosition) {
            }

            @Override public void onAnimationRepeat(int nextItemPosition) {
            }

            @Override public void onAnimationEnd(int nextItemPosition) {
            }
        });

        mLoadingView.startAnimation();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                mLoadingView.pauseAnimation();
                Intent i = new Intent(SplashScreenActivity.this, ContentActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 10000);
    }
}
