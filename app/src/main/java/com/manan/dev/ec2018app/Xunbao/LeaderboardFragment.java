package com.manan.dev.ec2018app.Xunbao;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LeaderboardFragment extends Fragment {

    static LeaderboardAdapter leaderboardAdapter;
    static RecyclerView recyclerView;
    static Context c;
    static ProgressDialog progressBar;
    static TextView refreshText;
    static ImageView refreshButton;
    static RequestQueue queue;
    static StringRequest stringRequest;
    public LeaderboardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        c=getActivity();
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("(Not you)");
        progressBar.setTitle("Checking");
        progressBar.setCanceledOnTouchOutside(false);

        refreshButton=view.findViewById(R.id.refresh_button);
        refreshText=view.findViewById(R.id.refresh_text);

        refreshButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reload();
                    }
                }
        );
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("heyt","resume");
    }

    public void setData(){

        progressBar.show();
        queue = Volley.newRequestQueue(c);
        String url = c.getResources().getString(R.string.xunbao_leaderboard_api);
        url="https://good-people.herokuapp.com/leaderboard_api";
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    List<LeaderboardList> leaderboardList=new ArrayList<>();
                    @Override
                    public void onResponse(String response) {
                        recyclerView.setVisibility(View.VISIBLE);
                        try {
                            JSONArray k = new JSONArray(response);
                            for (int i=0;i<k.length();i++) {
                                JSONObject k1 = k.getJSONObject(i);
                                String k2 = k1.getString("user");
                                String k3 = k1.getString("solved");
                                LeaderboardList k4 = new LeaderboardList(k2, Integer.toString(i + 1), k3);
                                leaderboardList.add(k4);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            leaderboardAdapter = new LeaderboardAdapter(c, leaderboardList);
                            recyclerView.setAdapter(leaderboardAdapter);

                        progressBar.hide();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.dismiss();
                List<LeaderboardList> leaderboardList=new ArrayList<>();

                Toast.makeText(c, "Problem Loading!", Toast.LENGTH_SHORT).show();

                    leaderboardAdapter = new LeaderboardAdapter(c, leaderboardList);
                    recyclerView.setAdapter(leaderboardAdapter);
                    refreshButton.setVisibility(View.VISIBLE);
                    refreshText.setVisibility(View.VISIBLE);
            }
        });
        queue.add(stringRequest);

    }

    public void reload(){
        progressBar.show();
        recyclerView.setVisibility(View.GONE);
        refreshText.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
        queue.add(stringRequest);
    }

}
