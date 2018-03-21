package com.manan.dev.ec2018app.NavMenuViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manan.dev.ec2018app.Adapters.CTAdapter;
import com.manan.dev.ec2018app.Models.postsModel;
import com.manan.dev.ec2018app.R;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

public class CulmycaTimesActivity extends AppCompatActivity {

    DatabaseReference postReference;
    List<postsModel> allposts;
    RecyclerView recyclerView;
    ProgressDialog progressBar;
    LinearLayoutManager mLayoutManager;
    private CTAdapter mAdapter;
    SwipeRefreshLayout s;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culmyca_times);

        allposts = new ArrayList<postsModel>();

        String url = "https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/final_image.jpeg?alt=media&token=e7685e47-6762-42d9-bc12-8b8484e0fe38";
        allposts.add(new postsModel("Culmyca Times to keep yourself updated with all the insides.", url, "manan", "-L7zAP6vrQM_7j914Eyb", 1521478378210L));

        backButton = findViewById(R.id.cul_back_button);
        recyclerView = findViewById(R.id.ctc_recycler_view);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading!");
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

        postReference = FirebaseDatabase.getInstance().getReference("posts");

        s = findViewById(R.id.swipe_refresh_layout);
        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()) {
                    reload();
                } else {
                    progressBar.dismiss();
                    MDToast.makeText(CulmycaTimesActivity.this, "Connect to internet!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
                }
            }
        });

        if (isNetworkAvailable()) {
            reload();
        } else {
            MDToast.makeText(CulmycaTimesActivity.this, "Connect to internet!", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
            progressBar.dismiss();
        }
    }

//    @Override
//    public void onRefresh() {
//        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        }, 2000);
//    }

//    @Override
//    public void onBackPressed() {
//        mLayoutManager.smoothScrollToPosition(recyclerView, null, 0);
//        if (!recyclerView.canScrollVertically(-1)) {
//            super.onBackPressed();
//        }
//    }

    public void reload() {
        postReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allposts = new ArrayList<postsModel>();

                for (DataSnapshot club : dataSnapshot.getChildren()) {
                    String clubName = club.getKey();
                    for (DataSnapshot posts : club.getChildren()) {
                        Log.e("posts", posts.toString());

                        postsModel post = posts.getValue(postsModel.class);
                        post.postid = posts.getKey();

                        Log.e("TAG", "onDataChange: postiddddddddddddddddddd" + post.getPostid());

                        allposts.add(post);
                        if (allposts.size() > 0) {
                            progressBar.dismiss();
                        }
                    }
                }
                mLayoutManager = new LinearLayoutManager(CulmycaTimesActivity.this);

                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setHasFixedSize(true);

                mAdapter = new CTAdapter(getApplicationContext(), allposts);
                recyclerView.setAdapter(mAdapter);
                progressBar.dismiss();

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
}
