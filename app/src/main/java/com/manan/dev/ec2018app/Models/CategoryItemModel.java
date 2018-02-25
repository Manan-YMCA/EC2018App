package com.manan.dev.ec2018app.Models;

import android.graphics.Bitmap;

/**
 * Created by shubhamsharma on 04/02/18.
 */

public class CategoryItemModel {


    private String displayName, clubName;
    private Bitmap image;


    public CategoryItemModel() {
    }

    public CategoryItemModel(String displayName, String clubName, Bitmap image) {
        this.displayName = displayName;
        this.clubName = clubName;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
