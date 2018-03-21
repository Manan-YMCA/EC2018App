package com.manan.dev.ec2018app.Xunbao;

/**
 * Created by KASHISH on 04-02-2018.
 */

public class LeaderboardList {
    private String name1, rank, level,fid;

    public LeaderboardList() {
    }

    public LeaderboardList(String name1, String rank, String level,String fid) {
        this.name1 = name1;
        this.rank = rank;
        this.level = level;
        this.fid=fid;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
