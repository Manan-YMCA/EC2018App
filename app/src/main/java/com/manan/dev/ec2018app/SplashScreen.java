package com.manan.dev.ec2018app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.manan.dev.ec2018app.Utilities.GifImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);




        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
*/
            GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
    //    gifImageView.setGifImageResource(R.drawable.tenor);
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
//                Intent i = new Intent(SplashScreen.this, UserLoginActivity.class);
//                startActivity(i);
                Intent myIntent = new Intent(SplashScreen.this, UserLoginActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(SplashScreen.this, R.anim.fade_in, R.anim.fade_out);
                SplashScreen.this.startActivity(myIntent, options.toBundle());
                // close this activity
                finish();
            }
        }, 5000);
    }
    }

