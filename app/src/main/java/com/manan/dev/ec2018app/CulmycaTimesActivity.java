package com.manan.dev.ec2018app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.manan.dev.ec2018app.Fragments.FragmentFbLogin;
import com.manan.dev.ec2018app.Models.approval;
import com.manan.dev.ec2018app.Models.commentsModel;
import com.manan.dev.ec2018app.Models.likesModel;
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
                    startActivity(new Intent(CulmycaTimesActivity.this, LoginActivity.class));
                }
                else{
                    FragmentManager fm = getFragmentManager();
                    FragmentFbLogin fbLogin = new FragmentFbLogin();
                    fbLogin.show(fm, "fbLoginFragment");
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
                for(DataSnapshot club:dataSnapshot.getChildren()){
                    String clubName=club.getKey();
                    for(DataSnapshot posts:club.getChildren()){

                        postsModel post=posts.getValue(postsModel.class);
                        post.clubName=clubName;
                        post.postid=posts.getKey();
                        List<commentsModel> allcomments=new ArrayList<commentsModel>();
                        for(DataSnapshot comments: posts.child("comments").getChildren()) {
                            commentsModel comment = comments.getValue(commentsModel.class);
                            allcomments.add(comment);
                        }

                        post.comments=allcomments;

                        List<likesModel> alllikes=new ArrayList<likesModel>();
                        for(DataSnapshot mlikes: posts.child("likefids").getChildren()) {
                            likesModel l = mlikes.getValue(likesModel.class);
                            alllikes.add(l);
                        }

                        post.likefids=alllikes;
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
