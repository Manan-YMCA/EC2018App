package com.manan.dev.ec2018app;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.loading.newton.NewtonCradleLoading;

public class UserLoginActivity extends AppCompatActivity {
    TextView  ECText,ContinueText,GuestText, ReadyText;
    Button LoginButton;ImageView ECLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_login);
        final NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading=(NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.start();
        newtonCradleLoading.setLoadingColor(R.color.colorPrimaryDark);
        ECText=(TextView)this.findViewById(R.id.ECText);
        ContinueText=(TextView)this.findViewById(R.id.ContinueText);
        GuestText=(TextView)this.findViewById(R.id.GuestText);
        ReadyText=(TextView)this.findViewById(R.id.ReadyText);
        LoginButton=(Button)this.findViewById(R.id.LoginButton);
        ECLogo=(ImageView)this.findViewById(R.id.EClogo);
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ECText.setVisibility(View.VISIBLE);
                ContinueText.setVisibility(View.VISIBLE);
                GuestText.setVisibility(View.VISIBLE);
                ReadyText.setVisibility(View.VISIBLE);
                LoginButton.setVisibility(View.VISIBLE);
                ECLogo.setVisibility(View.VISIBLE);
                newtonCradleLoading.stop();

            }
        },3000);

    }
}
