package com.manan.dev.ec2018app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manan.dev.ec2018app.Models.NotificationModel;
import com.manan.dev.ec2018app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Shubham on 3/18/2018.
 */

public class MyNotificationsAdapter extends RecyclerView.Adapter<MyNotificationsAdapter.MyViewHolder> {
    Context context;
    ArrayList<NotificationModel> notificationModelArrayList = new ArrayList<>();

    public MyNotificationsAdapter(Context context, ArrayList<NotificationModel> notificationModelArrayList) {
        this.context = context;
        this.notificationModelArrayList = notificationModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater l = LayoutInflater.from(context);
        View v = l.inflate(R.layout.single_my_notifications_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotificationModel nm = notificationModelArrayList.get(position);

        holder.notificationHeadingTextView.setText(nm.getTextHeading());
        holder.notificationDescriptionTextView.setText(nm.getText());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(nm.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yy", Locale.ENGLISH);
        String formattedDate = sdf.format(cal.getTime());

        SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
        String formattedTime = sdf1.format(cal.getTime());

        holder.notificationTimeTextView.setText(formattedTime);
        holder.notificationDateTextView.setText(formattedDate);

        Log.e("TAG", "onBindViewHolder: " + nm.getTime());
    }

    @Override
    public int getItemCount() {
        return notificationModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView notificationHeadingTextView, notificationDescriptionTextView, notificationTimeTextView, notificationDateTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            notificationHeadingTextView = itemView.findViewById(R.id.tv_notification_heading);
            notificationDescriptionTextView = itemView.findViewById(R.id.tv_notification_description);
            notificationDateTextView = itemView.findViewById(R.id.tv_notification_date);
            notificationTimeTextView = itemView.findViewById(R.id.tv_notification_time);
        }
    }
}
