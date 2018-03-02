package com.manan.dev.ec2018app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.Adapters.TicketLayoutAdapter;
import com.manan.dev.ec2018app.Models.QRTicketModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tickets extends AppCompatActivity {
    ProgressDialog mProgress;
    private TicketLayoutAdapter mAdapter;
    private ArrayList<QRTicketModel> userTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);

        final String phoneNumber = preferences.getString("Phone", null);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("I am working");
        mProgress.setTitle("yes i am");
        mProgress.setCanceledOnTouchOutside(false);
        GridView userTicketsView = (GridView) findViewById(R.id.gl_user_tickets);
        userTickets = new ArrayList<>();

        mAdapter = new TicketLayoutAdapter(getApplicationContext(), userTickets);
        Log.d("Tickets", phoneNumber);
        Log.d("Tickets", Integer.toString(userTickets.size()));
        userTicketsView.setAdapter(mAdapter);


        if (phoneNumber == null) {
            Toast.makeText(this, "no data in shred preference", Toast.LENGTH_SHORT).show();
        } else {
            displayTickets(phoneNumber);
        }
    }

    private void displayTickets(String phoneNumber) {

        String url = getResources().getString(R.string.get_events_qr_code);
        url += phoneNumber;
        Toast.makeText(this, "url: " + url, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("My success", "" + response);
                mProgress.dismiss();
                try {
                    JSONObject obj1 = new JSONObject(response);
                    JSONArray ticketDetails = obj1.getJSONArray("data");
                    for (int i = 0; i < ticketDetails.length(); i++) {
                        JSONObject obj2 = ticketDetails.getJSONObject(i);
                        QRTicketModel TicketModel = new QRTicketModel();

                        TicketModel.setPaymentStatus(obj2.getInt("paymentstatus"));
                        TicketModel.setArrivalStatus(obj2.getInt("arrived"));
                        TicketModel.setQRcode(obj2.getString("qrcode"));
                        TicketModel.setEventID(obj2.getString("eventid"));
                        TicketModel.setTimeStamp(obj2.getLong("timestamp"));

                        userTickets.add(TicketModel);
                    }
                    Log.d("Tickets", Integer.toString(userTickets.size()));
                    mAdapter.notifyDataSetChanged();
                }
                // Try and catch are included to handle any errors due to JSON
                catch (Exception e) {
                    // If an error occurs, this prints the error to the log
//                            Toast.makeText(FBLoginActivity.this, "Error aagya1", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    Log.d("Tickets", e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Tickets", "" + error);
                mProgress.dismiss();
            }
        });
        queue.add(request);
    }
}
