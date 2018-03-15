package com.manan.dev.ec2018app.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manan.dev.ec2018app.Models.Comment;
import com.manan.dev.ec2018app.R;


import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<Comment> commentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView uname , comment;
        public ImageView profilePic;

        public MyViewHolder(View view) {
            super(view);
            uname = (TextView) view.findViewById(R.id.username);
            comment = (TextView) view.findViewById(R.id.comment_txt);
            profilePic = (ImageView) view.findViewById(R.id.profile_pic);
        }
    }


    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.uname.setText(comment.getUname());
        holder.comment.setText(comment.getComment());
      //  Picasso.with(c)
        //holder.profilePic.setImageResource(comment.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
