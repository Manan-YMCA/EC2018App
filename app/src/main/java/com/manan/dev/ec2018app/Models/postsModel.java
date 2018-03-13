package com.manan.dev.ec2018app.Models;

import com.manan.dev.ec2018app.Models.commentsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KASHISH on 10-03-2018.
 */

public class postsModel{


        public String title;
    public String photoid;
    public String clubName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<likesModel> getLikefids() {
        return likefids;
    }

    public void setLikefids(ArrayList<likesModel> likefids) {
        this.likefids = likefids;
    }

    public String postid;
        public boolean approval;
        public int likes;
        public long time;
        public ArrayList<Comment> comments;
        public ArrayList<likesModel> likefids;
        public postsModel(){

        }
        public postsModel(String title,long time,String photoid, String clubName,boolean approval, int likes, ArrayList<Comment> comments) {
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
    public ArrayList<Comment> getcomments(){
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
