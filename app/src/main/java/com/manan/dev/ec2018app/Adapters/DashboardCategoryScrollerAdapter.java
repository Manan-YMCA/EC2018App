package com.manan.dev.ec2018app.Adapters;

/**
 * Created by shubhamsharma on 04/02/18.
 */

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manan.dev.ec2018app.Models.CategoryItemModel;
import com.manan.dev.ec2018app.R;



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

        CategoryItemModel singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getName());
        Drawable drawable = new BitmapDrawable(mContext.getResources(),singleItem.getImage());

        holder.itemImage.setImageDrawable(drawable);
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


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }

}