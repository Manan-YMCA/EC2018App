package com.manan.dev.ec2018app.Xunbao;

/**
 * Created by KASHISH on 04-02-2018.
 */

public class LeaderboardList {
    private String name1, rank, level;

    public LeaderboardList() {
    }

    public LeaderboardList(String name1, String rank, String level) {
        this.name1 = name1;
        this.rank = rank;
        this.level = level;
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
}
