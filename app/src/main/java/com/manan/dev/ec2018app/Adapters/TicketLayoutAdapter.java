package com.manan.dev.ec2018app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manan.dev.ec2018app.CategoryEventDisplayActivity;
import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Models.CategoryItemModel;
import com.manan.dev.ec2018app.Models.EventDetails;
import com.manan.dev.ec2018app.Models.QRTicketModel;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Utilities.TicketsGenerator;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by nisha on 2/28/2018.
 */

public class TicketLayoutAdapter extends BaseAdapter {
    private ArrayList<QRTicketModel> itemsList;
    private Context mContext;
    private View v;


    public TicketLayoutAdapter(Context context, ArrayList<QRTicketModel> itemsList) {
        Log.d("Tickets", "view builder");
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        v = LayoutInflater.from(mContext).inflate(R.layout.qr_ticket_layout, null);

        QRTicketModel singleItem = itemsList.get(position);
        EventDetails currEvent = new EventDetails();
        DatabaseController mDatabaseController = new DatabaseController(mContext);

        currEvent = mDatabaseController.retreiveEventsByID(singleItem.getEventID());


        final ImageView itemImage = (ImageView) v.findViewById(R.id.ticket_qr_iv);
        TextView feeStatus = (TextView) v.findViewById(R.id.tv_event_fees_status);
        TextView eventDate = (TextView) v.findViewById(R.id.tv_event_date);
        TextView eventTime = (TextView) v.findViewById(R.id.tv_event_time);
        TextView eventFee = (TextView) v.findViewById(R.id.tv_event_fees);
        TextView eventName = (TextView) v.findViewById(R.id.tv_event_name);

        Log.d("Tickets", singleItem.getQRcode());
        feeStatus.setText(String.valueOf(singleItem.getPaymentStatus()));
        TicketsGenerator generate = new TicketsGenerator();
        Bitmap currTicket = generate.GenerateClick(v, itemImage, singleItem.getQRcode(), mContext);
        itemImage.setImageBitmap(currTicket);
        eventName.setText(currEvent.getmName());
        eventFee.setText("RS " + String.valueOf(currEvent.getmFees()));

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currEvent.getmStartTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        String formattedDate = sdf.format(cal.getTime());
        eventDate.setText(formattedDate);

        SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
        String formattedTime = sdf1.format(cal.getTime());
        eventTime.setText(formattedTime);
        Log.d("Tickets", "data setSuccessfully");

        return v;
    }


}
