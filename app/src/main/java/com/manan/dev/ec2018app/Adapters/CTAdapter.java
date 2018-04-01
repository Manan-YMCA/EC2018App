package com.manan.dev.ec2018app.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.ProfileTracker;
import com.manan.dev.ec2018app.Models.postsModel;
import com.manan.dev.ec2018app.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CTAdapter extends RecyclerView.Adapter<CTAdapter.MyViewHolder> {

    private List<postsModel> postsList;
    private Context context;
    private postsModel topic;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView clubName, caption, postTime;
        public ImageView clubIcon, postImage;
        public ProgressBar bar;


        public MyViewHolder(View view) {
            super(view);
            clubName = (TextView) view.findViewById(R.id.ctc_clubname);
            caption = (TextView) view.findViewById(R.id.ctc_posttitle);
            postTime = (TextView) view.findViewById(R.id.ctc_posttime);
            clubIcon = (ImageView) view.findViewById(R.id.ctc_clubicon);
            postImage = (ImageView) view.findViewById(R.id.ctc_postimage);
            bar = (ProgressBar) view.findViewById(R.id.pb_image);
        }
    }


    public CTAdapter(Context context, List<postsModel> topicList) {
        this.postsList = topicList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.culmyca_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        topic = postsList.get(position);
        holder.caption.setText(topic.title);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(topic.time);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yy", Locale.ENGLISH);
        String formattedDate = sdf.format(cal.getTime());

        SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
        String formattedTime = sdf1.format(cal.getTime());

        holder.postTime.setText(formattedDate + " " + formattedTime);

        holder.caption.setText(topic.title);
        holder.clubName.setText(topic.clubName.toUpperCase());
        holder.bar.setVisibility(View.VISIBLE);



        Picasso.with(context).load(topic.getphotoid()).into(holder.postImage, new Callback() {
            @Override
            public void onSuccess() {
                holder.bar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }
}