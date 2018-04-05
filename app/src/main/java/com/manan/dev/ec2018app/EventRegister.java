package com.manan.dev.ec2018app;


import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Fragments.QRCodeActivity;
import com.manan.dev.ec2018app.Models.EventDetails;
import com.manan.dev.ec2018app.Models.QRTicketModel;
import com.manan.dev.ec2018app.Models.UserDetails;
import com.manan.dev.ec2018app.Notifications.MyNotificationResponse;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EventRegister extends AppCompatActivity {

    EditText name, mainPhone, mainName;
    AlarmManager alarmManager;
    PendingIntent pending_intent;
    EditText college, mainmail, mainClg;
    TextView personNo, eventTypeView, eventNameView;
    ArrayList<TextView> memberno;
    ArrayList<EditText> nameText, collegeText;
    String intentMail;
    String intentPhone;
    String intentClg;
    String intentName;
    UserDetails userDetails;
    int count = 1;
    TextView text;
    ViewGroup.LayoutParams layPar;
    private String eventName;
    private String eventId;
    private String eventType;
    private QRTicketModel TicketModel;
    private String qrCodeString;
    private DatabaseController databaseController;
    private ProgressBar barLoader;
    ProgressDialog pd, pd1;
    private ArrayList<QRTicketModel> userTickets;
    private IncomingHandler mIncomingHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        eventName = getIntent().getStringExtra("eventName");
        eventId = getIntent().getStringExtra("eventId");
        eventType = getIntent().getStringExtra("eventType");
        userDetails = new UserDetails();
        userTickets = new ArrayList<>();
        mIncomingHandler = new IncomingHandler(EventRegister.this);

//        barLoader = (ProgressBar) findViewById(R.id.pb_register);
//        barLoader.setVisibility(View.VISIBLE);

        pd = new ProgressDialog(EventRegister.this);
        pd.setMessage("Making your Ticket");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        pd1 = new ProgressDialog(EventRegister.this);
        pd1.setMessage("Loading your details");
        pd1.setCancelable(true);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Button Add = (Button) findViewById(R.id.add_mem_button);
        personNo = (TextView) findViewById(R.id.current_team_mem);
        layPar = personNo.getLayoutParams();
        memberno = new ArrayList<>();
        nameText = new ArrayList<>();
        collegeText = new ArrayList<>();
        databaseController = new DatabaseController(EventRegister.this);
        eventTypeView = (TextView) findViewById(R.id.max_team_mem);
        eventTypeView.setText(eventType);
        eventNameView = (TextView) findViewById(R.id.tv_event_name);
        eventNameView.setText(eventName);

        mainName = (EditText) findViewById(R.id.ld_reg_name);
        mainClg = (EditText) findViewById(R.id.ld_reg_clg);
        mainPhone = (EditText) findViewById(R.id.ld_mobile);
        mainmail = (EditText) findViewById(R.id.ld_email);
        nameText.add(mainName);
        collegeText.add(mainClg);

        final LinearLayout layout = findViewById(R.id.layout_infater);
        text = new TextView(this);
        if (eventType.equals("solo"))
            Add.setVisibility(View.GONE);
        if (eventType.equals("solo"))
            Add.setVisibility(View.GONE);
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        final String phoneNumber = prefs.getString("Phone", null);
        if (phoneNumber == null) {

        }
        if (isNetworkAvailable()) {
            pd1.show();
            getDetails(phoneNumber);
        }
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personNo.setVisibility(View.VISIBLE);
                final View v = LayoutInflater.from(EventRegister.this).inflate(R.layout.register_inflater, layout, false);
                name = (EditText) v.findViewById(R.id.inflate_reg_name);
                college = (EditText) v.findViewById(R.id.inflate_reg_clg);
                final ImageView remove = (ImageView) v.findViewById(R.id.bt_remove);
                final TextView tv_2 = (TextView) v.findViewById(R.id.member_no_count);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewGroup p1 = (ViewGroup) v.getParent();
                        ViewGroup p2 = (ViewGroup) p1.getParent();
                        ViewGroup p3 = (ViewGroup) p2.getParent();
                        Integer remove_member = Integer.parseInt(tv_2.getText().toString());

                        nameText.remove(remove_member - 1);
                        collegeText.remove(remove_member - 1);
                        memberno.remove(tv_2);
                        update();
                        p3.removeView(p2);
                        count--;
                        personNo.setText(String.valueOf(count));
                    }
                });
                count++;
                personNo.setText(String.valueOf(count));
                tv_2.setText(String.valueOf(count));
                memberno.add(tv_2);
                nameText.add(name);
                collegeText.add(college);

                layout.addView(v);
            }


        });


        Button bt = (Button) findViewById(R.id.register_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentName = "";
                intentClg = "";
                intentMail = "";
                intentPhone = "";
                intentPhone += mainPhone.getText().toString();
                intentMail += mainmail.getText().toString();
                for (int i = 0; i < nameText.size(); i++) {
                    intentName += nameText.get(i).getText().toString() + ",";
                }
                intentName = intentName.substring(0, intentName.length() - 1);

                for (int i = 0; i < collegeText.size(); i++) {
                    intentClg += collegeText.get(i).getText().toString() + ",";
                }
                intentClg = intentClg.substring(0, intentClg.length() - 1);
//
                Boolean checker = validateCredentials();
                if (checker) {
                    pd.show();
                    registerEvent();

                }
            }

        });


    }

    private void update() {
        for (int i = 0; i < memberno.size(); i++) {
            memberno.get(i).setText(String.valueOf(i + 2));
        }
    }

    private void registerEvent() {
        String url = getResources().getString(R.string.event_register_api);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);

                    int status = obj.getInt("success");
                    SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
                    String phoneNumber = prefs.getString("Phone", null);

                    if (status == 0) {
                        pd.dismiss();
                        checkCount(phoneNumber);
                        MDToast.makeText(EventRegister.this, "Already Registered fro the Event", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                    } else {
                        qrCodeString = (obj.getString("qrcode"));
                        if (phoneNumber == null) {
                            MDToast.makeText(EventRegister.this, "To view your Ticket Login with your registered Mobile Number", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                        }
                        pd.dismiss();
                        sendNotification();
                        FragmentManager fm = getFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putString("qrcodestring", qrCodeString);
                        bundle.putString("eventid", eventId);
                        bundle.putInt("activity", 1);
                        bundle.putString("eventid", eventId);
// set Fragmentclass Arguments
                        QRCodeActivity fragobj = new QRCodeActivity();
                        fragobj.setCancelable(true);
                        fragobj.setArguments(bundle);
                        fragobj.show(fm, "drff");
                        final String phone = prefs.getString("Phone", null);

                        addTicket(phone);
                    }
                }
                // Try and catch are included to handle any errors due to JSON
                catch (Exception e) {
                    // If an error occurs, this prints the error to the log

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("name", intentName);
                map.put("phone", intentPhone);
                map.put("email", intentMail);
                map.put("college", intentClg);
                map.put("eventname", eventName);
                return map;
            }
        };
        queue.add(request);
    }

    private void checkCount(final String phone) {
        String url = getResources().getString(R.string.get_events_qr_code);
        url += phone;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

            }
        });
        queue.add(request);

    }


    public void sendNotification() {
        Intent my_intent = new Intent(EventRegister.this, MyNotificationResponse.class);
        my_intent.putExtra("eventId", eventId);
        my_intent.putExtra("eventName", eventName);

        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
//        calendar.add(Calendar.SECOND, 10);

        DatabaseController db = new DatabaseController(getApplicationContext());
        EventDetails currEvent = new EventDetails();
        currEvent = db.retreiveEventsByID(eventId);
        Long eventStartTime = currEvent.getmStartTime();
        calendar.setTimeInMillis(eventStartTime);
        calendar.add(Calendar.HOUR, -1);

        SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

        my_intent.putExtra("uniqueId", currEvent.getmUniqueKey());

        pending_intent = PendingIntent.getBroadcast(EventRegister.this, currEvent.getmUniqueKey(), my_intent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);

    }

    private Boolean validateCredentials() {

        if (!isNetworkAvailable()) {
            MDToast.makeText(EventRegister.this, "No Internet Connection", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            return false;
        }

        for (EditText nameTextView : nameText) {
            if (nameTextView.getText().toString().equals("")) {
                nameTextView.setError("Enter a User Name");
                return false;
            }
        }
        for (EditText collegeTextView : collegeText) {
            if (collegeTextView.getText().toString().equals("")) {
                collegeTextView.setError("Enter a College Name");
                return false;
            }
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mainmail.getText().toString()).matches()) {
            mainmail.setError("Enter a Valid Email Address");
            return false;
        }
        if (mainPhone.getText().toString().equals("")) {
            mainPhone.setError("Enter a Phone Number");
            return false;
        }
        if (!Patterns.PHONE.matcher(mainPhone.getText().toString()).matches()) {
            mainPhone.setError("Enter a valid Phone Number");
            return false;
        }
        if (mainPhone.getText().toString().length() != 10) {
            mainPhone.setError("Enter a valid Phone Number");
            return false;
        }
        return true;
    }

    private void getDetails(final String phone) {
        String url = getResources().getString(R.string.get_user_details_api) + phone;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            barLoader.setVisibility(View.GONE);
                            pd1.dismiss();
                            JSONObject obj1 = new JSONObject(response);
                            JSONObject obj = obj1.getJSONObject("data");
                            mainName.setText(obj.getString("name"));
                            mainmail.setText(obj.getString("email"));
                            mainClg.setText(obj.getString("college"));
                            mainPhone.setText(phone);
                            userDetails.setmName(obj.getString("name"));
                            userDetails.setEmail(obj.getString("email"));
                            userDetails.setmCollege(obj.getString("college"));
                            userDetails.setmPhone(phone);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(obreq);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void addTicket(final String phoneNumber) {
        String url = getResources().getString(R.string.get_events_qr_code);
        url += phoneNumber;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj1 = new JSONObject(response);
                    JSONArray ticketDetails = obj1.getJSONArray("data");
                    for (int i = 0; i < ticketDetails.length(); i++) {
                        JSONObject obj2 = ticketDetails.getJSONObject(i);
                        if (obj2.getString("eventid").equals(eventId)) {
                            TicketModel = new QRTicketModel();

                            TicketModel.setPaymentStatus(obj2.getInt("paymentstatus"));
                            TicketModel.setArrivalStatus(obj2.getInt("arrived"));
                            TicketModel.setQRcode(obj2.getString("qrcode"));
                            TicketModel.setEventID(obj2.getString("eventid"));
                            TicketModel.setTimeStamp(obj2.getLong("timestamp"));
                            databaseController.addTicketsToDb(TicketModel);
                        }

                    }
                }
                // Try and catch are included to handle any errors due to JSON
                catch (Exception e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        queue.add(request);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class IncomingHandler extends Handler {
        private WeakReference<EventRegister> yourActivityWeakReference;

        IncomingHandler(EventRegister yourActivity) {
            yourActivityWeakReference = new WeakReference<>(yourActivity);
            databaseController = new DatabaseController(getApplicationContext());
        }

        @Override
        public void handleMessage(Message message) {
            if (yourActivityWeakReference != null) {
                EventRegister yourActivity = yourActivityWeakReference.get();

                switch (message.what) {
                    case 0:
                        updateDatabase();
                        break;
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

            viewTicket();
        }

    }

    private void viewTicket() {

        QRTicketModel ticket = new QRTicketModel();
        ticket = databaseController.retrieveTicketsByID(eventId);

        FragmentManager fm = getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("qrcodestring", ticket.getQRcode());
        bundle.putString("eventid", eventId);
        bundle.putInt("activity", 1);
        bundle.putString("eventid", eventId);
// set Fragmentclass Arguments
        QRCodeActivity fragobj = new QRCodeActivity();
        fragobj.setCancelable(true);
        fragobj.setArguments(bundle);
        fragobj.show(fm, "drff");

    }
}