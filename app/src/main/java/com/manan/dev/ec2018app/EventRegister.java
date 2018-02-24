package com.manan.dev.ec2018app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventRegister extends AppCompatActivity {

    EditText name,mainPhone,mainName;
    EditText college,mainmail,mainClg;
    TextView personNo;
    ArrayList<Integer> indexer;
    ArrayList<EditText> nameText, collegeText;
    String intentMail= "";
    String intentPhone="";
    String intentClg = "";
    String intentName = "";
    int count=1;
    TextView text;
    ViewGroup.LayoutParams layPar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        Button Add=(Button)findViewById(R.id.add_member);
        personNo=(TextView)findViewById(R.id.person_no);
        layPar=personNo.getLayoutParams();
        nameText = new ArrayList<>();
        collegeText = new ArrayList<>();
        final LinearLayout layout=findViewById(R.id.inflate_layout);
        text=new TextView(this);


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater Inflat=getLayoutInflater();
                final View v=Inflat.from(EventRegister.this).inflate(R.layout.register_inflater,layout,false);
                name = (EditText) v.findViewById(R.id.inflate_reg_name);
                college  = (EditText) v.findViewById(R.id.inflate_reg_clg);
                count++;
                nameText.add(name);
                collegeText.add(college);
                layout.addView(createNewTextView(count));
                layout.addView(v);
            }

        });
        Button bt = (Button) findViewById(R.id.bt_reg_event);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainName=(EditText)findViewById(R.id.event_reg_main_name);
                intentName+=mainName.getText().toString()+",";
                mainClg=(EditText)findViewById(R.id.event_reg_main_clg);
                intentClg+=mainClg.getText().toString()+",";
                mainPhone=(EditText)findViewById(R.id.event_reg_main_mob);
                intentPhone+=mainPhone.getText().toString()+",";
                mainmail=(EditText)findViewById(R.id.event_reg_main_email);
                intentMail+=mainmail.getText().toString()+",";
                for(int i = 0; i < nameText.size(); i++){
                    intentName += nameText.get(i).getText().toString()+",";
                }
                for(int i = 0; i < collegeText.size(); i++){
                    intentClg += collegeText.get(i).getText().toString()+",";
                }
                Toast.makeText(EventRegister.this, intentName, Toast.LENGTH_SHORT).show();

            }
        });
    }
    private TextView createNewTextView(int count) {
        text=new TextView(this);
        text.setText("Person "+ count);
        text.setLayoutParams(layPar);
        text.setTextColor(getResources().getColor(R.color.White));
        return text;

    }
}
