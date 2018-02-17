package com.manan.dev.ec2018app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.victor.loading.newton.NewtonCradleLoading;

public class UserLoginActivity extends AppCompatActivity {
    TextView ECText, ContinueText, GuestText, ReadyText;
    Button LoginButton;
    ImageView ECLogo, backImage;
    View lineView;
    LinearLayout guestLogin;

    private NewtonCradleLoading newtonCradleLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_login);

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        String restoredText = prefs.getString("Phone", null);
        if (restoredText != null) {
            startActivity(new Intent(getApplicationContext(), ContentActivity.class));
            finish();
        }

        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);

        guestLogin = (LinearLayout) this.findViewById(R.id.guest_user_ll);
        ECText = (TextView) this.findViewById(R.id.ECText);
        ContinueText = (TextView) this.findViewById(R.id.ContinueText);
        GuestText = (TextView) this.findViewById(R.id.GuestText);
        ReadyText = (TextView) this.findViewById(R.id.ReadyText);
        LoginButton = (Button) this.findViewById(R.id.LoginButton);
        ECLogo = (ImageView) this.findViewById(R.id.EClogo);
        lineView = this.findViewById(R.id.view);
        backImage = (ImageView) this.findViewById(R.id.iv_background);
        newtonCradleLoading.setVisibility(View.INVISIBLE);
        backImage.setAlpha(0f);
        backImage.setVisibility(View.INVISIBLE);
        lineView.setAlpha(0f);
        lineView.setVisibility(View.INVISIBLE);
        ECText.setAlpha(0f);
        ECText.setVisibility(View.INVISIBLE);
        ContinueText.setAlpha(0f);
        ContinueText.setVisibility(View.INVISIBLE);
        GuestText.setAlpha(0f);
        GuestText.setVisibility(View.INVISIBLE);
        ReadyText.setAlpha(0f);
        ReadyText.setVisibility(View.INVISIBLE);
        LoginButton.setAlpha(0f);
        LoginButton.setVisibility(View.INVISIBLE);
        ECLogo.setAlpha(0f);
        ECLogo.setVisibility(View.INVISIBLE);

        if (getIntent().getBooleanExtra("logout", false)) {
            postAnimation();
        } else {
            newtonCradleLoading.setVisibility(View.VISIBLE);
            newtonCradleLoading.start();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    newtonCradleLoading.stop();

                    postAnimation();

                    newtonCradleLoading.animate().alpha(0.0f).setDuration(1500).setListener(null);

                }
            }, 1700);
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

    private void postAnimation() {
        backImage.setVisibility(View.VISIBLE);
        backImage.animate().alpha(1.0f).setDuration(1500).setListener(null);
        lineView.setVisibility(View.VISIBLE);
        lineView.animate().alpha(1.0f).setDuration(1500).setListener(null);
        ECText.setVisibility(View.VISIBLE);
        ECText.animate().alpha(1.0f).setDuration(1500).setListener(null);
        ContinueText.setVisibility(View.VISIBLE);
        ContinueText.animate().alpha(1.0f).setDuration(1500).setListener(null);
        GuestText.setVisibility(View.VISIBLE);
        GuestText.animate().alpha(1.0f).setDuration(1500).setListener(null);
        ReadyText.setVisibility(View.VISIBLE);
        ReadyText.animate().alpha(1.0f).setDuration(1500).setListener(null);
        LoginButton.setVisibility(View.VISIBLE);
        LoginButton.animate().alpha(1.0f).setDuration(1500).setListener(null);
        ECLogo.setVisibility(View.VISIBLE);
        ECLogo.animate().alpha(1.0f).setDuration(1500).setListener(null);

    }
}
