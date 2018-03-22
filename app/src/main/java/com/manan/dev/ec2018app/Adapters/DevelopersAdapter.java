package com.manan.dev.ec2018app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.manan.dev.ec2018app.Models.DeveloperModel;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Utilities.CircleTransform;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by subham on 13/03/18.
 */


public class DevelopersAdapter extends RecyclerView.Adapter<DevelopersAdapter.ViewHolder> {

    private List<DeveloperModel> item;
    private Context context;

    public DevelopersAdapter(Context context, List<DeveloperModel> item) {
        this.item = item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.developer_display, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final DeveloperModel dev = item.get(position);

//      Picasso.with(context).load(dev.getPhotoUrl().toString()).into(holder.photo);
        holder.bar.setVisibility(View.VISIBLE);
        holder.reload.setVisibility(View.GONE);

        holder.reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bar.setVisibility(View.VISIBLE);
                holder.reload.setVisibility(View.GONE);
                Picasso.with(context).load(dev.getPhotoUrl()).transform(new CircleTransform()).into(holder.photo, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.bar.setVisibility(View.GONE);
                        holder.reload.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        Picasso.with(context).load(dev.getPhotoUrl()).transform(new CircleTransform()).into(holder.photo, new Callback() {
            @Override
            public void onSuccess() {
                holder.bar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.bar.setVisibility(View.GONE);
                holder.reload.setVisibility(View.VISIBLE);
            }
        });

        holder.name.setText(dev.getName());
        holder.more.setText(dev.getMore());

        if(dev.getGithubURL().equals("")){
            holder.github.setVisibility(View.GONE);
        }
        if(dev.getLinkedDUrl().equals("")){
            holder.linkedin.setVisibility(View.GONE);
        }

        holder.linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dev.getLinkedDUrl().toString()));
                context.startActivity(myIntent);
            }
        });

        holder.github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dev.getGithubURL().toString()));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo;
        public TextView name;
        public TextView more;
        public ImageView linkedin, github, reload;
        public ProgressBar bar;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.dev_photo);
            name = (TextView) itemView.findViewById(R.id.dev_name);
            linkedin = (ImageView) itemView.findViewById(R.id.dev_linkdin);
            more = (TextView) itemView.findViewById(R.id.dev_more);
            github = (ImageView) itemView.findViewById(R.id.dev_github);
            bar = (ProgressBar) itemView.findViewById(R.id.pb_dev_image);
            reload = (ImageView) itemView.findViewById(R.id.refresh_button);
        }
    }
}