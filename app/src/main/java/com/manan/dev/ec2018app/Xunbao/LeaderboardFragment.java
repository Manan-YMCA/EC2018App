package com.manan.dev.ec2018app.Xunbao;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.NavMenuViews.ProfileActivity;
import com.manan.dev.ec2018app.R;
import com.manan.dev.ec2018app.Utilities.ConnectivityReciever;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LeaderboardFragment extends Fragment implements ConnectivityReciever.ConnectivityReceiverListener {

    static LeaderboardAdapter leaderboardAdapter;
    static RecyclerView recyclerView;
    static Context c;
    private ProgressBar bar;
    TextView refresh;
    static RequestQueue queue;
    static StringRequest stringRequest;
//    ImageView refreshB;
    SwipeRefreshLayout swipeRefreshLayout;
    //ProgressDialog progressBar;

    public LeaderboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        c = getActivity();
//        refreshB=view.findViewById(R.id.refresh_butto);
        refresh=view.findViewById(R.id.refresh_text);
        swipeRefreshLayout=view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
        bar = (ProgressBar) view.findViewById(R.id.pb_leaderboard);
        bar.setVisibility(View.VISIBLE);
        bar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.pb_xunbao), android.graphics.PorterDuff.Mode.MULTIPLY);
        if(isNetworkAvailable()){
            setData();
        } else {
            MDToast.makeText(getActivity().getApplicationContext(), "Connect to Internet", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
        }
//        progressBar = new ProgressDialog(getActivity());
//        progressBar.setMessage("Loading");
//        progressBar.setCanceledOnTouchOutside(false);
//        progressBar.show();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(mLayoutManager);
//        refreshB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                refreshB.setVisibility(View.GONE);
//                bar.setVisibility(View.VISIBLE);
//                reload();
//            }
//        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("heyt", "resume");
    }

    public void setData() {
        queue = Volley.newRequestQueue(c);
        String url = c.getResources().getString(R.string.xunbao_leaderboard_api);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<LeaderboardList> leaderboardList = new ArrayList<>();
                        recyclerView.setVisibility(View.VISIBLE);
                        bar.setVisibility(View.GONE);
                        //progressBar.dismiss();
                        try {
                            JSONArray k = new JSONArray(response);
                            for (int i = 0; i < k.length(); i++) {
                                JSONObject k1 = k.getJSONObject(i);
                                JSONObject k2 = k1.getJSONObject("user");
                                String fname=k2.getString("first_name");
                                String lname=k2.getString("last_name");
                                String fid=k2.getString("username");
                                String k3 = k1.getString("solved");
                                LeaderboardList k4 = new LeaderboardList(fname+" "+lname, Integer.toString(i+1), Integer.toString(Integer.parseInt(k3)-1),fid);
                                leaderboardList.add(k4);
                            }
                            swipeRefreshLayout.setRefreshing(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            refresh.setVisibility(View.VISIBLE);
//                            refreshB.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        leaderboardAdapter = new LeaderboardAdapter(c, leaderboardList);
                        recyclerView.setAdapter(leaderboardAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //progressBar.dismiss();
                List<LeaderboardList> leaderboardList = new ArrayList<>();
                bar.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);

//                refreshB.setVisibility(View.VISIBLE);
                Toast.makeText(c, "Problem Loading!", Toast.LENGTH_SHORT).show();

                leaderboardAdapter = new LeaderboardAdapter(c, leaderboardList);
                recyclerView.setAdapter(leaderboardAdapter);
            }
        });
        queue.add(stringRequest);
    }

    public void reload() {
        recyclerView.setVisibility(View.GONE);
        refresh.setVisibility(View.GONE);
        queue.add(stringRequest);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            setData();
        } else {
            MDToast.makeText(getActivity().getApplicationContext(), "Connect to Internet", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
        }
    }
}
