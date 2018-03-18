package com.manan.dev.ec2018app;


import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        eventName = getIntent().getStringExtra("eventName");
        eventId = getIntent().getStringExtra("eventId");
        eventType = getIntent().getStringExtra("eventType");
        userDetails = new UserDetails();

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
            Toast.makeText(this, "shared pref no data", Toast.LENGTH_SHORT).show();
        }
        getDetails(phoneNumber);
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
                        Log.d("counter", String.valueOf(remove_member));
                        nameText.remove(remove_member - 1);
                        collegeText.remove(remove_member - 1);
                        Log.d("counter", String.valueOf(nameText.size()));
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
                Log.d("RegisterEvent", "intentname" + intentName + "intentclg " + intentClg + "intentphone" +
                        intentPhone + "intentmail" + intentMail);
//                Toast.makeText(EventRegister.this, "intentname" + intentName + "intentclg " + intentClg
//                        + "intentphone" + intentPhone +"intentmail" + intentMail, Toast.LENGTH_SHORT).show();
                Boolean checker = validateCredentials();
                if (checker) {
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
        Toast.makeText(this, "url: " + url, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(EventRegister.this, obj.getString("qrcode"), Toast.LENGTH_LONG).show();
                    qrCodeString = (obj.getString("qrcode"));

                    sendNotification();
                    FragmentManager fm = getFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putString("qrcodestring", qrCodeString);
                    bundle.putString("eventid", eventId);
                    bundle.putInt("activity", 1);
                    bundle.putString("eventid", eventId);
// set Fragmentclass Arguments
                    QRCodeActivity fragobj = new QRCodeActivity();
                    fragobj.setArguments(bundle);
                    fragobj.show(fm, "drff");
                    SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
                    final String phone = prefs.getString("Phone", null);
                    if (phone == null) {
                        Toast.makeText(EventRegister.this, "shared pref no data", Toast.LENGTH_SHORT).show();
                    }
                    addTicket(phone);
                }
                // Try and catch are included to handle any errors due to JSON
                catch (Exception e) {
                    // If an error occurs, this prints the error to the log
                    e.printStackTrace();
                    Toast.makeText(EventRegister.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("name", intentName);
                map.put("phone", intentPhone);
                map.put("email", intentMail);
                map.put("college", intentClg);
                map.put("eventid", eventId);
                return map;
            }
        };
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

        Log.d("values", sdf1.format(calendar.getTime()) + " " + sdf.format(calendar.getTime()));
        my_intent.putExtra("uniqueId", currEvent.getmUniqueKey());

        pending_intent = PendingIntent.getBroadcast(EventRegister.this, currEvent.getmUniqueKey(), my_intent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);

    }

    private Boolean validateCredentials() {
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
        Log.i("tg", "bgg");
        String url = getResources().getString(R.string.get_user_details_api) + phone;
        Toast.makeText(this, "URL: " + url, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            JSONObject obj = obj1.getJSONObject("data");
                            Toast.makeText(EventRegister.this, obj.getString("name"), Toast.LENGTH_LONG).show();
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
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                            Toast.makeText(EventRegister.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EventRegister.this, "Error aagya2", Toast.LENGTH_SHORT).show();
                        Log.e("Volley", "Error");
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
        Toast.makeText(this, "url: " + url, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("My success", "" + response);
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
                    Log.d("Tickets", e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Tickets", "" + error);
            }
        });
        queue.add(request);
    }
}