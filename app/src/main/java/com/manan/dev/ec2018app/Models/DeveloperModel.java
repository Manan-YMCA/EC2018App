package com.manan.dev.ec2018app.Models;

/**
 * Created by subham on 13/03/18.
 */

public class DeveloperModel {
   private String photoUrl;
    private String name,linkedDUrl,more;
    private Boolean isCreative,isDevloper,isOperation,isDesign;

    public DeveloperModel()
    {
    }
    public DeveloperModel(String photoUrl, String name, String linkedDUrl, String more, Boolean isDevloper, Boolean isDesign, Boolean isCreative, Boolean isOperation)
    {
        this.isCreative=isCreative;
        this.isDesign=isDesign;
        this.isDevloper=isDevloper;
        this.isOperation=isOperation;
        this.photoUrl=photoUrl;
        this.linkedDUrl=linkedDUrl;
        this.more=more;
        this.name=name;
    }

    public void setCreative(Boolean creative) {
        isCreative = creative;
    }

    public void setDesign(Boolean design) {
        isDesign = design;
    }

    public void setDevloper(Boolean devloper) {
        isDevloper = devloper;
    }

    public void setLinkedDUrl(String linkedDUrl) {
        this.linkedDUrl = linkedDUrl;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOperation(Boolean operation) {
        isOperation = operation;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getLinkedDUrl() {
        return linkedDUrl;
    }



    public Boolean getCreative() {
        return isCreative;
    }

    public Boolean getDesign() {
        return isDesign;
    }

    public Boolean getDevloper() {
        return isDevloper;
    }

    public Boolean getOperation() {
        return isOperation;
    }

    public String getMore() {
        return more;
    }

    public String getName() {
        return name;
    }

}
