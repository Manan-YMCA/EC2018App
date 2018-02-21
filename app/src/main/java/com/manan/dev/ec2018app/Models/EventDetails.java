package com.manan.dev.ec2018app.Models;

import java.util.ArrayList;

/**
 * Created by yatindhingra on 14/02/18.
 */

public class EventDetails {

    private String mName, mClubname, mCategory, mDesc, mRules, mVenue, mPhotoUrl, mEventId, mEventTeamSize;
    private long mStartTime, mEndTime, mFees;
    private ArrayList<Coordinators> mCoordinators;
    private ArrayList<String> mPrizes;

    public EventDetails() {
    }

    public EventDetails(String mName, String mClubname, String mCategory, String mDesc, String mRules, String mVenue, String mPhotoUrl, String mEventId, String mEventSize, long mStartTime, long mEndTime, long mFees, ArrayList<Coordinators> mCoordinators, ArrayList<String> mPrizes) {
        this.mName = mName;
        this.mClubname = mClubname;
        this.mCategory = mCategory;
        this.mDesc = mDesc;
        this.mRules = mRules;
        this.mVenue = mVenue;
        this.mPhotoUrl = mPhotoUrl;
        this.mEventId = mEventId;
        this.mEventTeamSize = mEventSize;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mFees = mFees;
        this.mCoordinators = mCoordinators;
        this.mPrizes = mPrizes;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmClubname() {
        return mClubname;
    }

    public void setmClubname(String mClubname) {
        this.mClubname = mClubname;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmRules() {
        return mRules;
    }

    public void setmRules(String mRules) {
        this.mRules = mRules;
    }

    public String getmVenue() {
        return mVenue;
    }

    public void setmVenue(String mVenue) {
        this.mVenue = mVenue;
    }

    public ArrayList<String> getmPrizes() {
        return mPrizes;
    }

    public void setmPrizes(ArrayList<String> mPrizes) {
        this.mPrizes = mPrizes;
    }

    public String getmPhotoUrl() {
        return mPhotoUrl;
    }

    public void setmPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getmEventId() {
        return mEventId;
    }

    public void setmEventId(String mEventId) {
        this.mEventId = mEventId;
    }

    public long getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public long getmFees() {
        return mFees;
    }

    public void setmFees(long mFees) {
        this.mFees = mFees;
    }

    public ArrayList<Coordinators> getmCoordinators() {
        return mCoordinators;
    }

    public void setmCoordinators(ArrayList<Coordinators> mCoordinators) {
        this.mCoordinators = mCoordinators;
    }

    public String getmEventTeamSize() {
        return mEventTeamSize;
    }

    public void setmEventTeamSize(String mEventSize) {
        this.mEventTeamSize = mEventSize;
    }
}
