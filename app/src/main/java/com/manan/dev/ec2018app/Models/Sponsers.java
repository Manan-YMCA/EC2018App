package com.manan.dev.ec2018app.Models;

/**
 * Created by Dell on 15-03-2018.
 */

public class Sponsers {
    private String sname, title,url,id;
    private String imageUrl;
    int rank;

    public Sponsers() {
    }

    public Sponsers(String sname, String title, String url, String id, String imageUrl, int rank) {
        this.sname = sname;
        this.title = title;
        this.url = url;
        this.id = id;
        this.imageUrl = imageUrl;
        this.rank = rank;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}