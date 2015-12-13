package uk.co.longdivision.hex.model;

import java.util.List;


public class Comment {

    private String mText;
    private String mUser;
    private List<Comment> mChildComments;

    public Comment(String text, String user, List<Comment> childComments) {
        this.mText = text;
        this.mUser = user;
        this.mChildComments = childComments;
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
}
