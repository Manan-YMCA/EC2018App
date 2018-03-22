package com.manan.dev.ec2018app.Notifications;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
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
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import static java.util.Collections.sort;

public class MyNotificationsActivity extends AppCompatActivity {
    RecyclerView notifyRecyclerView;
    ArrayList<NotificationModel> allNotificationsArrayList;
    MyNotificationsAdapter myNotificationsAdapter;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    String TAG = "Errorrrrrrrr!";
    ImageView backButton;
    ProgressDialog pd;
    ProgressDialog progress;
    TextView noNotifyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notifications);

        backButton = findViewById(R.id.iv_back_button);
        progress = new ProgressDialog(MyNotificationsActivity.this);
        progress.setTitle("Loading...");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.show();

        notifyRecyclerView = findViewById(R.id.rv_notifications);
        allNotificationsArrayList = new ArrayList<>();
        noNotifyTextView = findViewById(R.id.tv_no_notify_un);

        long lTime = 1521523868202L;
        allNotificationsArrayList.add(new NotificationModel("Attention!", "Welcome to Culmyca 18.", lTime));

//        LinearLayoutManager layoutManager = new LinearLayoutManager(MyNotificationsActivity.this);\
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManagerWrapper(MyNotificationsActivity.this, LinearLayoutManager.VERTICAL, false);
//        notifyRecyclerView.setLayoutManager(layoutManager);

        notifyRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(MyNotificationsActivity.this, LinearLayoutManager.VERTICAL, false));
        notifyRecyclerView.setHasFixedSize(true);

        notifyRecyclerView.getRecycledViewPool().clear();
        myNotificationsAdapter = new MyNotificationsAdapter(this, allNotificationsArrayList);
        notifyRecyclerView.setAdapter(myNotificationsAdapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Notification");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (!isNetworkAvailable()) {
            progress.dismiss();
            MDToast.makeText(MyNotificationsActivity.this, "Connect to internet!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
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
                    NotificationModel nm = dataSnapshot.getValue(NotificationModel.class);
                    updateList(nm);
                    progress.dismiss();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.e(TAG, "onChildChanged:" + dataSnapshot.getKey());
                    try {
                        NotificationModel nm = dataSnapshot.getValue(NotificationModel.class);
                        updateList(nm);
                        progress.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                        progress.dismiss();
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    NotificationModel nm = dataSnapshot.getValue(NotificationModel.class);
                    allNotificationsArrayList.remove(nm);
                    progress.dismiss();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("The read failed: ", databaseError.getDetails());
                    progress.dismiss();
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
        }
        sort(allNotificationsArrayList);
        notifyRecyclerView.getRecycledViewPool().clear();
        myNotificationsAdapter.notifyDataSetChanged();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class LinearLayoutManagerWrapper extends LinearLayoutManager {

        public LinearLayoutManagerWrapper(Context context) {
            super(context);
        }

        public LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public LinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }

    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context, int horizontal, boolean b) {
            super(context);
        }

        //... constructor
        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("probe", "meet a IOOBE in RecyclerView");
            }
        }
    }

}
