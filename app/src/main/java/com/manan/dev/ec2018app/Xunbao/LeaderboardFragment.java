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
    static RequestQueue queue;
    static StringRequest stringRequest;
    public LeaderboardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        c=getActivity();
        setData();

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


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                List<LeaderboardList> leaderboardList=new ArrayList<>();

                Toast.makeText(c, "Problem Loading!", Toast.LENGTH_SHORT).show();

                    leaderboardAdapter = new LeaderboardAdapter(c, leaderboardList);
                    recyclerView.setAdapter(leaderboardAdapter);
            }
        });
        queue.add(stringRequest);

    }

    public void reload(){
        recyclerView.setVisibility(View.GONE);
        queue.add(stringRequest);
    }

}
