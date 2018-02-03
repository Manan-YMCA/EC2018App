package com.manan.dev.ec2018app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class OtpVerificationActivity extends AppCompatActivity {

    EditText etPhoneNumber;
    EditText etOtpCode;
    TextView tvVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        etOtpCode = (EditText) findViewById(R.id.et_otp_code);
        tvVerify = (TextView) findViewById(R.id.tv_verify);

        etPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPhoneNumber.setText("");
            }
        });

        etOtpCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtpVerificationActivity.this,ContentActivity.class));
            }
        });

        //8 spaces
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyUserPhone(etPhoneNumber.getText().toString());

            }
        });
    }

    private void verifyUserPhone(String s) {
        //TODO
        //read 6 digit otp from message and display in etOtpCode EditText
        //add 8 spaces between pairs of 3 digits
        //add single space between each digit
    }
}
