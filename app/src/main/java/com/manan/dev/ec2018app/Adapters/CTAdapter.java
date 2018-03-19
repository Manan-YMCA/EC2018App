package com.manan.dev.ec2018app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.manan.dev.ec2018app.Models.postsModel;
import com.manan.dev.ec2018app.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.manan.dev.ec2018app.CulmycaTimesActivity.dialog;


public class CTAdapter extends RecyclerView.Adapter<CTAdapter.MyViewHolder>{

    private List<postsModel> postsList;
    private Context context;
    private postsModel topic;
    int liked=0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView clubName,caption,postTime;
        public ImageView clubIcon,postImage;


        public MyViewHolder(View view) {
            super(view);
            clubName = (TextView) view.findViewById(R.id.ctc_clubname);
            caption = (TextView) view.findViewById(R.id.ctc_posttitle);
            postTime = (TextView) view.findViewById(R.id.ctc_posttime);
            clubIcon = (ImageView) view.findViewById(R.id.ctc_clubicon);
            postImage=(ImageView)view.findViewById(R.id.ctc_postimage);
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
    public void onBindViewHolder(final MyViewHolder holder,int position) {
        topic = postsList.get(position);
        holder.caption.setText(topic.title);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(topic.time);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yy", Locale.ENGLISH);
        String formattedDate = sdf.format(cal.getTime());

        SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
        String formattedTime = sdf1.format(cal.getTime());

        holder.postTime.setText(formattedDate+" "+ formattedTime);

        holder.caption.setText(topic.title);
        Log.d("heyyy",topic.title);
        holder.clubName.setText(topic.clubName);

        Target mTarget;
        mTarget = new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                holder.postImage.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.with(context).load(topic.photoURL).into(mTarget);
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }
}