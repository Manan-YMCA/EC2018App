package com.manan.dev.ec2018app;


import android.app.FragmentManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.qrcode.encoder.QRCode;
import com.manan.dev.ec2018app.Fragments.QRCodeActivity;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventRegister extends AppCompatActivity {

    EditText name, mainPhone, mainName;
    EditText college, mainmail, mainClg;
    TextView personNo, eventTypeView, eventNameView;
    ArrayList<TextView> memberno;
    ArrayList<EditText> nameText, collegeText;
    String intentMail;
    String intentPhone;
    String intentClg;
    String intentName;
    int count = 1;
    TextView text;
    ViewGroup.LayoutParams layPar;
    private String eventName;
    private String eventId;
    private String eventType;

    private String qrCodeString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        eventName = getIntent().getStringExtra("eventName");
        eventId = getIntent().getStringExtra("eventId");
        eventType = getIntent().getStringExtra("eventType");

        Button Add = (Button) findViewById(R.id.add_mem_button);
        personNo = (TextView) findViewById(R.id.current_team_mem);
        layPar = personNo.getLayoutParams();
        memberno = new ArrayList<>();
        nameText = new ArrayList<>();
        collegeText = new ArrayList<>();
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
        if(eventType.equals("solo"))
        Add.setVisibility(View.GONE);
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
                        nameText.remove(remove_member-1);
                        collegeText.remove(remove_member-1);
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
                intentPhone += mainPhone.getText().toString() ;
                intentMail += mainmail.getText().toString() ;
                for (int i = 0; i < nameText.size(); i++) {
                    intentName += nameText.get(i).getText().toString() + ",";
                }
                intentName = intentName.substring(0, intentName.length()-1);

                for (int i = 0; i < collegeText.size(); i++) {
                    intentClg += collegeText.get(i).getText().toString() + ",";
                }
                intentClg = intentClg.substring( 0 , intentClg.length()-1);
                Log.d("RegisterEvent","intentname" + intentName + "intentclg " + intentClg + "intentphone" +
                        intentPhone +"intentmail" + intentMail);
//                Toast.makeText(EventRegister.this, "intentname" + intentName + "intentclg " + intentClg
//                        + "intentphone" + intentPhone +"intentmail" + intentMail, Toast.LENGTH_SHORT).show();
                registerEvent();

            }
        });


    }

    private void update() {
        for (int i = 0; i < memberno.size(); i++) {
            memberno.get(i).setText(String.valueOf(i+2));
        }
    }
    private void registerEvent(){
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

                    FragmentManager fm = getFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putString("qrcodestring", qrCodeString);
                    bundle.putString("eventid",eventId);
// set Fragmentclass Arguments
                    QRCodeActivity fragobj = new QRCodeActivity();
                    fragobj.setArguments(bundle);
                    fragobj.show(fm,"drff");
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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("name", intentName);
                map.put("phone", intentPhone);
                map.put("email", intentMail);
                map.put("college", intentClg);
                map.put("eventid", eventId );
                return map;
            }
        };
        queue.add(request);
    }

//    public void sendNotification(){
//        Notification.Builder noti = new Notification.Builder(getApplicationContext())
//                .setContentTitle(eventName)
//                .setContentText("")
//                .setSmallIcon(R.drawable.ec_app_logo)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//
//    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(EventRegister.this,SingleEventActivity.class)
        .putExtra("eventId",eventId).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
        return;
    }
}
