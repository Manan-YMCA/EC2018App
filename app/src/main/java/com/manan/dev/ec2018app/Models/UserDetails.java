package com.manan.dev.ec2018app.Models;

/**
 * Created by yatindhingra on 14/02/18.
 */

public class UserDetails {

    private String mName, Email, mCollege, mFbId;
    private long mPhone;

    public UserDetails() {
    }

    public UserDetails(String mName, String email, String mCollege, String mFbId, long mPhone) {
        this.mName = mName;
        Email = email;
        this.mCollege = mCollege;
        this.mFbId = mFbId;
        this.mPhone = mPhone;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getmCollege() {
        return mCollege;
    }

    public void setmCollege(String mCollege) {
        this.mCollege = mCollege;
    }

    public String getmFbId() {
        return mFbId;
    }

    public void setmFbId(String mFbId) {
        this.mFbId = mFbId;
    }

    public long getmPhone() {
        return mPhone;
    }

    public void setmPhone(long mPhone) {
        this.mPhone = mPhone;
    }
}
