package com.manan.dev.ec2018app.Models;

/**
 * Created by subham on 12/03/18.
 */

public class WhatsNewModel {

    private String content;
    private int intent;
    private String photourl;

    public WhatsNewModel()
    {

    }
    public WhatsNewModel(String content,int intent,String url)
    {this.content=content;
    this.intent=intent;
    this.photourl=url;
    }

public String getContent()
{
    return content;
}
public int getIntent()
{
    return intent;
}
public String getPhotourl()
{
    return photourl;
}

}



