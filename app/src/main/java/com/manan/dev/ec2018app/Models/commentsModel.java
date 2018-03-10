package com.manan.dev.ec2018app.Models;

/**
 * Created by KASHISH on 10-03-2018.
 */

public class commentsModel {
    public String username,comment;
    public commentsModel(){

    }
    public commentsModel(String username,String comment) {
        this.username=username;
        this.comment=comment;
    }
    public void setcomm(String comment){
        this.comment=comment;
    }
    public void setusername(String username){
        this.username=username;
    }
}
