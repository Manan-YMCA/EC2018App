package com.manan.dev.ec2018app.Fragments;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Models.EventDetails;
import com.manan.dev.ec2018app.ProfileActivity;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.SingleEventActivity;
import com.manan.dev.ec2018app.Utilities.TicketsGenerator;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class QRCodeActivity extends DialogFragment {
    TextView eventName,eventDate,eventTime;
    ImageView qrTicketImage ;
    private String eventId;
    private EventDetails eventDetails;
    private DatabaseController getEventDetails;
    private int activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.qrcode_dialog_box, container, false);
        String qrcodestring = getArguments().getString("qrcodestring");
        eventId = getArguments().getString("eventid");
        activity = getArguments().getInt("activity");
        qrTicketImage = (ImageView)rootView.findViewById(R.id.qr_ticket);
        eventName = (TextView)rootView.findViewById(R.id.tv_event_name);
        eventDate = (TextView)rootView.findViewById(R.id.tv_event_date);
        eventTime = (TextView)rootView.findViewById(R.id.tv_event_time);
        getEventDetails = new DatabaseController(getActivity());
        eventDetails = getEventDetails.retreiveEventsByID(eventId);
        eventName.setText(eventDetails.getmName());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(eventDetails.getmStartTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

        String formattedDate = sdf.format(cal.getTime());

        SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
        String formattedTime = sdf1.format(cal.getTime());
        eventDate.setText(formattedDate);
        eventTime.setText(formattedTime);

        TicketsGenerator ticketsGenerator = new TicketsGenerator();
        Bitmap qrTicket = ticketsGenerator.GenerateClick( qrcodestring, getActivity(), (int) getResources().getDimension(R.dimen.threefifty), (int) getResources().getDimension(R.dimen.twoforty));
        qrTicketImage.setImageBitmap(qrTicket);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if(activity == 1) {
            getActivity().finish();
        }
        super.onDestroyView();
    }
}
