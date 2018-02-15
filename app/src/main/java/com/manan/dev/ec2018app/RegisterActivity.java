package com.manan.dev.ec2018app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RelativeLayout rl_button = (RelativeLayout)findViewById(R.id.hint_set) ;
        rl_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editTextemail = (EditText)findViewById(R.id.email);
                if( editTextemail.getText().toString().length() == 0 )
                    editTextemail.setError("Email is required!" );
                EditText editTextname = (EditText)findViewById(R.id.name);
                if(editTextname.getText().toString().length() == 0)
                    editTextname.setError("Name is required!" );
                EditText editTextmobile = (EditText)findViewById(R.id.mob);
                if (editTextmobile.getText().length() ==0 )
                    editTextmobile.setError("Mobile number is required!" );
                EditText editTextcollege = (EditText)findViewById(R.id.clg);
                if (editTextcollege.getText().toString().length() ==0 )
                    editTextcollege.setError("Mobile number is required!" );
                boolean check;
                Pattern p;
                Matcher m;

                String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                p = Pattern.compile(EMAIL_STRING);

                m = p.matcher(editTextemail.getText().toString());
                check = m.matches();

                if(!check) {
                    editTextemail.setError("Not Valid Email");
                }
                String mobile = editTextmobile.getText().toString();
boolean check1 = isValidPhone(mobile);
                if(!check1) {
                    editTextmobile.setError("Not Valid Phone no.");
                }
            }
        });

    }

    private boolean isValidPhone(String phone) {

            boolean check=false;
            if(!Pattern.matches("[a-zA-Z]+", phone))
            {
                if(phone.length() < 6 || phone.length() > 13)
                {
                    check = false;

                }
                else
                {
                    check = true;

                }
            }
            else
            {
                check=false;
            }
            return check;
        }
    }

