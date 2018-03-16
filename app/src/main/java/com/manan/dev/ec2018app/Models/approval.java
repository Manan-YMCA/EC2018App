package com.manan.dev.ec2018app.Models;

/**
 * Created by KASHISH on 10-03-2018.
 */

public class approval {
    public boolean app;

    public approval(){}

    public approval(boolean app){
        this.app=app;
    }
    public void setapproval(boolean app){
        this.app=app;
    }
    public boolean getApproval(){
        return app;
    }
}
