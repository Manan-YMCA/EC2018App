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
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.Models.UserDetails;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText mobileNum;
    Button loginMobileNum;
    private UserDetails userDetails;
    private ProgressDialog mProgress;
    private LinearLayout lLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        userDetails = new UserDetails();
        mobileNum = (EditText) findViewById(R.id.mobileNum);
        loginMobileNum = (Button) findViewById(R.id.login_mobileNum);
        lLayoutView = (LinearLayout) findViewById(R.id.llayout_login_activity);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("I am working");
        mProgress.setTitle("yes i am");
        mProgress.setCanceledOnTouchOutside(false);
        loginMobileNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                if (checker) {
                    userDetails.setmPhone(mobileNum.getText().toString());
                    checkOTP(mobileNum.getText().toString());

                }
            }
        });

        //TODO
        //add an edit text to enter a mobile number
        // a button to login.
        // when login button is clicked hit get_user_api.
        //if user exists the send otp to user
        // if otp is verified save the number in shared preferences and open content activity.

    }


    private void checkOTP(String mobileNum) {
        boolean check = true;
        if(isNetworkAvailable()) {
            if (check) {
                getDetails(userDetails, mobileNum);
            }
        } else {
            Snackbar.make(lLayoutView, "CONNECT TO A HOTSPOT", Snackbar.LENGTH_LONG).show();
        }
    }

    private void getDetails(final UserDetails userDetails, final String phone) {
        String url = getResources().getString(R.string.get_user_details_api) + phone;
        Toast.makeText(this, "URL: " + url, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            Long success = obj1.getLong("success");
                            if(success == 1) {
                                SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE).edit();
                                editor.putString("Phone", userDetails.getmPhone());
                                editor.apply();
                                startActivity(new Intent(LoginActivity.this, ContentActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                finish();
                            }
                            else {
                                Snackbar.make(lLayoutView, "USER DOES NOT EXIST", Snackbar.LENGTH_LONG).show();
                            }
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
//                            Toast.makeText(FBLoginActivity.this, "Error aagya1", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.e("Volley", "Error");
                    }
                }
        );
        queue.add(obreq);
    }

    private Boolean validateCredentials() {
        if (mobileNum.getText().toString().equals("")) {
            mobileNum.setError("Enter a Phone Number");
            return false;
        }
        if (!Patterns.PHONE.matcher(mobileNum.getText().toString()).matches()) {
            mobileNum.setError("Enter a valid Phone Number");
            return false;
        }
        if (mobileNum.getText().toString().length() != 10) {
            mobileNum.setError("Enter a valid Phone Number");
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
