package com.manan.dev.ec2018app.NavMenuViews;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CulmycaTimesActivity extends AppCompatActivity {

    DatabaseReference postReference;
    List<postsModel> allposts;
    RecyclerView recyclerView;
    ProgressDialog progressBar;
    RecyclerView.LayoutManager mLayoutManager;
    private CTAdapter mAdapter;
    SwipeRefreshLayout s;
    ImageView backButton;

    public static AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culmyca_times);

        allposts=new ArrayList<postsModel>();

        backButton = findViewById(R.id.cul_back_button);
        recyclerView=findViewById(R.id.ctc_recycler_view);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading!");
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

        postReference = FirebaseDatabase.getInstance().getReference("posts");

        s=findViewById(R.id.swipe_refresh_layout);
        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                   @Override
                                   public void onRefresh() {
                                       reload();
                                   }
                               });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reload();
    }

    @Override
    public void onBackPressed() {
        mLayoutManager.smoothScrollToPosition(recyclerView,null,0);
        if(!recyclerView.canScrollVertically(-1)){
            super.onBackPressed();
        }
    }
    public void reload(){

        postReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allposts=new ArrayList<postsModel>();
                for(DataSnapshot club:dataSnapshot.getChildren()){
                    String clubName=club.getKey();
                    for(DataSnapshot posts:club.getChildren()){
                        Log.d("posts", posts.toString());
                        postsModel post=posts.getValue(postsModel.class);
                        post.postid=posts.getKey();
                        allposts.add(post);
                    }
                }
                Collections.sort(allposts,new Comparator<postsModel>(){
                    @Override
                    public int compare(postsModel t1, postsModel t2) {
                        long cmp= t1.time-t2.time;
                        return (int)cmp;
                    }
                });
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new CTAdapter(getApplicationContext(), allposts);
                recyclerView.setAdapter(mAdapter);
                progressBar.dismiss();
                s.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                s.setRefreshing(false);
            }
        });

    }
}
