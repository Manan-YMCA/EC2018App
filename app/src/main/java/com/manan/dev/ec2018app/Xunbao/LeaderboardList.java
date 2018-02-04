package com.manan.dev.ec2018app.Xunbao;

/**
 * Created by KASHISH on 04-02-2018.
 */

public class LeaderboardList {
    public String name1,rank,level;
    public LeaderboardList(String name1,String rank,String level) {
        this.name1=name1;
        this.rank=rank;
        this.level=level;
    }
    public String getName1() {
        return name1;
    }
    public String getRank(){
        return rank;
    }
    public String getLevel(){
        return level;
    }
}
