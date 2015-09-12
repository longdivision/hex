package uk.co.longdivision.hackernews.model;

import java.util.ArrayList;

public class Comment {

    private String text;

    private String user;

    private int score;

    private ArrayList<Comment> childComments;

    public Comment(String text, String user, int score) {
        this.text = text;
        this.user = user;
        this.score = score;
        this.childComments = new ArrayList<>();
    }

    public Comment(String text, String user, int score, ArrayList<Comment> childComments) {
        this.text = text;
        this.user = user;
        this.score = score;
        this.childComments = childComments;
    }

    public String getText(){
        return this.text;
    }

    public String getUser() {
        return this.user;
    }

    public int getScore() {
        return this.score;
    }

    public ArrayList<Comment> getChildComments() {
        return this.childComments;
    }
}
