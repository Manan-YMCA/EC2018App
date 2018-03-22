package com.manan.dev.ec2018app.Xunbao;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manan.dev.ec2018app.Fragments.FragmentFbLogin;
import com.manan.dev.ec2018app.LoginActivity;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Utilities.ConnectivityReciever;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;


public class QuestionFragment extends Fragment implements XunbaoActivity.loadQuestionFragment, ConnectivityReciever.ConnectivityReceiverListener {
    TextView question, contestEnd, refreshText, stage, loginText;
    ImageView xunbaoimg, refreshButton;
    LinearLayout submit;
    EditText ans;
    String queURL, ansURL, statusURL;
    StringRequest stat;
    JsonArrayRequest jobReq;
    RequestQueue queue;
    RelativeLayout queLayout;
    private ProgressBar bar, barImage;
    //ProgressDialog progressBar;
    int xstatus = 2;
    private String currFbid;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);

        mAuth = FirebaseAuth.getInstance();

        bar = (ProgressBar) view.findViewById(R.id.pb_question);
        bar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.pb_xunbao), android.graphics.PorterDuff.Mode.MULTIPLY);
        barImage = (ProgressBar) view.findViewById(R.id.pb_image);
        barImage.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.pb_xunbao), android.graphics.PorterDuff.Mode.MULTIPLY);
        bar.setVisibility(View.VISIBLE);
        barImage.setVisibility(View.GONE);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginText = (TextView) view.findViewById(R.id.tv_log_in);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), LoginActivity.class);
                in.putExtra("parent", "xunbao");
                startActivity(in);
            }
        });

        final String EMAIL = "email";

        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                FragmentFbLogin.fbLoginButton activity = (FragmentFbLogin.fbLoginButton) getActivity();
                handleFacebookAccessToken(loginResult.getAccessToken());
                activity.fbStatus(true, accessToken.getUserId());
                Toast.makeText(getActivity(), "Facebook Login Done!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(), "Facebook login cancelled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("TAG", "onError: " + exception.getMessage() );
            }
        });

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

                bar.setVisibility(View.VISIBLE);
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
            answer.put("skey",getActivity().getResources().getString(R.string.skey));
            answer.put("ans", ans.getText());

            final JsonObjectRequest answ = new JsonObjectRequest(Request.Method.POST, ansURL, answer,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject resp) {
                            bar.setVisibility(View.GONE);
                            try {
                                //progressBar.dismiss();
                                String end = resp.getString("response");
                                contestEnd.setVisibility(View.VISIBLE);
                                if(end.equals("1")) {
                                    MDToast.makeText(getActivity(), "Congrats! Right answer!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                    reload();
                                }
                                else
                                    MDToast.makeText(getActivity(), "Wrong answer!", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();

                            } catch (JSONException e) {

                                MDToast.makeText(getActivity(), "Problem submitting answer!", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                                //progressBar.dismiss();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            bar.setVisibility(View.GONE);
                            //progressBar.dismiss();
                            MDToast.makeText(getActivity(), "Problem submitting answer!", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                            volleyError.printStackTrace();
                        }
                    });
            queue.add(answ);
        } catch (JSONException e) {
            bar.setVisibility(View.GONE);
            MDToast.makeText(getActivity(), "Problem submitting answer!", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            //progressBar.dismiss();
        }
    }

    public void reload() {
        //progressBar.show();
        bar.setVisibility(View.VISIBLE);
        queLayout.setVisibility(View.GONE);
        contestEnd.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
        refreshText.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        loginText.setVisibility(View.GONE);
        queue.add(stat);
    }

    public void checkStatus() {
        stat = new StringRequest(Request.Method.GET, statusURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        bar.setVisibility(View.GONE);
                        refreshButton.setVisibility(View.GONE);
                        xstatus = Integer.parseInt(response);
                        Log.d("hey", "" + xstatus);
                        if (xstatus == 2) {
                            //progressBar.dismiss();
                            contestEnd.setText("KEEP CALM! CONTEST YET TO START!");
                            contestEnd.setVisibility(View.VISIBLE);
                        } else if (xstatus == 1) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                bar.setVisibility(View.VISIBLE);
                                queue.add(jobReq);
                                refreshText.setVisibility(View.GONE);
                                loginButton.setVisibility(View.GONE);
                                loginText.setVisibility(View.GONE);
                            } else {
                                refreshText.setVisibility(View.VISIBLE);
                                SharedPreferences prefs = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
                                String phoneNumber = prefs.getString("Phone", null);
                                if(phoneNumber == null){
                                    loginText.setVisibility(View.VISIBLE);
                                } else {
                                    loginButton.setVisibility(View.VISIBLE);
                                }
                            }
                        } else if (xstatus == 3) {
                            //progressBar.dismiss();
                            contestEnd.setText("THE CONTEST IS OVER! THANKS FOR PLAYING. IF YOU HAVE WON, WE WILL CONTACT YOU SHORTLY.");
                            contestEnd.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bar.setVisibility(View.GONE);
                Log.d("hey", "" + error);
                //progressBar.dismiss();
                refreshButton.setVisibility(View.VISIBLE);
                MDToast.makeText(getActivity(), "Problem loading!", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            }
        });
        queue.add(stat);
    }

    public void getQuestion() {
        JSONArray jsonArray = new JSONArray();
        JSONObject params = new JSONObject();
        try {
            params.put("fid", currFbid);
            if(Profile.getCurrentProfile()!=null) {
                params.put("skey", getActivity().getResources().getString(R.string.skey));
                params.put("fname",Profile.getCurrentProfile().getFirstName());
                params.put("lname",Profile.getCurrentProfile().getLastName());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(params);

        jobReq = new JsonArrayRequest(Request.Method.POST, queURL, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        bar.setVisibility(View.GONE);
                        JSONObject resp;
                        refreshButton.setVisibility(View.GONE);
                        try {
                            resp = response.getJSONObject(0);
                            try {
                                String end = resp.getString("response");
                                contestEnd.setVisibility(View.VISIBLE);
                                contestEnd.setText("YOU HAVE SUCCESSFULLY COMPLETED ALL THE QUESTIONS.\n WE WILL ANNOUNCE THE WINNERS ON 7th APRIL, 2018.\nIF YOU HAVE WON, WE WILL CONTACT YOU SHORTLY");
                                //progressBar.dismiss();
                            } catch (Exception e) {
                                bar.setVisibility(View.GONE);
                                barImage.setVisibility(View.VISIBLE);
                                queLayout.setVisibility(View.VISIBLE);
                                String imgUrl = resp.getString("image");
                                String que = resp.getString("desc");
                                Integer level = resp.getInt("pk");
                                question.setText(que);
                                stage.setText("STAGE - " + Integer.toString(level));

                                Picasso.with(getActivity()).load("https://xunbao-1.herokuapp.com" + imgUrl).into(xunbaoimg, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        barImage.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError() {
                                        barImage.setVisibility(View.GONE);
                                    }
                                });
                                //progressBar.dismiss();
                            }
                        } catch (JSONException e) {
                            bar.setVisibility(View.GONE);
                            //progressBar.dismiss();
                            refreshButton.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //progressBar.dismiss();

                        bar.setVisibility(View.GONE);
                        refreshButton.setVisibility(View.VISIBLE);

                        volleyError.printStackTrace();
                    }
                });
        queue.add(stat);
    }


    @Override
    public void makeQuestionVisible(String fbId) {
        currFbid = fbId;
        Log.d("xunbao", currFbid);

        if(isNetworkAvailable()) {
            checkStatus();
            if (!currFbid.equals("notLoggedIn"))
                getQuestion();
        } else {
            MDToast.makeText(getActivity().getApplicationContext(), "Connect to Internet", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("loginStatus", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("loginStatus", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            checkStatus();
            if (!currFbid.equals("notLoggedIn"))
                getQuestion();
        } else {
            MDToast.makeText(getActivity().getApplicationContext(), "Connect to Internet", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
