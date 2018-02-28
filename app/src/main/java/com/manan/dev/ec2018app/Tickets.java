package com.manan.dev.ec2018app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.Models.QRTicketModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Tickets extends AppCompatActivity {
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        SharedPreferences preferences=getSharedPreferences(getResources().getString(R.string.sharedPrefName),MODE_PRIVATE);
        final String phoneNumber=preferences.getString("Phone",null);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("I am working");
        mProgress.setTitle("yes i am");
        mProgress.setCanceledOnTouchOutside(false);
        if(phoneNumber==null)
        {
            Toast.makeText(this,"no data in shred preference",Toast.LENGTH_SHORT).show();
        }
        else
        {
            displayTickets(phoneNumber);
        }
    }

    private void displayTickets(String phoneNumber) {

        String url = getResources().getString(R.string.get_events_qr_code);
        url+=phoneNumber;
        Toast.makeText(this, "url: " + url, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), "dfdsfsd"+response, Toast.LENGTH_SHORT).show();
                Log.i("My success",""+response);
                mProgress.dismiss();
                try {
                    JSONObject obj1 = new JSONObject(response);
                    JSONArray ticketDetails=obj1.getJSONArray("data");
                    for(int i=0;i<ticketDetails.length();i++) {
                        JSONObject obj2 = ticketDetails.getJSONObject(i);
                        Long success = obj1.getLong("success");
                        if (success == 1) {
                            QRTicketModel TicketModel = new QRTicketModel();

                            TicketModel.setPaymentStatus(obj2.getInt("paymentstatus"));
                            TicketModel.setArrivalStatus(obj2.getInt("arrived"));
                            TicketModel.setQRcode(obj2.getString("qrcode"));
                            TicketModel.setEventID(obj2.getString("eventid"));
                            TicketModel.setTimeStamp(obj2.getLong("timestamp"));


                        } else {
                            Toast.makeText(Tickets.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                // Try and catch are included to handle any errors due to JSON
                catch (Exception e) {
                    // If an error occurs, this prints the error to the log
//                            Toast.makeText(FBLoginActivity.this, "Error aagya1", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    Toast.makeText(Tickets.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my error :"+error, Toast.LENGTH_LONG).show();
                Log.i("My error",""+error);
                mProgress.dismiss();
            }
        });
        queue.add(request);
    }
}
