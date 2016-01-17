package com.hexforhn.hex.viewmodel;

import android.text.format.DateUtils;

import java.util.Date;


public class CommentViewModel {

    private final int mCommentCount;
    private final String mText;
    private final String mUser;
    private final int mDepth;
    private final Date mDate;
    private Boolean mCollapsed;
    private boolean mVisible;

    public CommentViewModel(String user, String text, int depth, int commentCount, Date date) {
        this.mUser = user;
        this.mText = text;
        this.mDepth = depth;
        this.mCommentCount = commentCount;
        this.mDate = date;
        this.mVisible = true;
        this.mCollapsed = false;
    }

    public String getText() {
        return this.mText;
    }

    public String getUser() {
        return this.mUser;
    }

    public int getDepth() {
        return this.mDepth;
    }

    public int getCommentCount() {
        return this.mCommentCount;
    }

    public String getRelativeTime() {
        return DateUtils.getRelativeTimeSpanString(mDate.getTime(), System.currentTimeMillis(), 0)
                .toString();
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public boolean isCollapsed() {
        return this.mCollapsed;
    }

    public void setVisible(boolean visible) {
        this.mVisible = visible;
    }

    public void toggleCollapsed() {
        this.mCollapsed = !this.mCollapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.mCollapsed = collapsed;
    }

}
