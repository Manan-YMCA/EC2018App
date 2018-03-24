package com.manan.dev.ec2018app;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.manan.dev.ec2018app.Fragments.FragmentFbLogin;
import com.manan.dev.ec2018app.Fragments.FragmentOtpChecker;
import com.manan.dev.ec2018app.Models.UserDetails;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.manan.dev.ec2018app.Fragments.FragmentOtpChecker.REQUEST_ID_MULTIPLE_PERMISSIONS;

public class RegisterActivity extends AppCompatActivity implements FragmentOtpChecker.otpCheckStatus, FragmentFbLogin.fbLoginButton {

    private EditText userName, userEmail, userPhone, userCollege;
    private RelativeLayout view;
    private ProgressDialog mProgress;
    private TextView LoginText;
    private UserDetails userDetails;
    private String parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);

        parent = getIntent().getStringExtra("parent");

        userName = (EditText) findViewById(R.id.et_reg_name);
        userEmail = (EditText) findViewById(R.id.et_reg_email);
        userCollege = (EditText) findViewById(R.id.et_reg_clg);
        userPhone = (EditText) findViewById(R.id.et_reg_mob);
        view = (RelativeLayout) findViewById(R.id.rl_main_view);
        LoginText = (TextView) this.findViewById(R.id.tv_log_in);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Registering You");
        mProgress.setTitle("Please Wait");
        mProgress.setCanceledOnTouchOutside(false);
        userDetails = new UserDetails();
        userDetails.setmFbId("null");

        String first = "Already a User? ";
        String next = "<font color='#f55246'>Just Login!</font>";
        LoginText.setText(Html.fromHtml(first + next));

        TextView submit = (TextView) findViewById(R.id.tv_reg_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                if (checker) {
                    mProgress.show();
                    userDetails.setEmail(userEmail.getText().toString());
                    userDetails.setmName(userName.getText().toString());
                    userDetails.setmCollege(userCollege.getText().toString());
                    userDetails.setmPhone(userPhone.getText().toString());
                    //MDToast.makeText(RegisterActivity.this, "Done!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                    checkOTP(userDetails);
                }
            }
        });

        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent.equals("xunbao") || parent.equals("ct")) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class)
                            .putExtra("parent", "xunbao"));
                }
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class)
                        .putExtra("parent", "normal"));
            }
        });

    }

    private void checkOTP(UserDetails userDetails) {
        checkAndRequestPermissions();
        if(ContextCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
            FragmentManager fm = getFragmentManager();
            FragmentOtpChecker otpChecker = new FragmentOtpChecker();
            Bundle bundle = new Bundle();
            bundle.putString("phone", userPhone.getText().toString());
            otpChecker.setArguments(bundle);
            otpChecker.show(fm, "otpCheckerFragment");
        }
        mProgress.dismiss();
    }

    private void registerUser(final UserDetails userDetails) {
        String url = getResources().getString(R.string.register_user_api);
        Log.e("TAG", "registerUser url : " + url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MDToast.makeText(RegisterActivity.this, "Registered!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                Log.i("My success", "" + response);
                mProgress.dismiss();
                SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE).edit();
                editor.putString("Phone", userDetails.getmPhone());
                editor.apply();
                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token != null) {
                    startSession();
                } else {
                    FragmentManager fm = getFragmentManager();
                    FragmentFbLogin fbLogin = new FragmentFbLogin();
                    fbLogin.show(fm, "fbLoginFragment");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "onErrorResponse error : " + error);
                Log.i("My error", "" + error);
                mProgress.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("name", userDetails.getmName());
                map.put("email", userDetails.getEmail());
                map.put("phone", userDetails.getmPhone());
                map.put("college", userDetails.getmCollege());
                map.put("fb", userDetails.getmFbId());
                return map;
            }
        };
        queue.add(request);
    }

    private Boolean validateCredentials() {
        if (!isNetworkAvailable()) {
            Snackbar.make(view, "Check your Internet Connection", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (userName.getText().toString().equals("")) {
            userName.setError("Enter a User Name");
            return false;
        }
        if (userEmail.getText().toString().equals("")) {
            userEmail.setError("Enter an Email Address");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches()) {
            userEmail.setError("Enter a Valid Email Address");
            return false;
        }
        if (userPhone.getText().toString().equals("")) {
            userPhone.setError("Enter a Phone Number");
            return false;
        }
        if (!Patterns.PHONE.matcher(userPhone.getText().toString()).matches()) {
            userPhone.setError("Enter a valid Phone Number");
            return false;
        }
        if (userPhone.getText().toString().length() != 10) {
            userPhone.setError("Enter a valid Phone Number");
            return false;
        }
        if (userCollege.getText().toString().equals("")) {
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

    @Override
    public void updateResult(boolean status) {
        if (status) {
            mProgress.show();
            registerUser(userDetails);
        } else {
            mProgress.dismiss();
        }
    }

    @Override
    public void fbStatus(Boolean status, String userId) {
        if (status) {
            mProgress.show();
            userDetails.setmFbId(userId);
            registerUser(userDetails);
        } else {
            startSession();
        }
    }

    private void checkAndRequestPermissions() {
        int receiveSMS = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    private void startSession() {
        if (parent.equals("xunbao") || parent.equals("ct")) {
            mProgress.dismiss();
            finish();
        } else {
            startActivity(new Intent(RegisterActivity.this, ContentActivity.class));
            mProgress.dismiss();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS){
            Bundle bundle = new Bundle();
            FragmentManager fm = getFragmentManager();
            FragmentOtpChecker otpChecker = new FragmentOtpChecker();
            bundle.putString("phone", userDetails.getmPhone());
            otpChecker.setArguments(bundle);
            otpChecker.show(fm, "otpCheckerFragment");
        }
    }
}
