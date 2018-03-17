package com.manan.dev.ec2018app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.ec2018app.CulmycaTimesActivity;
import com.manan.dev.ec2018app.MapsActivity;
import com.manan.dev.ec2018app.Models.WhatsNewModel;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Xunbao.XunbaoActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class DashboardSliderFragment1 extends Fragment {
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    public ArrayList<WhatsNewModel> whatsnewarraylist;
    private ChildEventListener mChildEventListCurEvent;
    private ChildEventListener mChildEventListener;
    private TextView contenttv;
    private Handler handler;
    private Runnable update;
    private Timer timer;
    private int i;
    private boolean mIsRunning;
    private TimerTask mStatusChecker;


    // TODO: Rename parameter arguments, choose names that mat
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        handler = new Handler();
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_dashboard_slider_fragment1, container, false);
        mAuth = FirebaseAuth.getInstance();
        WhatsNewModel w;
        whatsnewarraylist = new ArrayList<>();
        whatsnewarraylist.add(new WhatsNewModel("Explore the new Culmyca'18 App! Various cool features are added and don't forget to register yourself first!", 7, "j"));
        contenttv = rootView.findViewById(R.id.content_whatnew);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("whatsnew");
        int t1 = whatsnewarraylist.size();
        Log.e("value", String.valueOf(t1));

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        attachDatabaseListener();

    mIsRunning=true;
      update.run();

    }

    @Override
    public void onPause() {
        super.onPause();
        mIsRunning=false;
        handler.removeCallbacks(mStatusChecker);

        detatchDatabaseListener();
    }

    private void detatchDatabaseListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }

    }

    private void attachDatabaseListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    WhatsNewModel w = dataSnapshot.getValue(WhatsNewModel.class);
                    whatsnewarraylist.add(w);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    try {
                        WhatsNewModel w = dataSnapshot.getValue(WhatsNewModel.class);
                        whatsnewarraylist.add(w);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    WhatsNewModel w = dataSnapshot.getValue(WhatsNewModel.class);
                    whatsnewarraylist.remove(w);

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mDatabaseReference.addChildEventListener(mChildEventListener);
            updateUI();

        }
    }

    private void updateUI() {
        i = 0;
         update = new Runnable() {
             @Override
             public void run() {
                 if (!mIsRunning) {
                     return; // stop when told to stop
                 }
                 contenttv.setText(whatsnewarraylist.get(i).getContent().toString());
                 //          timer.cancel();
                 //Toast.makeText(getActivity(),whatsnewarraylist.get(i[0]).getContent().toString(),Toast.LENGTH_SHORT).show();

                 contenttv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         if (whatsnewarraylist.get(i).getIntent() == 0) {
                             startActivity(new Intent(getActivity(), CulmycaTimesActivity.class));
                         }
                         if (whatsnewarraylist.get(i).getIntent() == 1) {
                             startActivity(new Intent(getActivity(), XunbaoActivity.class));
                         }
                         if (whatsnewarraylist.get(i).getIntent() == 2) {
                             startActivity(new Intent(getActivity(), MapsActivity.class));
                         }
                         else {

                         }
                     }
                 });//
                 // this function can change value of mInterval.
                 i++;
                 if (i == whatsnewarraylist.size()) {
                     i = 0;
                 }

                 handler.postDelayed(update, 7000);
             }

             void startRepeatingTask() {
                 mIsRunning = true;
                 update.run();
             }

             void stopRepeatingTask() {
                 mIsRunning = false;
                 handler.removeCallbacks(mStatusChecker);
             }

         };
    }
}
