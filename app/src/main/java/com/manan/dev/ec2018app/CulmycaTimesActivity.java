package com.manan.dev.ec2018app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manan.dev.ec2018app.Adapters.CTAdapter;
import com.manan.dev.ec2018app.Models.approval;
import com.manan.dev.ec2018app.Models.commentsModel;
import com.manan.dev.ec2018app.Models.postsModel;
import com.manan.dev.ec2018app.R;

import java.util.ArrayList;
import java.util.List;

public class CulmycaTimesActivity extends AppCompatActivity {


    ValueEventListener getPosts;
    DatabaseReference postReference;
    List<postsModel> allposts;
    RecyclerView recyclerView;
    ProgressDialog progressBar;
    private CTAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culmyca_times);
        allposts=new ArrayList<postsModel>();
        recyclerView=findViewById(R.id.ctc_recycler_view);
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading!");
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
        postReference = FirebaseDatabase.getInstance().getReference("posts");
        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot club:dataSnapshot.getChildren()){
                    String clubName=club.getKey();
                    Log.v("hey",clubName);
                    for(DataSnapshot posts:club.getChildren()){
                        postsModel post=posts.getValue(postsModel.class);
                        post.clubName=clubName;
                        List<commentsModel> allcomments=new ArrayList<commentsModel>();
                        for(DataSnapshot comments: posts.child("comments").getChildren()) {
                            Log.v("hey",comments.toString());
                            commentsModel comment = comments.getValue(commentsModel.class);
                            Log.v("hey",comment.username);
                            allcomments.add(comment);
                        }
                        post.comments=allcomments;
                        Log.v("hey",allcomments.toString());
                        allposts.add(post);
                    }
                }
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new CTAdapter(getApplicationContext(), allposts);
                recyclerView.setAdapter(mAdapter);
                progressBar.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
