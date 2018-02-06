package com.manan.dev.ec2018app.Models;

import android.graphics.Bitmap;

/**
 * Created by shubhamsharma on 04/02/18.
 */

public class CategoryItemModel {


    private String name;
    private Bitmap image;


    public CategoryItemModel() {
    }

    public CategoryItemModel(String name, Bitmap image) {
        this.name = name;
        this.image = image;
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
