package com.manan.dev.ec2018app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.Models.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, userEmail, userPhone, userCollege;
    private RelativeLayout view;
    private ProgressDialog mProgress;
    private TextView LoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText) findViewById(R.id.et_reg_name);
        userEmail = (EditText) findViewById(R.id.et_reg_email);
        userCollege = (EditText) findViewById(R.id.et_reg_clg);
        userPhone = (EditText) findViewById(R.id.et_reg_mob);
        view = (RelativeLayout) findViewById(R.id.rl_main_view);
        LoginText = (TextView) this.findViewById(R.id.tv_log_in);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("I am working");
        mProgress.setTitle("yes i am");
        mProgress.setCanceledOnTouchOutside(false);

        TextView submit = (TextView) findViewById(R.id.tv_reg_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.show();
                Boolean checker = validateCredentials();
                if(checker) {
                    UserDetails userDetails = new UserDetails();
                    userDetails.setEmail(userEmail.getText().toString());
                    userDetails.setmName(userName.getText().toString());
                    userDetails.setmCollege(userCollege.getText().toString());
                    userDetails.setmPhone(userPhone.getText().toString());
                    Toast.makeText(RegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    checkOTP(userDetails);
                }
            }
        });

        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void checkOTP(UserDetails userDetails) {
        boolean check = true;
        if(check){
            registerUser(userDetails);
        }
    }

    private void registerUser(final UserDetails userDetails) {
        String url = getResources().getString(R.string.register_user_api);
        Toast.makeText(this, "url: " + url, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), "dfdsfsd"+response, Toast.LENGTH_SHORT).show();
                Log.i("My success",""+response);
                mProgress.dismiss();
                SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE).edit();
                editor.putString("Phone", userDetails.getmPhone());
                editor.apply();
                startActivity(new Intent(getApplicationContext(), ContentActivity.class));
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my error :"+error, Toast.LENGTH_LONG).show();
                Log.i("My error",""+error);
                mProgress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String, String>();
                map.put("name",userDetails.getmName());
                map.put("email",userDetails.getEmail());
                map.put("phone",userDetails.getmPhone());
                map.put("college", userDetails.getmCollege());
                return map;
            }
        };
        queue.add(request);
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

