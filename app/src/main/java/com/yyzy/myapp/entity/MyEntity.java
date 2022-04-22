package com.yyzy.myapp.entity;

import java.io.Serializable;

public class MyEntity implements Serializable {
    private String titleImg;
    private String titleName;
    private String titleAuthor;
    private int readCount;
    private int likeCount;
    private int commentCount;
    private int enjoyCount;

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleAuthor() {
        return titleAuthor;
    }

    public void setTitleAuthor(String titleAuthor) {
        this.titleAuthor = titleAuthor;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getEnjoyCount() {
        return enjoyCount;
    }

    public void setEnjoyCount(int enjoyCount) {
        this.enjoyCount = enjoyCount;
    }
}
