package com.manan.dev.ec2018app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.manan.dev.ec2018app.Models.likesModel;
import com.manan.dev.ec2018app.Models.postsModel;
import com.manan.dev.ec2018app.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.manan.dev.ec2018app.CulmycaTimesActivity.dialog;


public class CTAdapter extends RecyclerView.Adapter<CTAdapter.MyViewHolder>{

    private List<postsModel> postsList;
    private Context context;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView clubName,caption,likes,comments,postTime;
        public ImageView clubIcon,like, comment,share;


        public MyViewHolder(View view) {
            super(view);
            clubName = (TextView) view.findViewById(R.id.ctc_clubname);
            caption = (TextView) view.findViewById(R.id.ctc_posttitle);
            likes = (TextView) view.findViewById(R.id.ctc_likescount);
            comments = (TextView) view.findViewById(R.id.ctc_commentscount);
            postTime = (TextView) view.findViewById(R.id.ctc_posttime);
            clubIcon = (ImageView) view.findViewById(R.id.ctc_clubicon);
            like = (ImageView) view.findViewById(R.id.ctc_likebtn);
            comment = (ImageView) view.findViewById(R.id.ctc_commentbtn);
            share = (ImageView) view.findViewById(R.id.ctc_sharebtn);
        }
    }


    public CTAdapter(Context context,List<postsModel> topicList) {
        this.postsList = topicList;
        this.context =context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.culmyca_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final postsModel topic = postsList.get(position);
        holder.caption.setText(topic.title);
        holder.postTime.setText(Long.toString(topic.time));
        holder.caption.setText(topic.title);
        holder.clubName.setText(topic.clubName);
        holder.likes.setText(Integer.toString(topic.likes)+" likes");
        holder.comments.setText(Integer.toString(topic.comments.size())+" comments");

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AccessToken token=AccessToken.getCurrentAccessToken();
                if(token==null) {
                    dialog.show();
                }
                else {

                    DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("likes").child(topic.clubName).child(topic.postid);

                    postRef.runTransaction(new Transaction.Handler() {

                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            postsModel p = mutableData.getValue(postsModel.class);
                            if (p == null) {
                                return Transaction.success(mutableData);
                            }

                            if (p.likefids.contains(new likesModel(Profile.getCurrentProfile().getId()))) {
                                // Unstar the post and remove self from stars
                                p.likes = p.likes - 1;
                                p.likefids.remove(new likesModel(Profile.getCurrentProfile().getId()));
                            } else {
                                // Star the post and add self to stars
                                p.likes = p.likes + 1;
                                p.likefids.add(new likesModel(Profile.getCurrentProfile().getId()));
                            }

                            mutableData.setValue(p);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b,
                                               DataSnapshot dataSnapshot) {
                            // Transaction completed
                        }
                    });
                }
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {

        return postsList.size();
    }
}