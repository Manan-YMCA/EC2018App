package com.manan.dev.ec2018app;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Models.Coordinators;
import com.manan.dev.ec2018app.Models.EventDetails;
import com.manan.dev.ec2018app.Utilities.GifImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    private DatabaseController mDatabaseController;
    private ArrayList<EventDetails> allEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        allEvents = new ArrayList<>();

        retreiveEvents();
        try {
            mDatabaseController = new DatabaseController(getApplicationContext());
        } catch (Exception e) {
            Log.d("DBChecker", e.getMessage());
        }


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
*/
            GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);

            //    gifImageView.setGifImageResource(R.drawable.tenor);
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
//                Intent i = new Intent(SplashScreen.this, UserLoginActivity.class);
//                startActivity(i);
                Intent myIntent = new Intent(SplashScreen.this, UserLoginActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(SplashScreen.this, R.anim.fade_in, R.anim.fade_out);
                SplashScreen.this.startActivity(myIntent, options.toBundle());
                // close this activity
                finish();
            }
        }, 5000);
    }


    private void retreiveEvents() {
        if (isNetworkAvailable()) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = getResources().getString(R.string.get_all_events_api);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray eventArray = object.getJSONArray("data");
                        EventDetails event = new EventDetails();
                        for (int i = 0; i < eventArray.length(); i++) {
                            JSONObject currEvent = eventArray.getJSONObject(i);
                            if (currEvent.has("timing"))
                                event.setmName(currEvent.getString("title"));
                            if (currEvent.has("fee"))
                                event.setmFees(currEvent.getLong("fee"));
                            if (currEvent.has("timing")) {
                                JSONObject timing = currEvent.getJSONObject("timing");
                                if (timing.has("from"))
                                    event.setmStartTime(timing.getLong("from"));
                                if (timing.has("to"))
                                    event.setmEndTime(timing.getLong("to"));
                            }
                            if (currEvent.has("clubname"))
                                event.setmClubname(currEvent.getString("clubname"));
                            if (currEvent.has("category"))
                                event.setmCategory(currEvent.getString("category"));
                            if (currEvent.has("desc"))
                                event.setmDesc(currEvent.getString("desc"));
                            if (currEvent.has("venue"))
                                event.setmVenue(currEvent.getString("venue"));
                            if (currEvent.has("rules"))
                                event.setmRules(currEvent.getString("rules"));
                            if (currEvent.has("photolink")) {
                                event.setmPhotoUrl(currEvent.getString("photolink"));
                            } else {
                                event.setmPhotoUrl(null);
                            }
                            event.setmCoordinators(new ArrayList<Coordinators>());
                            if (currEvent.has("coordinators")) {
                                JSONArray coordinators = currEvent.getJSONArray("coordinators");
                                for (int j = 0; j < coordinators.length(); j++) {
                                    JSONObject coordinatorsDetail = coordinators.getJSONObject(j);
                                    Coordinators coord = new Coordinators();
                                    if (coordinatorsDetail.has("_id"))
                                        coord.setmCoordId(coordinatorsDetail.getString("_id"));
                                    if (coordinatorsDetail.has("phone"))
                                        coord.setmCoordPhone(coordinatorsDetail.getLong("phone"));
                                    if (coordinatorsDetail.has("name"))
                                        coord.setmCoordName(coordinatorsDetail.getString("name"));
                                    event.getmCoordinators().add(coord);
                                }
                            }
                            event.setmPrizes(new ArrayList<String>());
                            if (currEvent.has("prizes")) {
                                JSONObject prize = currEvent.getJSONObject("prizes");
                                if (prize.has("prize1"))
                                    event.getmPrizes().add(prize.getString("prize1"));
                                if (prize.has("prize2"))
                                    event.getmPrizes().add(prize.getString("prize2"));
                                if (prize.has("prize3"))
                                    event.getmPrizes().add(prize.getString("prize3"));
                            } else {
                                event.getmPrizes().add(null);
                                event.getmPrizes().add(null);
                                event.getmPrizes().add(null);
                            }
                            if (currEvent.has("_id"))
                                event.setmEventId(currEvent.getString("_id"));
                            if (currEvent.has("eventtype")) {
                                event.setmEventTeamSize(currEvent.getString("eventtype"));
                                Log.d("DBChecker", currEvent.getString("eventtype") + " " + event.getmEventTeamSize());
                                //Toast.makeText(ContentActivity.this, currEvent.getString("eventtype"), Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(ContentActivity.this, event.getmEventId() + " " + event.getmPrizes().toString(), Toast.LENGTH_LONG).show();
                            allEvents.add(event);
                            updateDatabase();
                        }
                    } catch (Exception e) {
                        Toast.makeText(SplashScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("DBChecker", e.getMessage());
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(stringRequest);
        }

    }

    private void updateDatabase() {
        if (allEvents.size() > mDatabaseController.getCount()) {
            for (EventDetails eventDetails : allEvents) {
                mDatabaseController.addEntryToDb(eventDetails);
            }
        } else {
            for (EventDetails eventDetails : allEvents) {
                mDatabaseController.updateDb(eventDetails);
            }
        }
        Log.d("DBChecker", Integer.toString(mDatabaseController.getCount()));
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

