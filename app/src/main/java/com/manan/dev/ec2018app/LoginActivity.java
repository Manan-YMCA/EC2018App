package com.manan.dev.ec2018app;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.facebook.AccessToken;
import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Fragments.FragmentFbLogin;
import com.manan.dev.ec2018app.Fragments.FragmentOtpChecker;
import com.manan.dev.ec2018app.Models.QRTicketModel;
import com.manan.dev.ec2018app.Models.UserDetails;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.manan.dev.ec2018app.Fragments.FragmentOtpChecker.REQUEST_ID_MULTIPLE_PERMISSIONS;

public class LoginActivity extends AppCompatActivity implements FragmentOtpChecker.otpCheckStatus, FragmentFbLogin.fbLoginButton {
    EditText mobileNum;
    TextView NeedHelp;
    Button loginMobileNum;
    private TextView registerView;
    private UserDetails userDetails;
    private RelativeLayout RelativeView;
    String parent;
    private ArrayList<QRTicketModel> userTickets;
    private DatabaseController databaseController;
    private ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        parent = getIntent().getStringExtra("parent");
        userTickets = new ArrayList<>();
        userDetails = new UserDetails();
        pbLogin = (ProgressBar) findViewById(R.id.pb_login);
        mobileNum = (EditText) findViewById(R.id.mobileNum);
        loginMobileNum = (Button) findViewById(R.id.login_mobileNum);
        RelativeView = (RelativeLayout) findViewById(R.id.rl_main_view);
        NeedHelp = (TextView) findViewById(R.id.need_help);
        registerView = (TextView) findViewById(R.id.tv_register_option);
        databaseController = new DatabaseController(LoginActivity.this);

        String first = "Haven't completed the details? ";
        String next = "<font color='#f55246'>Click Here!</font>";
        registerView.setText(Html.fromHtml(first + next));

        NeedHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = "manantechnosurge@gmail.com";
                String subject = "Need Help";
                String messg = "I am facing a problem regarding...\n";
                sendEmailBug(to, subject, messg);
            }
        });

        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent.equals("xunbao") || parent.equals("ct")) {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class)
                            .putExtra("parent", "xunbao"));
                    finish();
                } else {
                    finish();
                }
            }
        });


        loginMobileNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                if (checker) {
                    pbLogin.setVisibility(View.VISIBLE);
                    userDetails.setmPhone(mobileNum.getText().toString());
                    checkOTP(mobileNum.getText().toString());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void checkOTP(String mobileNum) {
        getDetails(userDetails, mobileNum);
    }

    private void getDetails(final UserDetails userDetails, final String phone) {
        String url = getResources().getString(R.string.get_user_details_api) + phone;
        Log.e("TAG", "getDetails url: " + url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            Long success = obj1.getLong("success");
                            if (success == 1) {
                                checkAndRequestPermissions();
                                if(ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
                                    FragmentManager fm = getFragmentManager();
                                    FragmentOtpChecker otpChecker = new FragmentOtpChecker();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("phone", mobileNum.getText().toString());
                                    otpChecker.setArguments(bundle);
                                    otpChecker.show(fm, "otpCheckerFragment");
                                }
                            } else {
                                pbLogin.setVisibility(View.GONE);
//                                Snackbar.make(RelativeView, "Please register yourself first!", Snackbar.LENGTH_LONG).show();
                                MDToast.makeText(LoginActivity.this,"Please register yourself first!", Toast.LENGTH_LONG,MDToast.TYPE_INFO).show();
                                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                            Log.e("TAG", "onResponse: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "onErrorResponse: " + "Errorrrrrr");
                        Log.e("Volley", "Error");
                    }
                }
        );
        queue.add(obreq);
    }


    @Override
    public void updateResult(boolean status) {
        if (status) {
            pbLogin.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE).edit();
            editor.putString("Phone", userDetails.getmPhone());
            editor.apply();
            AccessToken token = AccessToken.getCurrentAccessToken();
            if (token != null) {
                pbLogin.setVisibility(View.GONE);
                startSession();
            } else {
                FragmentManager fmFB = getFragmentManager();
                FragmentFbLogin fbLogin = new FragmentFbLogin();
                fbLogin.show(fmFB, "fbLoginFragment");
                if (fbLogin.isVisible()) {
                    pbLogin.setVisibility(View.GONE);
                }
            }
        } else {
            pbLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void fbStatus(Boolean status, String userId) {
        if (status) {
            pbLogin.setVisibility(View.VISIBLE);
            userDetails.setmFbId(userId);
            registerUser(userDetails);
        } else {
            pbLogin.setVisibility(View.VISIBLE);
            startSession();
        }
    }

    private void startSession() {
        if (parent.equals("xunbao") || parent.equals("ct")) {
            pbLogin.setVisibility(View.GONE);
            finish();
        } else {
            startActivity(new Intent(LoginActivity.this, ContentActivity.class));
            pbLogin.setVisibility(View.GONE);
            finish();
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
    private Boolean validateCredentials() {
        if (!isNetworkAvailable()) {
            MDToast.makeText(LoginActivity.this, "Connect to internet!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
            return false;
        }
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

    private void sendEmailBug(String to, String subject, String msg) {

        Uri uri = Uri.parse("mailto:")
                .buildUpon()
                .appendQueryParameter("subject", subject)
                .appendQueryParameter("body", msg)
                .build();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }

    private void registerUser(final UserDetails userDetails) {

        String url = getResources().getString(R.string.register_user_api);
        Log.e("TAG", "registerUser url: " + url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pbLogin.setVisibility(View.GONE);
                MDToast.makeText(LoginActivity.this, "Registered!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                startSession();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "onErrorResponse my errorrrrrrrrrrr: " + error);
                Log.i("My error", "" + error);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS){

            FragmentManager fm = getFragmentManager();
            FragmentOtpChecker otpChecker = new FragmentOtpChecker();
            Bundle bundle = new Bundle();
            bundle.putString("phone", mobileNum.getText().toString());
            otpChecker.setArguments(bundle);
            otpChecker.show(fm, "otpCheckerFragment");
        }
    }
}
