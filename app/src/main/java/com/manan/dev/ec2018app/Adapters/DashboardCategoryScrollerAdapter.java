package com.manan.dev.ec2018app.Adapters;

/**
 * Created by shubhamsharma on 04/02/18.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manan.dev.ec2018app.CategoryEventDisplayActivity;
import com.manan.dev.ec2018app.Models.CategoryItemModel;
import com.manan.dev.ec2018app.R;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class DashboardCategoryScrollerAdapter extends RecyclerView.Adapter<DashboardCategoryScrollerAdapter.SingleItemRowHolder> {

    private ArrayList<CategoryItemModel> itemsList;
    private Context mContext;

    public DashboardCategoryScrollerAdapter(Context context, ArrayList<CategoryItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_scroller_item, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {


        final CategoryItemModel singleItem = itemsList.get(i);

        if (singleItem.getDisplayName().length() <= 12) {
            String displayName = singleItem.getDisplayName();
            holder.tvTitle.setText(displayName);
        } else if (singleItem.getDisplayName().length() > 12) {
            String displayName = singleItem.getDisplayName().substring(0, 9);
            displayName += "..";
            holder.tvTitle.setText(displayName);
        } else
            holder.tvTitle.setText(singleItem.getDisplayName());
        Drawable drawable = new BitmapDrawable(mContext.getResources(), singleItem.getImage());

        holder.itemImage.setImageDrawable(drawable);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                singleItem.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                mContext.startActivity(new Intent(mContext, CategoryEventDisplayActivity.class)
                        .putExtra("clubname", singleItem.getClubName())
                        .putExtra("clubPhoto", byteArray)
                        .putExtra("clubdisplay", singleItem.getDisplayName()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);

        }

    }

}