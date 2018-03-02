package com.manan.dev.ec2018app;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        String eventName = getIntent().getStringExtra("eventName");
        String eventId = getIntent().getStringExtra("eventId");
        String eventType = getIntent().getStringExtra("eventType");

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
                intentPhone += mainPhone.getText().toString() + ",";
                intentMail += mainmail.getText().toString() + ",";
                for (int i = 0; i < nameText.size(); i++) {
                    intentName += nameText.get(i).getText().toString() + ",";
                }
                for (int i = 0; i < collegeText.size(); i++) {
                    intentClg += collegeText.get(i).getText().toString() + ",";
                }
                Toast.makeText(EventRegister.this, intentName, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void update() {
        for (int i = 0; i < memberno.size(); i++) {
            memberno.get(i).setText(String.valueOf(i+2));
        }


    }

}
