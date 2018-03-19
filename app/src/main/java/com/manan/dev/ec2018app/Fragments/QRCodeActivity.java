package com.manan.dev.ec2018app.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.manan.dev.ec2018app.Models.QRTicketModel;
import com.manan.dev.ec2018app.ProfileActivity;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.SingleEventActivity;
import com.manan.dev.ec2018app.Utilities.TicketsGenerator;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class QRCodeActivity extends DialogFragment {
    TextView eventName, eventDate, eventTime, fees, status;
    ImageView qrTicketImage;
    private String eventId;
    private EventDetails eventDetails;
    private DatabaseController getEventDetails;
    private int activity, paymentStatus, arrivalStatus;

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
        status = (TextView) rootView.findViewById(R.id.eventfeestatus);
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

        fees.setText(String.valueOf("RS " + eventDetails.getmFees()));
        status.setText(String.valueOf(paymentStatus));

        TicketsGenerator ticketsGenerator = new TicketsGenerator();
        Bitmap qrTicket = ticketsGenerator.GenerateClick(qrcodestring, getActivity(), (int) getResources().getDimension(R.dimen.threefifty), (int) getResources().getDimension(R.dimen.twoforty), 120, 120);
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
