package com.manan.dev.ec2018app.Models;

/**
 * Created by subham on 13/03/18.
 */

public class DeveloperModel {
    private String photoUrl;
    private String name, linkedDUrl, more,githubURL ;

    public DeveloperModel() {
    }

    public DeveloperModel(String photoUrl, String name, String linkedDUrl, String more, String githubURL) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.linkedDUrl = linkedDUrl;
        this.more = more;
        this.githubURL = githubURL;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkedDUrl() {
        return linkedDUrl;
    }

    public void setLinkedDUrl(String linkedDUrl) {
        this.linkedDUrl = linkedDUrl;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }
}
