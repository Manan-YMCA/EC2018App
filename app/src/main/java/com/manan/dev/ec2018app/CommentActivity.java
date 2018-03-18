package com.manan.dev.ec2018app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.manan.dev.ec2018app.Adapters.CommentAdapter;
import com.manan.dev.ec2018app.Models.Comment;
import com.manan.dev.ec2018app.Models.likesModel;
import com.manan.dev.ec2018app.Models.postsModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
        private List<Comment> commentList = new ArrayList<>();
        private RecyclerView recyclerView;
        private CommentAdapter mAdapter;
        private DatabaseReference mDatabase;
        private String postId;
        private String clubName;
        private ChildEventListener mListener;
        private ArrayList<Comment> allcomments;
    private ArrayList<postsModel> allposts;
    private postsModel post;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_comment);
            final EditText addComment = (EditText)findViewById(R.id.et_addcomment);
            TextView postButton = (TextView)findViewById(R.id.post_btn);
            postId = getIntent().getStringExtra("eventId");
            clubName = getIntent().getStringExtra("clubName");
            allcomments = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            android.app.ActionBar ab = getActionBar();
            mAdapter = new CommentAdapter(CommentActivity.this , commentList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mDatabase= FirebaseDatabase.getInstance().getReference().child("posts").child(clubName).child(postId).child("comments");

            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!addComment.getText().equals("")){

                        allcomments.add(new Comment(Profile.getCurrentProfile().getName(),addComment.getText().toString(), Calendar.getInstance().getTimeInMillis(),Profile.getCurrentProfile().getProfilePictureUri(500,500).toString()));
                        FirebaseDatabase.getInstance().getReference().child("posts").child(clubName).child(postId).child("comments").setValue(allcomments).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(CommentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(CommentActivity.this, "ho gya", Toast.LENGTH_SHORT).show();
                                    updateView();
                                }
                            }
                        });
                    }
                }
            });





        }

    @Override
    protected void onResume() {
        super.onResume();
         attachDatabase();

    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDatabase();
        allcomments.clear();
    }

    private void detachDatabase() {
        if(mListener!=null)
        {
            mDatabase.removeEventListener(mListener);
            mListener=null;
        }
    }

    private void attachDatabase() {
        if(mListener==null){
            mListener=new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    GenericTypeIndicator<ArrayList<Comment>> t = new GenericTypeIndicator<ArrayList<Comment>>(){};
                    ArrayList<Comment> commentList = dataSnapshot.getValue(t);
                    Toast.makeText(CommentActivity.this, commentList.get(0).getComment(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
            mDatabase.addChildEventListener(mListener);
            updateView();
        }
    }

    private void updateView() {
        mAdapter.notifyDataSetChanged();
    }
}
