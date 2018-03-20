package com.manan.dev.ec2018app.Models;

/**
 * Created by KASHISH on 10-03-2018.
 */

public class postsModel {

    public String title;
    public String photoURL;
    public String clubName;
    public String postid;
    public long time;

    public postsModel() {

    }

    public postsModel(String title, String photoURL, String clubName, String postid,long time) {
        this.title = title;
        this.photoURL = photoURL;
        this.clubName = clubName;
        this.postid = postid;
        this.time = time;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getclubName() {
        return clubName;
    }

    public void setclubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public long gettime() {
        return time;
    }

    public void settime(long time) {
        this.time = time;
    }

    public String getphotoid() {
        return photoURL;
    }

    public void setphotoid(String photoid) {
        this.photoURL= photoid;
    }

}
