package com.manan.dev.ec2018app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manan.dev.ec2018app.Adapters.CTAdapter;
import com.manan.dev.ec2018app.Models.Comment;
import com.manan.dev.ec2018app.Models.likesModel;
import com.manan.dev.ec2018app.Models.postsModel;

import java.util.ArrayList;
import java.util.List;

public class CulmycaTimesActivity extends AppCompatActivity {


    ValueEventListener getPosts;
    DatabaseReference postReference;
    List<postsModel> allposts;
    RecyclerView recyclerView;
    ProgressDialog progressBar;
    RecyclerView.LayoutManager mLayoutManager;
    private CTAdapter mAdapter;
    SwipeRefreshLayout s;

    public static AlertDialog.Builder builder;
    public static AlertDialog dialog;
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


        s=findViewById(R.id.swipe_refresh_layout);

        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                   @Override
                                   public void onRefresh() {
                                       reload();
                                   }
                               });

            builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Required!");
        builder.setMessage("To like or share, you must login with facebook");
        builder.setPositiveButton("Continue", new Dialog.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), Context.MODE_PRIVATE);
                final String phoneNumber = preferences.getString("Phone", null);
                if (phoneNumber == null) {
                    finish();
                    Intent in=new Intent(CulmycaTimesActivity.this, LoginActivity.class);
                    in.putExtra("parent","ct");
                    startActivity(in);
                }
                else{
                    /*FragmentManager fm = getFragmentManager();
                    FragmentFbLogin fbLogin = new FragmentFbLogin();
                    fbLogin.show(fm, "fbLoginFragment");*/
                    finish();
                    Intent in=new Intent(CulmycaTimesActivity.this, ProfileActivity.class);
                    in.putExtra("parent","ct");
                    startActivity(in);
                }
            }
        });
        builder.setNegativeButton("Cancel", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog = builder.create();



        postReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allposts=new ArrayList<postsModel>();
                for(DataSnapshot club : dataSnapshot.getChildren()){
                    String clubName = club.getKey();
                    Log.e("Club Name Key", "onDataChange: " + clubName );

                    for(DataSnapshot posts:club.getChildren()){

                        postsModel post = posts.getValue(postsModel.class);

                        post.clubName = clubName;
                        post.postid=posts.getKey();

                        ArrayList<Comment> allcomments=new ArrayList<Comment>();
                        for(DataSnapshot comments: posts.child("comments").getChildren()) {
                            Comment comment = comments.getValue(Comment.class);
                            allcomments.add(comment);
                        }

                        post.comments=allcomments;

                        ArrayList<likesModel> alllikes=new ArrayList<likesModel>();
                        for(DataSnapshot mlikes: posts.child("likefids").getChildren()) {
                            likesModel l = mlikes.getValue(likesModel.class);
                            alllikes.add(l);
                        }

                        post.likefids=alllikes;
                        allposts.add(post);
                    }
                }
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
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

    @Override
    public void onBackPressed() {
        mLayoutManager.smoothScrollToPosition(recyclerView,null,0);
        if(!recyclerView.canScrollVertically(-1)){
            super.onBackPressed();
        }
    }
    public void reload(){

        postReference = FirebaseDatabase.getInstance().getReference("posts");
        postReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allposts=new ArrayList<postsModel>();
                for(DataSnapshot club:dataSnapshot.getChildren()){
                    String clubName=club.getKey();
                    for(DataSnapshot posts:club.getChildren()){

                        postsModel post=posts.getValue(postsModel.class);
                        post.clubName=clubName;
                        post.postid=posts.getKey();
                        ArrayList<Comment> allcomments=new ArrayList<Comment>();
                        for(DataSnapshot comments: posts.child("comments").getChildren()) {
                            Comment comment = comments.getValue(Comment.class);
                            allcomments.add(comment);
                        }

                        post.comments=allcomments;

                        ArrayList<likesModel> alllikes=new ArrayList<likesModel>();
                        for(DataSnapshot mlikes: posts.child("likefids").getChildren()) {
                            likesModel l = mlikes.getValue(likesModel.class);
                            alllikes.add(l);
                        }

                        post.likefids=alllikes;
                        allposts.add(post);
                    }
                }
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new CTAdapter(getApplicationContext(), allposts);
                recyclerView.setAdapter(mAdapter);
                s.setRefreshing(false);
                progressBar.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
