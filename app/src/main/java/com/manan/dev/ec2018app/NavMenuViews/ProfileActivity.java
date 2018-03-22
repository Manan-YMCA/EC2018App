package com.manan.dev.ec2018app.NavMenuViews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manan.dev.ec2018app.ContentActivity;
import com.manan.dev.ec2018app.Models.UserDetails;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Utilities.ConnectivityReciever;
import com.manan.dev.ec2018app.Utilities.GetRoundedImage;
import com.manan.dev.ec2018app.Utilities.MyApplication;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements ConnectivityReciever.ConnectivityReceiverListener {
    private UserDetails userDetails;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private RelativeLayout profilePictureFrame;
    private ImageView profilePicture, backbutton;
    private TextView textView1;
    private ProfileTracker mProfileTracker;
    private FirebaseAuth mAuth;
    private ProgressBar ivBar, detailsBar;
    private TextView tvCollege;
    private TextView tvName;
    private TextView tvMail;
    private TextView tvPhone;
    private EditText input1;
    private EditText input2;
    private EditText input3;
    private String phoneNumber;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        callbackManager = CallbackManager.Factory.create();
        userDetails = new UserDetails();
        userDetails.setmFbId("null");
        tvName = (TextView) findViewById(R.id.tv_name);
        tvMail = (TextView) findViewById(R.id.tv_email);
        tvCollege = (TextView) findViewById(R.id.tv_college);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        final String EMAIL = "email";
        backbutton = findViewById(R.id.pr_back_button);


        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        phoneNumber = prefs.getString("Phone", null);
        if (phoneNumber == null) {
            Log.e("TAG", "onCreate : " + "Shared pref no data!");
        }

        ivBar = (ProgressBar) findViewById(R.id.pb_profile_image);
        detailsBar = (ProgressBar) findViewById(R.id.pb_user_profile);

        ivBar.setVisibility(View.VISIBLE);
        detailsBar.setVisibility(View.VISIBLE);


        mAuth = FirebaseAuth.getInstance();
        textView1 = (TextView) findViewById(R.id.tv6);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        profilePictureFrame = (RelativeLayout) findViewById(R.id.profile_picture_layout);
        profilePicture = (ImageView) findViewById(R.id.profile_pic);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ivBar.setVisibility(View.VISIBLE);
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.d("fbProfile", currentProfile.getFirstName() + "bond");
                            mProfileTracker.stopTracking();
                        }
                    };
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.d("fbProfile", profile.getFirstName());
                }

                userDetails.setmFbId(loginResult.getAccessToken().getUserId());
                registerUser(userDetails);
                profilePicture.setImageBitmap(null);
                checkStatus();
                handleFirebaseLogin(loginResult.getAccessToken());
                Log.d("hogya", "hogya");
            }

            @Override
            public void onCancel() {
                Toast.makeText(ProfileActivity.this, "Facebook login cancelled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("TAG", "onError: " + exception.getMessage());
            }
        });
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.dashboard_image);
//        Bitmap resized = Bitmap.createScaledBitmap(largeIcon, 100, 100, true);
//        GetRoundedImage getRoundedImage = new GetRoundedImage();
//        Bitmap conv_bm = getRoundedImage.getRoundedShape(resized);
//        BitmapDrawable background = new BitmapDrawable(conv_bm);
//        profilePicture.setBackground(background);
        final LayoutInflater factory = LayoutInflater.from(this);
        ImageView editButton = (ImageView) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input1 = new EditText(ProfileActivity.this);
                input2 = new EditText(ProfileActivity.this);
                input3 = new EditText(ProfileActivity.this);
                flag = false;
                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                final LinearLayout layout = new LinearLayout(ProfileActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.removeAllViews();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(30, 0, 30, 0);
                final TextView tv_name = new TextView(ProfileActivity.this);
                tv_name.setLayoutParams(layoutParams);


                tv_name.setText("Name");
                input1.setLayoutParams(layoutParams);
                final TextView tv_email = new TextView(ProfileActivity.this);
                tv_email.setText("Email");
                tv_email.setLayoutParams(layoutParams);
                input2.setLayoutParams(layoutParams);
                final TextView tv_college = new TextView(ProfileActivity.this);
                tv_college.setText("College Name");

                Log.e("TAG", "onClick: " + userDetails.getmName());

                input1.setText(userDetails.getmName());
                input3.setText(userDetails.getmCollege());
                input2.setText(userDetails.getEmail());
                tv_college.setLayoutParams(layoutParams);
                input3.setLayoutParams(layoutParams);
                layout.addView(tv_name);
                layout.addView(input1);
                layout.addView(tv_email);
                layout.addView(input2);
                layout.addView(tv_college);
                layout.addView(input3);
                alert.setIcon(R.drawable.vector_pencil_black).setTitle("Edit Profile").setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                Log.i("AlertDialog", "TextEntry 1 Entered " + input1.getText().toString());
                                Log.i("AlertDialog", "TextEntry 2 Entered " + input2.getText().toString());
    /* User clicked OK so do some stuff */
                                boolean checker = checkDetails();
                                if (checker) {
                                    Log.e("TAG", "onClick: hello" );
                                    detailsBar.setVisibility(View.VISIBLE);
                                    userDetails.setmName(input1.getText().toString());
                                    userDetails.setEmail(input2.getText().toString());
                                    userDetails.setmCollege(input3.getText().toString());
                                    tvName.setText("");
                                    tvCollege.setText("");
                                    tvMail.setText("");
                                    tvPhone.setText("");

                                    registerUser(userDetails);
                                    layout.removeAllViews();
                                } else {
                                    MDToast.makeText(ProfileActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                                }
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
     /*
     * User clicked cancel so do some stuff
     */
                                layout.removeAllViews();
                            }
                        });

               alert.show();

            }
        });

    }

    private boolean checkDetails() {
        if (!isNetworkAvailable()) {
            MDToast.makeText(ProfileActivity.this, "No Internet Access", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            return false;
        }
        if (input1.getText().toString().equals("")) {
            input1.setError("Enter a Name");
            return false;
        }
        if (input2.getText().toString().equals("")){
            input2.setError("Enter an Email Address");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(input2.getText().toString()).matches()) {
            input2.setError("Enter a Valid Email Address");
            Log.e("TAG", "checkDetails: " + input2.getText().toString() );
            return false;
        }
        if (input3.getText().toString().equals("")){
            input3.setError("Enter a College Name");
        }
        return true;
    }

    private void handleFirebaseLogin(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("loginStatus", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            checkStatus();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("loginStatus", "signInWithCredential:failure", task.getException());
                            Toast.makeText(ProfileActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void getDetails(final String phone) {
        Log.i("tg", "bgg");
        final TextView tvName = (TextView) findViewById(R.id.tv_name);
        final TextView tvMail = (TextView) findViewById(R.id.tv_email);
        final TextView tvCollege = (TextView) findViewById(R.id.tv_college);
        final TextView tvPhone = (TextView) findViewById(R.id.tv_phone);
        String url = getResources().getString(R.string.get_user_details_api) + phone;

        Log.e("TAG", "getDetails url: " + url);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            detailsBar.setVisibility(View.GONE);
                            JSONObject obj1 = new JSONObject(response);
                            JSONObject obj = obj1.getJSONObject("data");
                            Log.e("TAG", "onResponse: " + obj.getString("name"));
                            tvName.setText(obj.getString("name"));
                            tvMail.setText(obj.getString("email"));
                            tvCollege.setText(obj.getString("college"));
                            tvPhone.setText(phone);
                            userDetails.setmName(obj.getString("name"));
                            userDetails.setEmail(obj.getString("email"));
                            userDetails.setmCollege(obj.getString("college"));
                            userDetails.setmPhone(phone);
                            checkStatus();
//                            if (!obj.getString("fb").equals("null")) {
//                                userDetails.setmFbId(obj.getString("fb"));
//                                loginButton.setVisibility(View.GONE);
//                                addPhoto(userDetails);
//                            } else {
//                                userDetails.setmFbId(null);
//                            }
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
                        Log.e("TAG", "onErrorResponse: " + error.getMessage());
                        Log.e("Volley", "Error");
                    }
                }
        );
        queue.add(obreq);
    }

    private void registerUser(final UserDetails userDetails) {
        String url = getResources().getString(R.string.register_user_api);
        Log.e("TAG", "registerUser url : " + url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                detailsBar.setVisibility(View.GONE);
                tvName.setText(userDetails.getmName());
                tvMail.setText(userDetails.getEmail());
                tvCollege.setText(userDetails.getmCollege());
                tvPhone.setText(userDetails.getmPhone());

                Log.e("TAG", "onResponse: " + response);
                Log.i("My success", "" + response);

                Log.e("TAG", "onResponse: " + "API info updated!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "onErrorResponse error : " + error);
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

    public void addPhoto() {
        try {

            Picasso.with(getApplicationContext()).load(Profile.getCurrentProfile().getProfilePictureUri(800, 800));
            Log.d("fbProfile", Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getProfilePictureUri(800, 800).toString());
            Picasso.with(ProfileActivity.this).load(Profile.getCurrentProfile().getProfilePictureUri(800, 800).toString())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                            GetRoundedImage getRoundedImage = new GetRoundedImage();
                            Bitmap conv_bm = getRoundedImage.getRoundedShape(bitmap);
                            BitmapDrawable background = new BitmapDrawable(conv_bm);
                            profilePictureFrame.setBackground(background);
                            profilePicture.setImageResource(R.drawable.frame_profile_2);
                            Log.e("TAG", "onBitmapLoaded: " + "Image Loaded!");
                            ivBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

        } catch (Exception e) {
            Log.d("fbProfile", e.getMessage());
        }


    }

    private void checkStatus() {
        AccessToken token = AccessToken.getCurrentAccessToken();

        if (token == null) {
            ivBar.setVisibility(View.GONE);
            profilePicture.setImageDrawable(getResources().getDrawable(R.drawable.profile_frame));
        } else {
            loginButton.setVisibility(View.GONE);
            textView1.setText("On days when we're not working on our college fest, we help recruit for James Bond. Check your details to see if the biodata we're sending is correct.");
            addPhoto();
            //    Picasso.with(getApplicationContext()).load(Profile.getCurrentProfile().getProfilePictureUri(200,200));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
            phoneNumber = prefs.getString("Phone", null);
            if (phoneNumber == null) {
                Log.e("TAG", "onCreate : " + "Shared pref no data!");
            }
            getDetails(phoneNumber);
        } else {
            MDToast.makeText(ProfileActivity.this, "Connect to Internet", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        phoneNumber = prefs.getString("Phone", null);
        if (phoneNumber == null) {
            Log.e("TAG", "onCreate : " + "Shared pref no data!");
        }
        if(isNetworkAvailable()){
            getDetails(phoneNumber);
        }
        MyApplication.getInstance().setConnectivityListener(ProfileActivity.this);
    }
}
