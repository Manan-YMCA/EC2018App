package com.manan.dev.ec2018app.Notifications;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.ec2018app.Adapters.MyNotificationsAdapter;
import com.manan.dev.ec2018app.Models.NotificationModel;
import com.manan.dev.ec2018app.R;

import java.util.ArrayList;

public class MyNotificationsActivity extends AppCompatActivity {
    RecyclerView notifyRecyclerView;
    ArrayList<NotificationModel> allNotificationsArrayList, notificationDuplicateArrayList;
    MyNotificationsAdapter myNotificationsAdapter;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    String TAG = "Errorrrrrrrr!";
    ImageView backButton;
    ProgressDialog pd;
    ProgressDialog progress;
    TextView noNotifyTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notifications);

        backButton = findViewById(R.id.iv_back_button);
        noNotifyTV = findViewById(R.id.tv_no_notify);
        noNotifyTV.setVisibility(View.GONE);

        progress = new ProgressDialog(MyNotificationsActivity.this);
        progress.setTitle("Loading Notifications...");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.show();

        notifyRecyclerView = findViewById(R.id.rv_notifications);
        allNotificationsArrayList = new ArrayList<>();
        notificationDuplicateArrayList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyNotificationsActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        notifyRecyclerView.setLayoutManager(layoutManager);
        notifyRecyclerView.setHasFixedSize(true);

        myNotificationsAdapter = new MyNotificationsAdapter(this, allNotificationsArrayList);
        notifyRecyclerView.setAdapter(myNotificationsAdapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Notification");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (allNotificationsArrayList.size() == 0) {
            progress.dismiss();
//            Toast.makeText(this, "No Notifications!", Toast.LENGTH_SHORT).show();
            noNotifyTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        attachDatabaseListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        detatchDatabaseListener();
        allNotificationsArrayList.clear();
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
                    Log.e(TAG, "onChildAdded:" + dataSnapshot.getKey());
//                    Toast.makeText(MyNotificationsActivity.this, dataSnapshot.getValue().toString() , Toast.LENGTH_SHORT).show();
                    NotificationModel nm = dataSnapshot.getValue(NotificationModel.class);
//                    Toast.makeText(MyNotificationsActivity.this, nm.getTextHeading()  , Toast.LENGTH_SHORT).show();
                    updateList(nm);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.e(TAG, "onChildChanged:" + dataSnapshot.getKey());
                    try {
                        NotificationModel nm = dataSnapshot.getValue(NotificationModel.class);
                        //String nmString = dataSnapshot.getKey();
                        // updateList(nm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    NotificationModel nm = dataSnapshot.getValue(NotificationModel.class);
                    allNotificationsArrayList.remove(nm);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("The read failed: ", databaseError.getDetails());
                }
            };
            mDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void updateList(NotificationModel nm) {
        Log.e(TAG, "updateList: " + allNotificationsArrayList.size());
        allNotificationsArrayList.add(nm);

        if (allNotificationsArrayList.size() > 0) {
            progress.dismiss();
//            Toast.makeText(this, "There is something!", Toast.LENGTH_SHORT).show();
            noNotifyTV.setVisibility(View.GONE);
        }
        myNotificationsAdapter.notifyDataSetChanged();
    }
}
