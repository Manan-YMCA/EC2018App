package com.manan.dev.ec2018app.Xunbao;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.manan.dev.ec2018app.R;

import java.util.List;

/**
 * Created by KASHISH on 04-02-2018.
 */

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    private List<LeaderboardList> leaderboardList;
    private Context context;

    public LeaderboardAdapter(Context context, List<LeaderboardList> ll) {
        this.context = context;
        this.leaderboardList = ll;
    }

    @Override
    public LeaderboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_leaderboard, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeaderboardAdapter.MyViewHolder holder, int position) {
        LeaderboardList member = leaderboardList.get(position);

        holder.rank.setText(member.getRank()+".");
        holder.name1.setText(member.getName1());
        holder.level.setText(member.getLevel());

        if (member.getRank().equals("1")) {
            holder.llBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.plank_black));
        } else {
            holder.llBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.plank_black));
        }

        if(AccessToken.getCurrentAccessToken()!=null) {
            if(member.getFid().equals(Profile.getCurrentProfile().getId())){
                holder.name1.setTextColor(Color.parseColor("#fffb00"));
                holder.level.setTextColor(Color.parseColor("#fffb00"));
                holder.rank.setTextColor(Color.parseColor("#fffb00"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return leaderboardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rank, name1, level;
        public LinearLayout llBackground;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            rank = (TextView) itemLayoutView.findViewById(R.id.Rank);
            name1 = (TextView) itemLayoutView.findViewById(R.id.name);
            level = (TextView) itemLayoutView.findViewById(R.id.level);
            llBackground = (LinearLayout) itemLayoutView.findViewById(R.id.ll_leaderboard_background);
        }
    }

}

