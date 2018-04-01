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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.List;

import static java.util.Collections.sort;

public class CulmycaTimesActivity extends AppCompatActivity implements ConnectivityReciever.ConnectivityReceiverListener {

    DatabaseReference postReference;
    List<postsModel> allposts;
    RecyclerView recyclerView;
    ProgressDialog progressBar;
    LinearLayoutManager mLayoutManager;
    private CTAdapter mAdapter;
    SwipeRefreshLayout s;
    ImageView backButton;
    TextView noPostsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culmyca_times);

        allposts = new ArrayList<postsModel>();

        backButton = findViewById(R.id.cul_back_button);
        recyclerView = findViewById(R.id.ctc_recycler_view);
        noPostsTextView = findViewById(R.id.tv_no_posts);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading!");
//        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

        postReference = FirebaseDatabase.getInstance().getReference("posts");

        s = findViewById(R.id.swipe_refresh_layout);
        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()) {
                    noPostsTextView.setVisibility(View.GONE);
                    reload();
                } else {
                    noPostsTextView.setVisibility(View.VISIBLE);
                    progressBar.dismiss();
                    s.setRefreshing(false);
                }
            }
        });

        if (isNetworkAvailable()) {
            noPostsTextView.setVisibility(View.GONE);
            reload();
        } else {
            noPostsTextView.setVisibility(View.VISIBLE);
            MDToast.makeText(CulmycaTimesActivity.this, "Connect to internet!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
            progressBar.dismiss();
            s.setRefreshing(false);
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
        MyApplication.getInstance().setConnectivityListener(CulmycaTimesActivity.this);
    }

    public void reload() {
        postReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allposts = new ArrayList<postsModel>();

                for (DataSnapshot club : dataSnapshot.getChildren()) {
                    String clubName = club.getKey();
                    for (DataSnapshot posts : club.getChildren()) {

                        postsModel post = posts.getValue(postsModel.class);
                        post.postid = posts.getKey();


                        allposts.add(post);

                        Check();
                    }
                }
                mLayoutManager = new LinearLayoutManager(CulmycaTimesActivity.this);
                sort(allposts);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setHasFixedSize(true);

                mAdapter = new CTAdapter(getApplicationContext(), allposts);
                recyclerView.setAdapter(mAdapter);

                Check();

                s.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.dismiss();
                s.setRefreshing(false);
            }
        });
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
            reload();
        } else {
            Check();
            progressBar.dismiss();
            MDToast.makeText(CulmycaTimesActivity.this, "Connect to internet!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
            s.setRefreshing(false);
        }
    }

    void Check(){
        if(allposts.size() == 0){
            noPostsTextView.setVisibility(View.VISIBLE);
            progressBar.dismiss();
        }
        if (allposts.size() > 0) {
            noPostsTextView.setVisibility(View.GONE);
            progressBar.dismiss();
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


