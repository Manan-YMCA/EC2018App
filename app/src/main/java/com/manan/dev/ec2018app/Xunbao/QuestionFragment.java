package com.manan.dev.ec2018app.Xunbao;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.manan.dev.ec2018app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class QuestionFragment extends Fragment implements XunbaoActivity.loadQuestionFragment {
    TextView question, contestEnd, refreshText, stage;
    ImageView xunbaoimg, refreshButton;
    LinearLayout submit;
    EditText ans;
    String queURL, ansURL, statusURL;
    StringRequest stat;
    JsonArrayRequest jobReq;
    RequestQueue queue;
    RelativeLayout queLayout;
    //ProgressDialog progressBar;
    int xstatus = 2;
    private String currFbid;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);


//        progressBar = new ProgressDialog(getActivity());
//        progressBar.setMessage("Loading Question!");
//        progressBar.setCanceledOnTouchOutside(false);
//        progressBar.show();


        queURL = getActivity().getResources().getString(R.string.xunbao_get_question_api);
        ansURL = getActivity().getResources().getString(R.string.xunbao_check_answer_api);
        statusURL = getActivity().getResources().getString(R.string.xunbao_status);
        queLayout = view.findViewById(R.id.question_layout);
        stage = view.findViewById(R.id.tv_question_number);
        question = view.findViewById(R.id.tv_question_text);
        xunbaoimg = view.findViewById(R.id.iv_xunbao_question_image);
        ans = view.findViewById(R.id.et_xunbao_answer);
        submit = view.findViewById(R.id.ll_submit);
        contestEnd = view.findViewById(R.id.contest_ends);
        refreshButton = view.findViewById(R.id.refresh_button);
        refreshText = view.findViewById(R.id.refresh_text);
        queue = Volley.newRequestQueue(getActivity());

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

                //progressBar.show();
                submitAnswer();
            }
        });

        return view;

    }

    private void submitAnswer() {
        JSONObject answer = new JSONObject();

        try {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            answer.put("email", currFbid);
            answer.put("skey", "abbv");
            answer.put("ans", ans.getText());

            final JsonObjectRequest answ = new JsonObjectRequest(Request.Method.POST, ansURL, answer,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject resp) {
                            try {

                                //progressBar.dismiss();

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
                                //progressBar.dismiss();
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //progressBar.dismiss();
                            Toast.makeText(getActivity(), "Problem submitting answer!", Toast.LENGTH_SHORT).show();
                            volleyError.printStackTrace();
                        }
                    });

            queue.add(answ);

        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Problem submitting answer!", Toast.LENGTH_SHORT).show();
            //progressBar.dismiss();
        }

    }

    public void reload() {
        //progressBar.show();
        queLayout.setVisibility(View.GONE);
        contestEnd.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
        refreshText.setVisibility(View.GONE);
        queue.add(stat);
    }

    public void checkStatus() {
        stat = new StringRequest(Request.Method.GET, statusURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        xstatus = Integer.parseInt(response);
                        Log.d("hey", "" + xstatus);
                        if (xstatus == 2) {
                            //progressBar.dismiss();
                            contestEnd.setText("KEEP CALM! CONTEST YET TO START");
                            contestEnd.setVisibility(View.VISIBLE);
                        } else if (xstatus == 1) {
                            if(!currFbid.equals("notLoggedIn")) {
                                queue.add(jobReq);
                                refreshText.setVisibility(View.GONE);
                            }

                        } else if (xstatus == 3) {
                            //progressBar.dismiss();
                            contestEnd.setText("THE CONTEST IS OVER! THANKS FOR PLAYING. IF YOU HAVE WON, WE WILL CONTACT YOU SHORTLY");
                            contestEnd.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hey", "" + error);
                //progressBar.dismiss();
                refreshButton.setVisibility(View.VISIBLE);
                refreshText.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Problem loading!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stat);
    }

    public void getQuestion() {
        JSONArray jsonArray = new JSONArray();
        JSONObject params = new JSONObject();
        try {
//            if (!currFbid.equals("notLoggedIn"))
                params.put("email", currFbid);
            params.put("skey", "abbv");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(params);

        jobReq = new JsonArrayRequest(Request.Method.POST, queURL, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject resp;
                        try {
                            resp = response.getJSONObject(0);
                            try {
                                String end = resp.getString("response");
                                contestEnd.setVisibility(View.VISIBLE);
                                contestEnd.setText("YOU HAVE SUCCESSFULLY COMPLETED ALL THE QUESTIONS.\n WE WILL ANNOUNCE THE WINNERS ON 31st March, 2018.\nIF YOU HAVE WON, WE WILL CONTACT YOU SHORTLY");
                                //progressBar.dismiss();

                            } catch (Exception e) {
                                queLayout.setVisibility(View.VISIBLE);
                                String imgUrl = resp.getString("image");
                                String que = resp.getString("desc");
                                Integer level = resp.getInt("pk");
                                question.setText(que);
                                stage.setText("STAGE - " + Integer.toString(level));
                                float density = getResources().getDisplayMetrics().density;
                                float size = (question.getMeasuredHeight() + stage.getMeasuredHeight()) / density + 50;
                                Picasso.with(getActivity()).load("https://xunbao-1.herokuapp.com" + imgUrl).into(xunbaoimg);
                                //progressBar.dismiss();
                            }
                        } catch (JSONException e) {
                            //progressBar.dismiss();
                            refreshButton.setVisibility(View.VISIBLE);
                            refreshText.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //progressBar.dismiss();
                        refreshButton.setVisibility(View.VISIBLE);
                        refreshText.setVisibility(View.VISIBLE);

                        volleyError.printStackTrace();
                    }
                });
        queue.add(stat);

    }


    @Override
    public void makeQuestionVisible(String fbId) {
        currFbid = fbId;
        Log.d("xunbao", currFbid);
        checkStatus();
        getQuestion();
    }
}
