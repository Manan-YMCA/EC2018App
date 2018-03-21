package com.manan.dev.ec2018app.Models;

import android.support.annotation.NonNull;

/**
 * Created by Shubham on 3/18/2018.
 */

public class NotificationModel implements Comparable<NotificationModel>{
    String textHeading,text;
    long time;

    public NotificationModel() {}

    public NotificationModel(String textHeading, String text, long time) {
        this.textHeading = textHeading;
        this.text = text;
        this.time = time;
    }

    public String getTextHeading() {
        return textHeading;
    }

    public void setTextHeading(String textHeading) {
        this.textHeading = textHeading;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int compareTo(@NonNull NotificationModel o) {
        if(this.getTime() < o.getTime())
            return 1;
        else
            return -1;
    }
}
