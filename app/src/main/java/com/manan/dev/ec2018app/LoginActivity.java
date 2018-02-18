package com.manan.dev.ec2018app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        userDetails = new UserDetails();
        mobileNum=(EditText)findViewById(R.id.mobileNum);
        loginMobileNum=(Button)findViewById(R.id.login_mobileNum);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("I am working");
        mProgress.setTitle("yes i am");
        mProgress.setCanceledOnTouchOutside(false);
        loginMobileNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                if(checker)
                {
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
        if(check){
            getDetails(userDetails,mobileNum);
        }
    }
    private void getDetails(final UserDetails userDetails, final String phone) {
        String url = getResources().getString(R.string.get_user_details_api)+phone;
        Toast.makeText(this, "URL: " + url, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            JSONObject obj = obj1.getJSONObject("data");
                            Toast.makeText(LoginActivity.this, obj.getString("name"), Toast.LENGTH_LONG).show();
                            userDetails.setmName(obj.getString("name"));
                            userDetails.setEmail(obj.getString("email"));
                            userDetails.setmCollege(obj.getString("college"));
                            userDetails.setmPhone(phone);
                            userDetails.setmFbId(obj.getString("fbID"));
                            registerUser(userDetails);
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

    private void registerUser(final UserDetails userDetails) {
        String url = getResources().getString(R.string.register_user_api);
        Toast.makeText(this, "url: " + url, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), "abc"+response, Toast.LENGTH_SHORT).show();
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
    private Boolean validateCredentials()
    {
        if(mobileNum.getText().toString().equals("")){
            mobileNum.setError("Enter a Phone Number");
            return false;
        }
        if(!Patterns.PHONE.matcher(mobileNum.getText().toString()).matches()){
            mobileNum.setError("Enter a valid Phone Number");
            return false;
        }
        if(mobileNum.getText().toString().length() != 10){
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
