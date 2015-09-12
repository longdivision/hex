package uk.co.longdivision.hackernews.asynctask;

import android.os.AsyncTask;

import java.util.ArrayList;

import uk.co.longdivision.hackernews.model.Comment;
import uk.co.longdivision.hackernews.model.Item;
import uk.co.longdivision.hackernews.model.Story;


public class GetItem extends AsyncTask<Void, Integer, Item> {

    private ItemHandler handler;

    public GetItem(ItemHandler handler) {
        super();
        this.handler = handler;
    }

    @Override
    protected Item doInBackground(Void... params) {
        Comment childA = new Comment("I am comment.","Foobar", 1);
        Comment childB = new Comment("I am comment.", "Foobar", 1);
        ArrayList<Comment> childComments = new ArrayList<>();
        childComments.add(childA);
        childComments.add(childB);

        return new Story("Story", "username", "example.com", 451, 123, childComments);
    }

    @Override
    public void onPostExecute(Item item) {
        handler.onItemReady(item);
    }
}
