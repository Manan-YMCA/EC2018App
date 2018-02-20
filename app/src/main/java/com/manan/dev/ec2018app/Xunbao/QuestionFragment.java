package com.manan.dev.ec2018app.Xunbao;


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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class QuestionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);

        final ProgressBar progressBar=view.findViewById(R.id.progress_bar);
        final String queURL = "https://xunbao-1.herokuapp.com/getq/";

        final TextView question = view.findViewById(R.id.question);
        final ImageView xunbaoimg=view.findViewById(R.id.xunbaoimg);
        final EditText ans=view.findViewById(R.id.answer);
        final Button submit=view.findViewById(R.id.submit);
        final TextView contestEnd=view.findViewById(R.id.contest_ends);
        final RelativeLayout questionLayout=view.findViewById(R.id.question_layout);

        final ImageView refreshButton=view.findViewById(R.id.refresh_button);
        final TextView refreshText=view.findViewById(R.id.refresh_text);

        JSONArray jsonArray =new JSONArray();
        JSONObject params =new JSONObject();
        try {
            params.put("email", "gK");
            params.put("skey", "abbv");
            jsonArray.put(params);
        }catch (Exception e){

        }

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        final JsonArrayRequest jobReq = new JsonArrayRequest(Request.Method.POST, queURL, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject resp= null;
                        try{

                            progressBar.setVisibility(View.GONE);
                            resp = response.getJSONObject(0);

                            try {
                                String end = resp.getString("response");
                                contestEnd.setVisibility(View.VISIBLE);
                                if (end.equals("2"))
                                    contestEnd.setText("LOOKS LIKE THE GAME ENDS!\n HOPE YOU HAD FUN, YOU CAN SEE THE WINNERS ON LEADERBOARD.\n IF YOU HAVE WON, WE WILL CONTACT YOU SHORTLY.");
                                else
                                    contestEnd.setText("YOU HAVE SUCCESSFULLY COMPLETED ALL THE QUESTIONS. WE WILL ANNOUNCE THE WINNERS ON 31st March, 2018.IF YOU HAVE WIN, WE WILL CONTACT YOU SHORTLY");

                            } catch (Exception e){

                                question.setVisibility(View.VISIBLE);
                                submit.setVisibility(View.VISIBLE);
                                ans.setVisibility(View.VISIBLE);
                                xunbaoimg.setVisibility(View.VISIBLE);
                                String imgUrl =resp.getString("image");
                                String que=resp.getString("desc");
                                question.setText(que);
                                Picasso.with(getActivity()).load(imgUrl).into(xunbaoimg);
                                progressBar.setVisibility(View.GONE);

                            }
                        } catch (JSONException e) {

                            Log.v("hey","catch");
                            progressBar.setVisibility(View.GONE);
                            refreshButton.setVisibility(View.VISIBLE);
                            refreshText.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressBar.setVisibility(View.GONE);
                        refreshButton.setVisibility(View.VISIBLE);
                        refreshText.setVisibility(View.VISIBLE);

                        volleyError.printStackTrace();
                    }
                });
        queue.add(jobReq);
        refreshButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        question.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                        ans.setVisibility(View.GONE);
                        xunbaoimg.setVisibility(View.GONE);
                        contestEnd.setVisibility(View.GONE);
                        refreshButton.setVisibility(View.GONE);
                        refreshText.setVisibility(View.GONE);
                        queue.add(jobReq);
                    }
                }
        );

        return view;

    }

}
