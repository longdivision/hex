package uk.co.longdivision.hackernews.viewmodel;

import java.util.Date;

public class Comment {

    private String text;

    private String user;

    private int depth;

    private Date time;

    public Comment(String user, String text, int depth, Date time) {
        this.user = user;
        this.text = text;
        this.depth = depth;
        this.time = time;
    }

    public String getText() {
        return this.text;
    }

    public String getUser() {
        return this.user;
    }

    public int getDepth() {
        return this.depth;
    }

    public String getRelativeTime() {
        return "1 hour ago";
    }
}
