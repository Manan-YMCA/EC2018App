package com.manan.dev.ec2018app.Models;

/**
 * Created by Suneja's on 11-03-2018.
 */

public class Comment {
    private String uname, comment;
    private long time;
    private int imageUrl;

    public Comment() {
    }

    public Comment(String uname, String comment, long time, int imageUrl) {
        this.uname = uname;
        this.comment = comment;
        this.time = time;
        this.imageUrl = imageUrl;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
