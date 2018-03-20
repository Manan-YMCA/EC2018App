package com.manan.dev.ec2018app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Fragments.QRCodeActivity;
import com.manan.dev.ec2018app.Models.EventDetails;
import com.manan.dev.ec2018app.Models.QRTicketModel;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Utilities.TicketsGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by nisha on 2/28/2018.
 */

public class TicketLayoutAdapter extends RecyclerView.Adapter<TicketLayoutAdapter.MyViewHolder> {
    private ArrayList<QRTicketModel> itemsList;
    private Context mContext;


    public TicketLayoutAdapter(Context context, ArrayList<QRTicketModel> itemsList) {
        Log.d("Tickets", "view builder");
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qr_ticket_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final QRTicketModel singleItem = itemsList.get(position);
        EventDetails currEvent = new EventDetails();
        DatabaseController mDatabaseController = new DatabaseController(mContext);

        currEvent = mDatabaseController.retreiveEventsByID(singleItem.getEventID());


        Log.d("Tickets", singleItem.getQRcode());
        holder.feeStatus.setText(String.valueOf(singleItem.getPaymentStatus()));
        TicketsGenerator generate = new TicketsGenerator();
        Bitmap currTicket = generate.GenerateClick(singleItem.getQRcode(), mContext,(int) mContext.getResources().getDimension(R.dimen.onefifty),(int) mContext.getResources().getDimension(R.dimen.onefifty), 80, 80);
        holder.itemImage.setImageBitmap(currTicket);
        holder.eventName.setText(currEvent.getmName());
        holder.eventFee.setText("RS " + String.valueOf(currEvent.getmFees()));

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currEvent.getmStartTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        String formattedDate = sdf.format(cal.getTime());
        holder.eventDate.setText(formattedDate);

        SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
        String formattedTime = sdf1.format(cal.getTime());
        holder.eventTime.setText(formattedTime);
        Log.d("Tickets", "data setSuccessfully");

        final EventDetails finalCurrEvent = currEvent;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager fm = ((Activity) mContext).getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("qrcodestring", singleItem.getQRcode());
                bundle.putString("eventid", finalCurrEvent.getmEventId());
                bundle.putInt("activity", 0);
                bundle.putInt("paymentStatus", singleItem.getPaymentStatus());
                bundle.putInt("arrivalStatus", singleItem.getArrivalStatus());
// set Fragmentclass Arguments
                QRCodeActivity fragobj = new QRCodeActivity();
                fragobj.setArguments(bundle);
                fragobj.show(fm,"drff");

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView itemImage;
        public TextView feeStatus, eventDate, eventTime, eventFee, eventName;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemImage = (ImageView) itemView.findViewById(R.id.ticket_qr_iv);
            feeStatus = (TextView) itemView.findViewById(R.id.tv_event_fees_status);
            eventDate = (TextView) itemView.findViewById(R.id.tv_event_date);
            eventTime = (TextView) itemView.findViewById(R.id.tv_event_time);
            eventFee = (TextView) itemView.findViewById(R.id.tv_event_fees);
            eventName = (TextView) itemView.findViewById(R.id.tv_event_name);
            cardView=(CardView)itemView.findViewById(R.id.cv_qr_ticket);

        }
    }
}
