package com.manan.dev.ec2018app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manan.dev.ec2018app.Models.UserDetails;
import com.manan.dev.ec2018app.Utilities.GetRoundedImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private UserDetails userDetails;
    private ProgressDialog mProgress;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ProgressDialog ProgressDialog;
    private RelativeLayout profilePictureFrame;
    private ImageView profilePicture;
    private TextView textView1;
    private TextView textView2;
    private ProfileTracker mProfileTracker;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        callbackManager = CallbackManager.Factory.create();
        userDetails = new UserDetails();
        userDetails.setmFbId("null");
        final String EMAIL = "email";
        ProgressDialog = new ProgressDialog(this);
        ProgressDialog.setMessage("I am working");
        ProgressDialog.setTitle("Registering again");

        mAuth = FirebaseAuth.getInstance();
        ProgressDialog.setCanceledOnTouchOutside(false);
        textView1 = (TextView)findViewById(R.id.tv5);
        textView2 = (TextView)findViewById(R.id.tv6);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        profilePictureFrame = (RelativeLayout) findViewById(R.id.profile_picture_layout);
        profilePicture = (ImageView)findViewById(R.id.profile_pic);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(Profile.getCurrentProfile() == null) {
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
                handleFirebaseLogin(loginResult.getAccessToken());
                Log.d("hogya", "hogya");
            }

            @Override
            public void onCancel() {
                Toast.makeText(ProfileActivity.this, "Fb login cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(ProfileActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("I am working");
        mProgress.setTitle("yes i am");
        mProgress.setCanceledOnTouchOutside(false);

        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        final String phoneNumber = prefs.getString("Phone", null);
        if (phoneNumber == null) {
            Toast.makeText(this, "shared pref no data", Toast.LENGTH_SHORT).show();
        }
        checkStatus();
        getDetails(phoneNumber);
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.dashboard_image);
//        Bitmap resized = Bitmap.createScaledBitmap(largeIcon, 100, 100, true);
//        GetRoundedImage getRoundedImage = new GetRoundedImage();
//        Bitmap conv_bm = getRoundedImage.getRoundedShape(resized);
//        BitmapDrawable background = new BitmapDrawable(conv_bm);
//        profilePicture.setBackground(background);
        LayoutInflater factory = LayoutInflater.from(this);
        ImageView editButton = (ImageView) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView tvName = (TextView) findViewById(R.id.tv_name);
                final TextView tvMail = (TextView) findViewById(R.id.tv_email);
                final TextView tvCollege = (TextView) findViewById(R.id.tv_college);
                final TextView tvPhone = (TextView) findViewById(R.id.tv_phone);
                final EditText input1 = new EditText(ProfileActivity.this);
                final EditText input2 = new EditText(ProfileActivity.this);
                final EditText input3 = new EditText(ProfileActivity.this);
                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                final LinearLayout layout = new LinearLayout(ProfileActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.removeAllViews();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(30, 0, 30, 0);
                TextView tv_name = new TextView(ProfileActivity.this);
                tv_name.setLayoutParams(layoutParams);


                tv_name.setText("Name");
                input1.setLayoutParams(layoutParams);
                TextView tv_email = new TextView(ProfileActivity.this);
                tv_email.setText("Email");
                tv_email.setLayoutParams(layoutParams);
                input2.setLayoutParams(layoutParams);
                TextView tv_college = new TextView(ProfileActivity.this);
                tv_college.setText("College Name");
                Toast.makeText(ProfileActivity.this, userDetails.getmName(), Toast.LENGTH_SHORT).show();
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
                                userDetails.setmName(input1.getText().toString());
                                userDetails.setEmail(input2.getText().toString());
                                userDetails.setmCollege(input3.getText().toString());
                                registerUser(userDetails);
                                tvName.setText(userDetails.getmName());
                                tvMail.setText(userDetails.getEmail());
                                tvCollege.setText(userDetails.getmCollege());
                                layout.removeAllViews();
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
                            Toast.makeText(ProfileActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "URL: " + url, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            JSONObject obj = obj1.getJSONObject("data");
                            Toast.makeText(ProfileActivity.this, obj.getString("name"), Toast.LENGTH_LONG).show();
                            tvName.setText(obj.getString("name"));
                            tvMail.setText(obj.getString("email"));
                            tvCollege.setText(obj.getString("college"));
                            tvPhone.setText(phone);
                            userDetails.setmName(obj.getString("name"));
                            userDetails.setEmail(obj.getString("email"));
                            userDetails.setmCollege(obj.getString("college"));
                            userDetails.setmPhone(phone);
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
                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this, "Error aagya2", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(getApplicationContext(), "dfdsfsd" + response, Toast.LENGTH_SHORT).show();
                Log.i("My success", "" + response);
                mProgress.dismiss();
                Toast.makeText(ProfileActivity.this, "Api info updated", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
                mProgress.dismiss();
            }
        })
        {
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
            Log.d("fbProfile", Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getProfilePictureUri(800, 800).toString());
            Picasso.with(ProfileActivity.this).load(Profile.getCurrentProfile().getProfilePictureUri(800,800).toString())
                    .into(new Target(){
                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap,Picasso.LoadedFrom from){
                            GetRoundedImage getRoundedImage = new GetRoundedImage();
                            Bitmap conv_bm = getRoundedImage.getRoundedShape(bitmap);
                            BitmapDrawable background = new BitmapDrawable(conv_bm);
                            profilePictureFrame.setBackground(background);
                            profilePicture.setImageResource(R.drawable.frame_profile_2);
                            Toast.makeText(ProfileActivity.this, "Image loaded", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            Log.d("fbProfile", errorDrawable.toString());
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable){

                        }
                    });

        } catch (Exception e) {
            Log.d("fbProfile", e.getMessage());
        }


    }
    private void checkStatus(){
        AccessToken token = AccessToken.getCurrentAccessToken();

        if (token == null) {
            profilePicture.setImageDrawable(getResources().getDrawable(R.drawable.profile_frame));
        }
        else{
            loginButton.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            addPhoto();
        //    Picasso.with(getApplicationContext()).load(Profile.getCurrentProfile().getProfilePictureUri(200,200));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
