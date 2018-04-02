package com.manan.dev.ec2018app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.Adapters.TicketLayoutAdapter;
import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Models.QRTicketModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Tickets extends AppCompatActivity {
    ProgressDialog mProgress;
    private TicketLayoutAdapter mAdapter;
    private ArrayList<QRTicketModel> userTickets;
    private DatabaseController databaseController;
    private RecyclerView userTicketsView;
    private ImageView tickback;
    private TextView noTickets;
    SwipeRefreshLayout s;
    private String phoneNumber;

    private Handler mIncomingHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        phoneNumber = preferences.getString("Phone", null);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Showing your ticket.");
        mProgress.setTitle("Loading...");
        mProgress.setCanceledOnTouchOutside(false);
        noTickets = (TextView) findViewById(R.id.tv_no_tickets);

        userTicketsView = (RecyclerView) findViewById(R.id.gl_user_tickets);
        userTicketsView.setLayoutManager(new LinearLayoutManager(Tickets.this));
        userTickets = new ArrayList<>();
        mIncomingHandler = new IncomingHandler(Tickets.this);
        databaseController = new DatabaseController(Tickets.this);
        mAdapter = new TicketLayoutAdapter(Tickets.this, userTickets);

        if (userTickets.size() > 0) {
            noTickets.setVisibility(View.GONE);
        }
        userTicketsView.setAdapter(mAdapter);
        tickback = findViewById(R.id.tic_back_button);

        s = findViewById(R.id.swipe_refresh_layout);

        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable())
                    new LoadTickets().execute();
                else
                    MDToast.makeText(Tickets.this, "No Internet Connection", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                s.setRefreshing(false);
            }
        });
        if (phoneNumber == null) {
        } else {

            displayTickets(phoneNumber);
            //  new DisplayTickets().execute();

        }
        tickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void reload(final String phone) {

        String url = getResources().getString(R.string.get_events_qr_code);
        url += phone;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgress.dismiss();
                try {
                    JSONObject obj1 = new JSONObject(response);
                    JSONArray ticketDetails = obj1.getJSONArray("data");
                    Log.e("NO", String.valueOf(ticketDetails.length()));
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
                    mIncomingHandler.sendEmptyMessage(0);
                }
                // Try and catch are included to handle any errors due to JSON
                catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mProgress.dismiss();
            }
        });
        queue.add(request);
    }

    private void displayTickets(String phoneNumber) {
   //     Log.e("NO", String.valueOf(userTickets.size()));

        userTickets = databaseController.retrieveAllTickets();
        if (userTickets.size() > 0) {
            noTickets.setVisibility(View.GONE);
            mProgress.dismiss();
            mAdapter = new TicketLayoutAdapter(Tickets.this, userTickets);
            userTicketsView.setAdapter(mAdapter);
        }
        //mAdapter.notifyDataSetChanged();
    }

    public class IncomingHandler extends Handler {
        private WeakReference<Tickets> yourActivityWeakReference;

        IncomingHandler(Tickets yourActivity) {
            yourActivityWeakReference = new WeakReference<>(yourActivity);
            databaseController = new DatabaseController(getApplicationContext());
        }

        @Override
        public void handleMessage(Message message) {
            if (yourActivityWeakReference != null) {
                Tickets yourActivity = yourActivityWeakReference.get();
                switch (message.what) {
                    case 0:
                        updateDatabase();
                        break;
                }
            }
        }
    }

        private void updateDatabase() {
            if (userTickets.size() > databaseController.getTicketCount()) {
                for (int i = 0; i < userTickets.size(); i++) {
                    databaseController.addTicketsToDb(userTickets.get(i));
                }
            } else {
                for (int i = 0; i < userTickets.size(); i++) {
                    databaseController.updateDbTickets(userTickets.get(i));
                }
            }
displayTickets(phoneNumber);
            // new DisplayTickets().execute();
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }


    private class LoadTickets extends AsyncTask<Void, Void, Void> {
        public LoadTickets() {
            super();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            reload(phoneNumber);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgress.dismiss();
        }


    }
//    private class DisplayTickets extends AsyncTask<Void, Void, Void> {
//        public DisplayTickets() {
//            super();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            displayTickets(phoneNumber);
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//
//    }





}
