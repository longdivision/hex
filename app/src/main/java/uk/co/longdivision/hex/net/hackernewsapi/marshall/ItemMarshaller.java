package uk.co.longdivision.hex.net.hackernewsapi.marshall;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.longdivision.hex.model.Comment;
import uk.co.longdivision.hex.model.Item;
import uk.co.longdivision.hex.model.Story;

public class ItemMarshaller {

    public static Item marshall(JSONObject rawItem) {
        try {
            String id = rawItem.getString("id");
            String title = rawItem.getString("title");
            String url = rawItem.getString("url");
            String commentsUrl = rawItem.getString("commentsUrl");
            String author = rawItem.getString("author");
            String domain = rawItem.getString("domain");
            int score = rawItem.getInt("score");
            int commentCount = rawItem.getInt("commentCount");
            Date date = new DateTime(rawItem.getString("time")).toDate();

            JSONArray rawComments = rawItem.has("comments") ? rawItem.getJSONArray("comments") : null;
            List<Comment> comments =  new ArrayList<>();
            if (rawComments != null) {
                comments = marshallComments(rawComments);
            }

            return new Story(id, title, url, commentsUrl, author, domain, score, commentCount, date, comments);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Comment> marshallComments(JSONArray rawComments) {
        List<Comment> comments = new ArrayList<>();

        for (int i = 0; i < rawComments.length(); i++) {
            try {
                JSONObject rawComment = rawComments.getJSONObject(i);
                List<Comment> childComments = new ArrayList<>();
                JSONArray rawChildComments = rawComment.getJSONArray("comments");

                if (rawChildComments != null) {
                    childComments = marshallComments(rawComment.getJSONArray("comments"));
                }

                String text = rawComment.getString("text");
                String author = rawComment.getString("author");
                int commentCount = rawComment.getInt("commentCount");
                Date date = new DateTime(rawComment.getString("time")).toDate();
                comments.add(new Comment(text, author, childComments, commentCount, date));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return comments;
    }
}
