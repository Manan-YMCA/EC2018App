package com.manan.dev.ec2018app.Models;

import com.manan.dev.ec2018app.Models.commentsModel;

import java.util.List;

/**
 * Created by KASHISH on 10-03-2018.
 */

public class postsModel{


        public String title,photoid,clubName,postid;
        public boolean approval;
        public int likes;
        public long time;
        public List<commentsModel> comments;
        public List<likesModel> likefids;
        public postsModel(){

        }
        public postsModel(String title,long time,String photoid, String clubName,boolean approval, int likes, List<commentsModel> comments) {
            this.title = title;
            this.time=time;
            this.photoid=photoid;
            this.clubName=clubName;
            this.approval=approval;
            this.likes=likes;
            this.comments=comments;
        }
    public void setapproval(boolean approval){
        this.approval=approval;
    }
    public boolean getApproval(){
        return approval;
    }
    public void settime(long approval){
        this.time=approval;
    }
    public long gettime(){
        return time;
    }
    public List<commentsModel> getcomments(){
        return comments;
    }

    public void settitle(String title){
        this.title=title;
    }
    public String gettitle(){
        return title;
    }
    public String getClubName(){
        return clubName;
    }
    public void setlikes(int likes){
        this.likes=likes;
    }
    public int getlikes(){
        return likes;
    }

    public void setphotoid(String url){
        this.photoid=photoid;
    }
    public String getphotoid(){
        return photoid;
    }
}
