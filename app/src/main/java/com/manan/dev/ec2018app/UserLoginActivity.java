package com.manan.dev.ec2018app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.loading.newton.NewtonCradleLoading;

public class UserLoginActivity extends AppCompatActivity {
    TextView  ECText,ContinueText,GuestText, ReadyText;
    Button LoginButton;
    ImageView ECLogo,backImage;
    View lineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_login);

        final NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading=(NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.start();


        ECText = this.findViewById(R.id.ECText);
        ContinueText = this.findViewById(R.id.ContinueText);
        GuestText = this.findViewById(R.id.GuestText);
        ReadyText = this.findViewById(R.id.ReadyText);
        LoginButton = this.findViewById(R.id.LoginButton);
        ECLogo = this.findViewById(R.id.EClogo);
        lineView = this.findViewById(R.id.view);
        backImage = this.findViewById(R.id.iv_background);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //facebook login here
                startActivity(new Intent(UserLoginActivity.this, OTPVerificationActivity.class));
            }
        });


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

        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                newtonCradleLoading.stop();

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
        },2500);

    }
}
