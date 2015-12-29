package uk.co.longdivision.hex.model;

import java.util.Date;
import java.util.List;


public class Comment {

    private final int mCommentCount;
    private final String mText;
    private final String mUser;
    private final List<Comment> mChildComments;
    private final Date mDate;

    public Comment(String text, String user, List<Comment> childComments, int commentCount,
                   Date date) {
        this.mText = text;
        this.mUser = user;
        this.mChildComments = childComments;
        this.mCommentCount = commentCount;
        this.mDate = date;
    }

    public String getText(){
        return this.mText;
    }

    public String getUser() {
        return this.mUser;
    }

    public List<Comment> getChildComments() {
        return this.mChildComments;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public Date getDate() {
        return this.mDate;
    }
}
