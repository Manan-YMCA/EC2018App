package com.manan.dev.ec2018app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, userEmail, userPhone, userCollege;
    private RelativeLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText) findViewById(R.id.et_reg_name);
        userEmail = (EditText) findViewById(R.id.et_reg_email);
        userCollege = (EditText) findViewById(R.id.et_reg_clg);
        userPhone = (EditText) findViewById(R.id.et_reg_mob);
        view = (RelativeLayout) findViewById(R.id.rl_main_view);

        TextView submit = (TextView) findViewById(R.id.tv_reg_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                if(checker){
                    //TODO
                    //first check OTP
                    //the register the user
                }
            }
        });

    }

    private Boolean validateCredentials() {
        if(!isNetworkAvailable()){
            Snackbar.make(view, "Check your Internet Connection", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if(userName.getText().toString().equals("")){
            userName.setError("Enter a User Name");
            return false;
        }
        if(userEmail.getText().toString().equals("")){
            userEmail.setError("Enter an Email Address");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches()){
            userEmail.setError("Enter a Valid Email Address");
            return false;
        }
        if(userPhone.getText().toString().equals("")){
            userPhone.setError("Enter a Phone Number");
            return false;
        }
        if(!Patterns.PHONE.matcher(userPhone.getText().toString()).matches()){
            userPhone.setError("Enter a valid Phone Number");
            return false;
        }
        if(userPhone.getText().toString().length() != 10){
            userPhone.setError("Enter a valid Phone Number");
            return false;
        }
        if(userCollege.getText().toString().equals("")){
            userCollege.setError("Enter a College Name");
            return false;
        }
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

