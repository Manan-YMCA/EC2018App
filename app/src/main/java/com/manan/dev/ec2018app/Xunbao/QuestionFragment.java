package com.manan.dev.ec2018app.Xunbao;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.LoginActivity;
import com.manan.dev.ec2018app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class QuestionFragment extends Fragment {
    TextView question,contestEnd,refreshText,stage;
    ImageView xunbaoimg,refreshButton;
    Button submit;
    EditText ans;
    String queURL,ansURL,statusURL;
    StringRequest stat;
    JsonArrayRequest jobReq;
    JSONArray jsonArray;
    JSONObject params;
    RequestQueue queue;
    RelativeLayout queLayout;
    ProgressDialog progressBar;
    int xstatus=2;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);




        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Login Required!");
        builder.setMessage("To continue, you must login with facebook");

        // add the buttons
        builder.setPositiveButton("Continue", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        builder.setNegativeButton("Cancel", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });

        final AlertDialog dialog = builder.create();


        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("(Not you)");
        progressBar.setTitle("Checking");
        progressBar.setCancelable(false);
        progressBar.show();

        queURL = "https://good-people.herokuapp.com/getq/";
        ansURL="https://good-people.herokuapp.com/checkans/";
        statusURL="https://good-people.herokuapp.com/status/";

        queLayout=view.findViewById(R.id.question_layout);

        stage =view.findViewById(R.id.stage);
        question = view.findViewById(R.id.question);
        xunbaoimg=view.findViewById(R.id.xunbaoimg);
        ans=view.findViewById(R.id.answer);
        submit=view.findViewById(R.id.submit);
        contestEnd=view.findViewById(R.id.contest_ends);
        refreshButton=view.findViewById(R.id.refresh_button);
        refreshText=view.findViewById(R.id.refresh_text);



        queue = Volley.newRequestQueue(getActivity());




        jsonArray =new JSONArray();
        params =new JSONObject();



        stat = new StringRequest(Request.Method.GET, statusURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        xstatus=Integer.parseInt(response);

                        if(xstatus==2){
                            progressBar.dismiss();
                            contestEnd.setText("KEEP CALM! CONTEST YET TO START");
                            contestEnd.setVisibility(View.VISIBLE);
                        }
                        else if(xstatus==1){
                            if(true) {
                                progressBar.dismiss();
                                dialog.show();
                            }
                            else {
                                try {
                                    params.put("email", "gda");
                                    params.put("skey", "abbv");
                                    jsonArray.put(params);
                                }catch (Exception e){

                                }
                                queue.add(jobReq);
                            }

                        }
                        else if(xstatus==3){
                            progressBar.dismiss();
                            contestEnd.setText("THE CONTEST IS OVER! THANKS FOR PLAYING. IF YOU HAVE WON, WE WILL CONTACT YOU SHORTLY");
                            contestEnd.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                refreshButton.setVisibility(View.VISIBLE);
                refreshText.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Problem loading!", Toast.LENGTH_SHORT).show();
            }
        });



        jobReq = new JsonArrayRequest(Request.Method.POST, queURL, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject resp;
                        try{
                            resp = response.getJSONObject(0);
                            try {
                                String end = resp.getString("response");
                                contestEnd.setVisibility(View.VISIBLE);
                                contestEnd.setText("YOU HAVE SUCCESSFULLY COMPLETED ALL THE QUESTIONS.\n WE WILL ANNOUNCE THE WINNERS ON 31st March, 2018.\nIF YOU HAVE WON, WE WILL CONTACT YOU SHORTLY");
                                progressBar.dismiss();

                            } catch (Exception e){
                                queLayout.setVisibility(View.VISIBLE);
                                String imgUrl =resp.getString("image");
                                String que=resp.getString("desc");
                                Integer level =resp.getInt("pk");
                                question.setText(que);
                                stage.setText("STAGE - "+Integer.toString(level));
                                Picasso.with(getActivity()).load("https://good-people.herokuapp.com"+imgUrl).into(xunbaoimg);
                                progressBar.dismiss();
                            }
                        } catch (JSONException e) {
                            progressBar.dismiss();
                            refreshButton.setVisibility(View.VISIBLE);
                            refreshText.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressBar.dismiss();
                        refreshButton.setVisibility(View.VISIBLE);
                        refreshText.setVisibility(View.VISIBLE);

                        volleyError.printStackTrace();
                    }
                });
        queue.add(stat);
        refreshButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reload();
                    }
                }
        );


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.show();
                JSONObject answer=new JSONObject();

                try {
                    answer.put("email","gKa");
                    answer.put("skey","abbv");
                    answer.put("ans",ans.getText());

                    final JsonObjectRequest answ = new JsonObjectRequest(Request.Method.POST, ansURL,answer,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject resp) {
                                    try{

                                        progressBar.dismiss();

                                        String end = resp.getString("response");
                                        contestEnd.setVisibility(View.VISIBLE);
                                        if (end.equals("0"))
                                            Toast.makeText(getActivity(), "Wrong answer!", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(getActivity(), "Congrats! Right answer!", Toast.LENGTH_SHORT).show();
                                            reload();
                                        }
                                    } catch (JSONException e) {

                                        Toast.makeText(getActivity(), "Problem submitting answer!", Toast.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    progressBar.dismiss();
                                    Toast.makeText(getActivity(), "Problem submitting answer!", Toast.LENGTH_SHORT).show();
                                    volleyError.printStackTrace();
                                }
                            });

                            queue.add(answ);

                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Problem submitting answer!", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                }

            }
        });

        return view;

    }

    public void reload(){
        progressBar.show();
        queLayout.setVisibility(View.GONE);
        contestEnd.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
        refreshText.setVisibility(View.GONE);
        queue.add(stat);
    }

}
