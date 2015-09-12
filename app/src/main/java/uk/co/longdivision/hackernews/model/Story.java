package uk.co.longdivision.hackernews.model;

import java.util.ArrayList;

public class Story implements Item{

    private String id;

    private String title;

    private String user;

    private String domain;

    private int score;

    private int commentCount;

    private ArrayList<Comment> comments;

    public Story(String title, String user, String domain, int score, int commentCount) {
        this.title = title;
        this.user = user;
        this.domain = domain;
        this.score = score;
        this.commentCount = commentCount;
        this.comments = new ArrayList<>();
    }

    public Story(String title, String user, String domain, int score, int commentCount,
                 ArrayList<Comment> comments) {
        this.title = title;
        this.user = user;
        this.domain = domain;
        this.score = score;
        this.commentCount = commentCount;
        this.comments = comments;
    }

    public String getId() {
        return "1";
    }

    public String getTitle() {
        return this.title;
    }

    public String getUser() {
        return this.user;
    }

    public String getDomain() {
        return this.domain;
    }

    public int getScore() {
        return this.score;
    }

    public int getCommentCount() {
        return this.commentCount;
    }
}
