package com.manan.dev.ec2018app.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Models.EventDetails;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Utilities.TicketsGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class QRCodeActivity extends DialogFragment {
    TextView eventName, eventDate, eventTime, fees, status;
    ImageView qrTicketImage, back;
    private String eventId;
    private EventDetails eventDetails;
    private DatabaseController getEventDetails;
    private int activity, paymentStatus, arrivalStatus;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public QRCodeActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.qrcode_dialog_box, container, false);
        String qrcodestring = getArguments().getString("qrcodestring");
        eventId = getArguments().getString("eventid");
        activity = getArguments().getInt("activity");
        paymentStatus = getArguments().getInt("paymentStatus");
        arrivalStatus = getArguments().getInt("arrivalStatus");

        qrTicketImage = (ImageView) rootView.findViewById(R.id.qr_ticket);
        eventName = (TextView) rootView.findViewById(R.id.tv_event_name);
        eventDate = (TextView) rootView.findViewById(R.id.tv_event_date);
        eventTime = (TextView) rootView.findViewById(R.id.tv_event_time);
        fees = (TextView) rootView.findViewById(R.id.eventfees);
        status = (TextView) rootView.findViewById(R.id.iv_event_fees_status);
        back = (ImageView) rootView.findViewById(R.id.iv_cross);
        getEventDetails = new DatabaseController(mContext);
        eventDetails = getEventDetails.retreiveEventsByID(eventId);
        eventName.setText(eventDetails.getmName());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(eventDetails.getmStartTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(cal.getTime());

        SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
        String formattedTime = sdf1.format(cal.getTime());
        eventDate.setText(formattedDate);
        eventTime.setText(formattedTime);

        fees.setText(String.valueOf("RS " + eventDetails.getmFees()));

        if (String.valueOf(eventDetails.getmFees()).equals("0")) {
            status.setTextColor(mContext.getResources().getColor(R.color.status_free));
            status.setText("FREE");
        } else if (String.valueOf(paymentStatus).equals("0")) {
            status.setTextColor(mContext.getResources().getColor(R.color.primaryFocused));
            status.setText("PENDING");
        } else {
            status.setTextColor(mContext.getResources().getColor(R.color.status_paid));
            status.setText("PAID");
        }


        TicketsGenerator ticketsGenerator = new TicketsGenerator();
        Bitmap qrTicket = ticketsGenerator.GenerateClick(qrcodestring, mContext, (int) getResources().getDimension(R.dimen.threefifty), (int) getResources().getDimension(R.dimen.twoforty), 120, 120);
        qrTicketImage.setImageBitmap(qrTicket);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (activity == 1) {
            getActivity().finish();
        }
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCancelable(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
