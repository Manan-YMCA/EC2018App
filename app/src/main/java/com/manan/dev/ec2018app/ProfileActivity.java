package com.manan.dev.ec2018app;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.Models.UserDetails;
import com.manan.dev.ec2018app.Utilities.GetRoundedImage;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        final String phoneNumber = prefs.getString("Phone", null);
        if (phoneNumber == null) {
            Toast.makeText(this, "shared pref no data", Toast.LENGTH_SHORT).show();
        }
        getDetails(phoneNumber);
        RelativeLayout profilePicture = (RelativeLayout) findViewById(R.id.profile_picture_layout);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.dashboard_image);
        Bitmap resized = Bitmap.createScaledBitmap(largeIcon, 100, 100, true);
        GetRoundedImage getRoundedImage = new GetRoundedImage();
        Bitmap conv_bm = getRoundedImage.getRoundedShape(resized);
        BitmapDrawable background = new BitmapDrawable(conv_bm);
        profilePicture.setBackground(background);
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.text_entry, null);
        ImageView editButton = (ImageView) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                final EditText input1 = new EditText(ProfileActivity.this);
                input1.setText("Prerna");
                input1.setLayoutParams(layoutParams);
                TextView tv_email = new TextView(ProfileActivity.this);
                tv_email.setText("Email");
                tv_email.setLayoutParams(layoutParams);
                final EditText input2 = new EditText(ProfileActivity.this);
                input2.setText("prerna@gmail.com");
                input2.setLayoutParams(layoutParams);
                TextView tv_college = new TextView(ProfileActivity.this);
                tv_college.setText("College Name");
                tv_college.setLayoutParams(layoutParams);
                final EditText input3 = new EditText(ProfileActivity.this);
                input3.setText("YMCAUST");
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
    private void getDetails(final String phone) {
      final TextView tvName = (TextView)findViewById(R.id.tv_name);
        final TextView tvMail=(TextView)findViewById(R.id.tv_email);
        final TextView tvCollege = (TextView)findViewById(R.id.tv_college);
        final TextView tvPhone = (TextView)findViewById(R.id.tv_phone);
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
                            Toast.makeText(ProfileActivity.this, obj.getString("name"), Toast.LENGTH_LONG).show();
                            tvName.setText(obj.getString("name"));
                            tvMail.setText(obj.getString("email"));
                            tvCollege.setText(obj.getString("college"));
                            tvPhone.setText(phone);

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            // If an error occurs, this prints the error to the log
//                            Toast.makeText(FBLoginActivity.this, "Error aagya1", Toast.LENGTH_SHORT).show();
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

}
