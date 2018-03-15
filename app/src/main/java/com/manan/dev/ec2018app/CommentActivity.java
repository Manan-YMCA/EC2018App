package com.manan.dev.ec2018app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.manan.dev.ec2018app.Adapters.CommentAdapter;
import com.manan.dev.ec2018app.Models.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
        private List<Comment> commentList = new ArrayList<>();
        private RecyclerView recyclerView;
        private CommentAdapter mAdapter;
        private DatabaseReference mDatabase;
        private String postId;
        private String clubName;
        private ChildEventListener mListener;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_comment);
            final EditText addComment = (EditText)findViewById(R.id.et_addcomment);
            TextView postButton = (TextView)findViewById(R.id.post_btn);
            postId = getIntent().getStringExtra("eventId");
            clubName = getIntent().getStringExtra("clubName");
            SpannableString s = new SpannableString("Comments");
            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Black)), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            getSupportActionBar().setTitle(s);
            
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            android.app.ActionBar ab = getActionBar();
            mAdapter = new CommentAdapter(commentList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
       //     commentList.add(new Comment("Jayati Bhayana","Cool!",12345678,R.drawable.vector_rules));
//            mDatabase= FirebaseDatabase.getInstance().getReference().child("posts").child(clubName).child(postId);

            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!addComment.getText().equals("")){

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
        }
    }
}
