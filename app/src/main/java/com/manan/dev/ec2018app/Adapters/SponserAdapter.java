package com.manan.dev.ec2018app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manan.dev.ec2018app.Models.Sponsers;
import com.manan.dev.ec2018app.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class SponserAdapter extends RecyclerView.Adapter<SponserAdapter.MyViewHolder> {

    private List<Sponsers> sponsersList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sname, title;
        public ImageView sponserPic;
        public CardView cardview;

        public MyViewHolder(View view) {
            super(view);
            sname = (TextView) view.findViewById(R.id.tv1);
            title = (TextView) view.findViewById(R.id.tv2);
            sponserPic = (ImageView) view.findViewById(R.id.card_img);
            cardview = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public SponserAdapter(Context context, List<Sponsers> sponsersList) {
        this.sponsersList = sponsersList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Sponsers sponsers = sponsersList.get(position);
        holder.sname.setText(sponsers.getSname());
        Picasso.with(mContext).load(sponsers.getImageUrl()).into(holder.sponserPic);
        holder.title.setText(sponsers.getTitle());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(sponsers.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sponsersList.size();
    }
}
