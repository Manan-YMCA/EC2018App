package com.manan.dev.ec2018app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Models.Coordinators;
import com.manan.dev.ec2018app.Models.EventDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    private DatabaseController mDatabaseController;
    private ArrayList<EventDetails> allEvents;
    private static final int DURATION = 5000;
    private ValueAnimator mCurrentAnimator;
    private final Matrix mMatrix = new Matrix();
    private ImageView mImageView;
    private float mScaleFactor;
    private int mDirection = 1;
    private RectF mDisplayRect = new RectF();
    private IncomingHandler incomingHandler;
    private static boolean offline = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (offline) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            offline = false;
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        incomingHandler = new IncomingHandler(SplashScreen.this);
        allEvents = new ArrayList<>();

        mImageView = (ImageView) findViewById(R.id.background_one);
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mScaleFactor = (float) mImageView.getHeight() / (float) mImageView.getDrawable().getIntrinsicHeight();
                mMatrix.postScale(mScaleFactor, mScaleFactor);
                mImageView.setImageMatrix(mMatrix);
                animate();
            }
        });

    }

    private void animate() {
        updateDisplayRect();
        if (mDirection == 1) {
            animate(mDisplayRect.left, mDisplayRect.left - (mDisplayRect.right - mImageView.getWidth()));
        }
    }

    private void animate(float from, float to) {
        mCurrentAnimator = ValueAnimator.ofFloat(from, to);
        mCurrentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();

                mMatrix.reset();
                mMatrix.postScale(mScaleFactor, mScaleFactor);
                mMatrix.postTranslate(value, 0);

                mImageView.setImageMatrix(mMatrix);

            }
        });
        mCurrentAnimator.setDuration(DURATION);
        mCurrentAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                retreiveEvents();
                startActivity(new Intent(SplashScreen.this, UserLoginActivity.class));
                overridePendingTransition(R.anim.right_to_left_anim, R.anim.end_activity_anim);
                finish();
            }
        });
        mCurrentAnimator.start();
    }

    private void updateDisplayRect() {
        mDisplayRect.set(0, 0, mImageView.getDrawable().getIntrinsicWidth(), mImageView.getDrawable().getIntrinsicHeight());
        mMatrix.mapRect(mDisplayRect);
    }


    private void retreiveEvents() {
        if (isNetworkAvailable()) {
            RequestQueue queue = Volley.newRequestQueue(SplashScreen.this);
            String url = getResources().getString(R.string.get_all_events_api);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray eventArray = object.getJSONArray("data");
                        for (int i = 0; i < eventArray.length(); i++) {
                            EventDetails event = new EventDetails();
                            JSONObject currEvent = eventArray.getJSONObject(i);
                            event.setmUniqueKey(i);
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
                                //Toast.makeText(ContentActivity.this, currEvent.getString("eventtype"), Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(ContentActivity.this, event.getmEventId() + " " + event.getmPrizes().toString(), Toast.LENGTH_LONG).show();
                            allEvents.add(event);
                        }
                        incomingHandler.sendEmptyMessage(0);
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


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class IncomingHandler extends Handler {
        private WeakReference<SplashScreen> yourActivityWeakReference;

        IncomingHandler(SplashScreen yourActivity) {
            yourActivityWeakReference = new WeakReference<>(yourActivity);
            mDatabaseController = new DatabaseController(getApplicationContext());
        }

        @Override
        public void handleMessage(Message message) {
            if (yourActivityWeakReference != null) {
                SplashScreen yourActivity = yourActivityWeakReference.get();

                switch (message.what) {
                    case 0:
                        updateDatabase();
                        break;
                }
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
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentAnimator.removeAllUpdateListeners();
    }

    @Override
    public void onBackPressed() {
        mCurrentAnimator.removeAllUpdateListeners();
        super.onBackPressed();
    }
}

