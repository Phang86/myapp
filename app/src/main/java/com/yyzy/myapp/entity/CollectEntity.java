package com.yyzy.myapp.entity;

import java.io.Serializable;

public class CollectEntity implements Serializable {

    private String newsTitle;
    private String authorName;
    private String headerUrl;
    private int commentCount;
    private String picx;
    private String picxx;
    private String picxxx;
    private String releaseDate;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPicx() {
        return picx;
    }

    public void setPicx(String picx) {
        this.picx = picx;
    }

    public String getPicxx() {
        return picxx;
    }

    public void setPicxx(String picxx) {
        this.picxx = picxx;
    }

    public String getPicxxx() {
        return picxxx;
    }

    public void setPicxxx(String picxxx) {
        this.picxxx = picxxx;
    }
}
