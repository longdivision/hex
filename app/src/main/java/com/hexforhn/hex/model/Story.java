package com.hexforhn.hex.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Story implements Item {

    private String mId;
    private String mTitle;
    private String mUrl;
    private String mCommentsUrl;
    private String mUser;
    private String mDomain;
    private int mScore;
    private int mCommentCount;
    private Date mDate;
    private List<Comment> mComments;

    public Story(String id, String title, String url, String commentsUrl, String user, String domain,
                 int score, int commentCount, Date date) {
        this.mId = id;
        this.mTitle = title;
        this.mUrl = url;
        this.mCommentsUrl = commentsUrl;
        this.mUser = user;
        this.mDomain = domain;
        this.mScore = score;
        this.mCommentCount = commentCount;
        this.mDate = date;
        this.mComments = new ArrayList<>();
    }

    public Story(String id, String title, String url, String commentsUrl, String user, String domain,
                 int score, int commentCount, Date date, List<Comment> comments) {
        this.mId = id;
        this.mTitle = title;
        this.mUrl = url;
        this.mCommentsUrl = commentsUrl;
        this.mUser = user;
        this.mDomain = domain;
        this.mScore = score;
        this.mCommentCount = commentCount;
        this.mDate = date;
        this.mComments = comments;
    }

    public String getId() {
        return this.mId;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getCommentsUrl() {
        return this.mCommentsUrl;
    }

    public String getUser() {
        return this.mUser;
    }

    public String getDomain() {
        return this.mDomain;
    }

    public int getScore() {
        return this.mScore;
    }

    public int getCommentCount() {
        return this.mCommentCount;
    }

    public Date getDate() {
        return this.mDate;
    }

    public List<Comment> getComments() {
        return this.mComments;
    }
}
