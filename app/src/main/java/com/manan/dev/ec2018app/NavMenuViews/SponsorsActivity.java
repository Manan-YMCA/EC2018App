package com.manan.dev.ec2018app.NavMenuViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manan.dev.ec2018app.Adapters.SponserAdapter;
import com.manan.dev.ec2018app.Models.Sponsers;
import com.manan.dev.ec2018app.R;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SponsorsActivity extends AppCompatActivity {
    private ArrayList<Sponsers> sponserList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SponserAdapter mAdapter;
    private ProgressDialog progressBar;
    private TextView noSponTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);
        sponserList = new ArrayList<>();

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading Sponsors...");
//        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

        noSponTV = findViewById(R.id.tv_no_spon);
        noSponTV.setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SponsorsActivity.this));
        mAdapter = new SponserAdapter(SponsorsActivity.this,sponserList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        findViewById(R.id.spon_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (isNetworkAvailable()) {
            RequestQueue queue = Volley.newRequestQueue(SponsorsActivity.this);
            String url = "https://elementsculmyca2018.herokuapp.com/api/v1/sponsors/getsponsors";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        Log.d("Errorrrrrrrrrrrrrrrrrr", response.toString());
                        JSONArray sponsersArray = object.getJSONArray("data");
                        for (int i = 0; i < sponsersArray.length(); i++) {
                            Sponsers sponsers = new Sponsers();
                            JSONObject currEvent = sponsersArray.getJSONObject(i);

                            if (currEvent.has("name")) {
                                sponsers.setSname(currEvent.getString("name"));
                                Log.d("Errorrrrrrrrrrrrrrrrrr",sponsers.getSname());
                            }
                            if (currEvent.has("title"))
                                sponsers.setTitle(currEvent.getString("title"));
                            if (currEvent.has("_id"))
                                sponsers.setId(currEvent.getString("_id"));
                            if (currEvent.has("rank"))
                                sponsers.setRank(currEvent.getInt("rank"));
                            if (currEvent.has("logo"))
                                sponsers.setImageUrl(currEvent.getString("logo"));
                            if (currEvent.has("url"))
                                sponsers.setUrl(currEvent.getString("url"));
                            sponserList.add(sponsers);
                            progressBar.dismiss();
                            Log.d("Errorrrrrrrrrrrrrr", sponsers.getSname());
                        }

                        mAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Log.d("Errorrrrrrrrrrrrrr",e.getMessage() );
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.dismiss();
                    Log.e("TAG", "onErrorResponse: " + error.getLocalizedMessage() );
                    noSponTV.setVisibility(View.VISIBLE);
                }
            });
            queue.add(stringRequest);
        }
        else{
            progressBar.dismiss();
            noSponTV.setVisibility(View.VISIBLE);
            MDToast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
