package uk.co.longdivision.hackernews.viewmodel;

public class ItemListItem {

    private String title;

    private String user;

    private String domain;

    private int score;

    private int commentCount;

    public ItemListItem(String title, String user, String domain, int score, int commentCount) {
        this.title = title;
        this.user = user;
        this.domain = domain;
        this.score = score;
        this.commentCount = commentCount;
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
