package com.manan.dev.ec2018app.NavMenuViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PointF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manan.dev.ec2018app.Adapters.CTAdapter;
import com.manan.dev.ec2018app.Models.postsModel;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Utilities.ConnectivityReciever;
import com.manan.dev.ec2018app.Utilities.MyApplication;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.sort;

public class CulmycaTimesActivity extends AppCompatActivity implements ConnectivityReciever.ConnectivityReceiverListener {

    DatabaseReference postReference;
    List<postsModel> allposts;
    HashMap<String, ArrayList<postsModel>> postMaps;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    private CTAdapter mAdapter;
    ImageView backButton;
    TextView noPostsTextView;
    private ChildEventListener mChildEventListener;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culmyca_times);

        allposts = new ArrayList<postsModel>();
        postMaps = new HashMap<>();

        backButton = findViewById(R.id.cul_back_button);
        recyclerView = findViewById(R.id.ctc_recycler_view);
        noPostsTextView = findViewById(R.id.tv_no_posts);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // = new ProgressDialog(this);
        //.setMessage("Loading!");
//        //.setCancelable(false);
        //.setCanceledOnTouchOutside(false);
        //.show();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading!");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        mLayoutManager = new LinearLayoutManager(CulmycaTimesActivity.this);
        sort(allposts);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new CTAdapter(getApplicationContext(), allposts);
        recyclerView.setAdapter(mAdapter);

        Check();


        postReference = FirebaseDatabase.getInstance().getReference("posts");


        if (isNetworkAvailable()) {
            //noPostsTextView.setVisibility(View.GONE);
            //reload();
        } else {
            ///noPostsTextView.setVisibility(View.VISIBLE);
            MDToast.makeText(CulmycaTimesActivity.this, "Connect to internet!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
            //.dismiss();
            //s.setRefreshing(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (allposts.size() > 0) {
            noPostsTextView.setVisibility(View.GONE);
            if (!recyclerView.canScrollVertically(-1)) {
                super.onBackPressed();
            }
            recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(CulmycaTimesActivity.this));
            recyclerView.smoothScrollToPosition(0);
        } else {
            noPostsTextView.setVisibility(View.GONE);
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseListener();
        MyApplication.getInstance().setConnectivityListener(CulmycaTimesActivity.this);
    }

    private void attachDatabaseListener() {
        if(mChildEventListener == null){
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //Log.d("yatin", dataSnapshot.toString());
                    try {
                        String clubName;
                        clubName = dataSnapshot.getKey();
                        if(!postMaps.containsKey(dataSnapshot.getKey())){
                            postMaps.put(dataSnapshot.getKey(), new ArrayList<postsModel>());
                        }
                        postMaps.get(clubName).clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            //Log.d("yatin", data.toString());
                            postsModel post = data.getValue(postsModel.class);
                            assert post != null;
                            post.setPostid(data.getKey());
                            postMaps.get(clubName).add(post);
                        }
                        allposts.clear();
                        progressDialog.dismiss();
                        for (Map.Entry<String, ArrayList<postsModel>> entry : postMaps.entrySet()) {
                            ArrayList<postsModel> posts = entry.getValue();
                            allposts.addAll(posts);
                        }
                        sort(allposts);
                        updateUi();
                        Log.d("yatin", String.valueOf(allposts.size()));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    try {
                        String clubName;
                        clubName = dataSnapshot.getKey();
                        if(!postMaps.containsKey(dataSnapshot.getKey())){
                            postMaps.put(dataSnapshot.getKey(), new ArrayList<postsModel>());
                        }
                        postMaps.get(clubName).clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            //Log.d("yatin", data.toString());
                            postsModel post = data.getValue(postsModel.class);
                            assert post != null;
                            post.setPostid(data.getKey());
                            postMaps.get(clubName).add(post);
                        }
                        allposts.clear();
                        progressDialog.dismiss();
                        for (Map.Entry<String, ArrayList<postsModel>> entry : postMaps.entrySet()) {
                            ArrayList<postsModel> posts = entry.getValue();
                            allposts.addAll(posts);
                        }
                        updateUi();
                        Log.d("yatin", String.valueOf(allposts.size()));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            postReference.addChildEventListener(mChildEventListener);
        }
    }

    private void updateUi() {
        noPostsTextView.setVisibility(View.GONE);
        Log.e("yatin", String.valueOf(allposts.size()));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        allposts.clear();
        postMaps.clear();
        detatchDatabaseListener();
    }

    private void detatchDatabaseListener() {
        if(mChildEventListener != null){
            postReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showNetError(isConnected);
    }

    private void showNetError(boolean isConnected) {
        if (isConnected) {
            Check();
            //reload();
        } else {
            Check();
            //.dismiss();
            MDToast.makeText(CulmycaTimesActivity.this, "Connect to internet!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
        }
    }

    void Check(){
        if(allposts.size() == 0){
            noPostsTextView.setVisibility(View.VISIBLE);
            //.dismiss();
        }
        if (allposts.size() > 0) {
            noPostsTextView.setVisibility(View.GONE);
            //.dismiss();
        }
    }

    public class LinearLayoutManagerWithSmoothScroller extends LinearLayoutManager {

        public LinearLayoutManagerWithSmoothScroller(Context context) {
            super(context, VERTICAL, false);
        }

        public LinearLayoutManagerWithSmoothScroller(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                           int position) {
            RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext());
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }

        private class TopSnappedSmoothScroller extends LinearSmoothScroller {
            public TopSnappedSmoothScroller(Context context) {
                super(context);

            }

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return LinearLayoutManagerWithSmoothScroller.this
                        .computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected int getVerticalSnapPreference() {
                return SNAP_TO_START;
            }
        }
    }
}


