package com.manan.dev.ec2018app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    EditText name,mainPhone,mainName;
    EditText college,mainmail,mainClg;
    TextView personNo;
    ArrayList<TextView> memberno;
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

        Button Add=(Button)findViewById(R.id.add_mem_button);
        personNo=(TextView)findViewById(R.id.current_team_mem);
        layPar=personNo.getLayoutParams();
        memberno =new ArrayList<>();
        nameText = new ArrayList<>();
        collegeText = new ArrayList<>();
        final LinearLayout layout=findViewById(R.id.layout_infater);
        text=new TextView(this);

    Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater Inflat=getLayoutInflater();
                final View v=Inflat.from(EventRegister.this).inflate(R.layout.register_inflater,layout,false);
                name = (EditText) v.findViewById(R.id.inflate_reg_name);
                college  = (EditText) v.findViewById(R.id.inflate_reg_clg);
                ImageView remove = (ImageView) v.findViewById(R.id.bt_remove);
                 final TextView tv_1= (TextView) v.findViewById(R.id.member_no);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewGroup p1 = (ViewGroup) v.getParent();
                        ViewGroup p2 = (ViewGroup) p1.getParent();
                        ViewGroup p3 = (ViewGroup) p2.getParent();
                      memberno.remove(tv_1);
                        update();
                        p3.removeView(p2);
                        count--;
                    }
                });
                count++;
                tv_1.setText("Member :"+count);
                memberno.add(tv_1);
                nameText.add(name);
                collegeText.add(college);

                layout.addView(createNewTextView(count));

                layout.addView(v);
            }


        });




        Button bt = (Button) findViewById(R.id.register_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainName=(EditText)findViewById(R.id.ld_reg_name);
                intentName+=mainName.getText().toString()+",";
                mainClg=(EditText)findViewById(R.id.ld_reg_clg);
                intentClg+=mainClg.getText().toString()+",";
                mainPhone=(EditText)findViewById(R.id.ld_mobile);
                intentPhone+=mainPhone.getText().toString()+",";
                mainmail=(EditText)findViewById(R.id.ld_email);
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

    private void update() {
        for (int i=0; i<memberno.size();i++)
        {
            memberno.get(i).setText("Member :"+(i+2));
        }


    }

    private TextView createNewTextView(int count) {
        text=new TextView(this);
        text.setText("Person "+ count);
        text.setLayoutParams(layPar);
        text.setTextColor(getResources().getColor(R.color.White));
        return text;

    }
}
