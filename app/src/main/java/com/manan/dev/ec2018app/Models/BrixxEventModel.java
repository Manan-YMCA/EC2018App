package com.manan.dev.ec2018app.Models;

/**
 * Created by subham on 12/03/18.
 */

public class BrixxEventModel {

    private String content, fblink, photourl, instalink,title;
    private String date;

    public BrixxEventModel() {

    }

    public BrixxEventModel(String content, String fblink, String photourl, String instalink, String title, String date) {
        this.content = content;
        this.fblink = fblink;
        this.photourl = photourl;
        this.instalink = instalink;
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFblink() {
        return fblink;
    }

    public void setFblink(String fblink) {
        this.fblink = fblink;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getInstalink() {
        return instalink;
    }

    public void setInstalink(String instalink) {
        this.instalink = instalink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}