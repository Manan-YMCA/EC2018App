package com.manan.dev.ec2018app.Adapters;

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

import com.manan.dev.ec2018app.CategoryEventDisplayActivity;
import com.manan.dev.ec2018app.Models.CategoryItemModel;
import com.manan.dev.ec2018app.Models.QRTicketModel;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Utilities.TicketsGenerator;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by nisha on 2/28/2018.
 */

public class TicketLayoutAdapter extends RecyclerView.Adapter<TicketLayoutAdapter.SingleItemRowHolder> {
    private ArrayList<QRTicketModel> itemsList;
    private Context mContext;
    protected TextView qrCodeText;
    View v;

    protected ImageView itemImage;
    protected TextView feeStatus,eventFee,eventDate,eventTime,eventName;

    public TicketLayoutAdapter(Context context, ArrayList<QRTicketModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public TicketLayoutAdapter.SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
         v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qr_ticket_layout, null);
        TicketLayoutAdapter.SingleItemRowHolder mh = new TicketLayoutAdapter.SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(TicketLayoutAdapter.SingleItemRowHolder holder, int i) {

        final QRTicketModel singleItem = itemsList.get(i);

        holder.qrCodeText.setText(singleItem.getQRcode());
        holder.feeStatus.setText(singleItem.getPaymentStatus());
        TicketsGenerator generate=new TicketsGenerator();
        ImageView img=(ImageView) v.findViewById(R.id.ticket_qr_iv);
        img=generate.GenerateClick(v,img,singleItem.getQRcode());
       // holder.itemImage.setImageBitmap();

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView qrCodeText;

        protected ImageView itemImage;
        protected TextView feeStatus,eventFee,eventDate,eventTime,eventName;


        public SingleItemRowHolder(View view) {
            super(view);

            this.qrCodeText = (TextView) view.findViewById(R.id.tv_qr_code_text);
            this.itemImage = (ImageView) view.findViewById(R.id.ticket_qr_iv);
            this.feeStatus=(TextView)view.findViewById(R.id.tv_event_fees_status);
            this.eventFee=(TextView)view.findViewById(R.id.tv_event_fees);
            this.eventDate=(TextView)view.findViewById(R.id.tv_event_date);
            this.eventTime=(TextView)view.findViewById(R.id.tv_event_time);
            this.eventName=(TextView)view.findViewById(R.id.tv_event_name);



        }

    }

}
