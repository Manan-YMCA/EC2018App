package com.manan.dev.ec2018app.Xunbao;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manan.dev.ec2018app.R;

import java.util.ArrayList;
import java.util.List;


public class LeaderboardFragment extends Fragment {

    private List<LeaderboardList> leaderboardList = new ArrayList<>();
    RecyclerView recyclerView;
    LeaderboardAdapter leaderboardAdapter;

    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LeaderboardList dummy = new LeaderboardList("KASHISH", "1.", "10");
        leaderboardList.add(dummy);
        dummy = new LeaderboardList("K", "2.", "9");
        leaderboardList.add(dummy);
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        leaderboardAdapter = new LeaderboardAdapter(getContext(), leaderboardList);
        recyclerView.setAdapter(leaderboardAdapter);
        return view;
    }

}
