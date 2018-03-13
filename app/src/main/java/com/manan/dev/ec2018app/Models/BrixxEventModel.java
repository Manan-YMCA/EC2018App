package com.manan.dev.ec2018app.Models;

/**
 * Created by subham on 12/03/18.
 */

public class BrixxEventModel {

    private String description,intentlink,photourl,title;

    public BrixxEventModel()
    {

    }
    public BrixxEventModel(String description, String intentlink ,String photourl,String title )
    {
        this.description=description;
        this.intentlink=intentlink;
        this.photourl=photourl;
        this.title=title;

    }

    public String getDescription() {
        return description;
    }

    public String getIntentlink() {
        return intentlink;
    }

    public String getPhotourl() {
        return photourl;
    }

    public String getTitle() {
        return title;
    }
}
