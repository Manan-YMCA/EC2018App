package com.manan.dev.ec2018app.Models;

/**
 * Created by yatindhingra on 14/02/18.
 */

public class Coordinators {

    private String mCoordName, mCoordId;
    private long mCoordPhone;

    public Coordinators() {
    }

    public Coordinators(String mCoordName, String mCoordId, long mCoordPhone) {
        this.mCoordName = mCoordName;
        this.mCoordId = mCoordId;
        this.mCoordPhone = mCoordPhone;
    }

    public String getmCoordName() {
        return mCoordName;
    }

    public void setmCoordName(String mCoordName) {
        this.mCoordName = mCoordName;
    }

    public String getmCoordId() {
        return mCoordId;
    }

    public void setmCoordId(String mCoordId) {
        this.mCoordId = mCoordId;
    }

    public long getmCoordPhone() {
        return mCoordPhone;
    }

    public void setmCoordPhone(long mCoordPhone) {
        this.mCoordPhone = mCoordPhone;
    }
}
