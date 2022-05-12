package com.yyzy.myapp.entity;

import java.io.Serializable;

public class ViedeoDataEntity implements Serializable {
    private String author;
    private int collectNum;
    private int commentNum;
    private String coverUrl;
    private String headurl;
    private int likeNum;
    private String title;
    private String playUrl;
    private String createTime;
    private boolean isFlagCollect;
    private boolean isFlagLike;

    public ViedeoDataEntity(String author, int collectNum, int commentNum, String coverUrl, String headurl, int likeNum, String title) {
        this.author = author;
        this.collectNum = collectNum;
        this.commentNum = commentNum;
        this.coverUrl = coverUrl;
        this.headurl = headurl;
        this.likeNum = likeNum;
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ViedeoDataEntity() {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public boolean isFlagCollect() {
        return isFlagCollect;
    }

    public void setFlagCollect(boolean flagCollect) {
        isFlagCollect = flagCollect;
    }

    public boolean isFlagLike() {
        return isFlagLike;
    }

    public void setFlagLike(boolean flagLike) {
        isFlagLike = flagLike;
    }
}
