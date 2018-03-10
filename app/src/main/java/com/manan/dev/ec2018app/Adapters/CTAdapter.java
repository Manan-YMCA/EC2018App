package com.manan.dev.ec2018app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.ec2018app.Models.postsModel;
import com.manan.dev.ec2018app.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;



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
        postsModel topic = postsList.get(position);
        holder.caption.setText(topic.title);
        holder.postTime.setText(Long.toString(topic.time));
        holder.caption.setText(topic.title);
        holder.clubName.setText(topic.clubName);
        holder.likes.setText(Integer.toString(topic.likes)+" likes");
        holder.comments.setText(Integer.toString(topic.comments.size())+" comments");

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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