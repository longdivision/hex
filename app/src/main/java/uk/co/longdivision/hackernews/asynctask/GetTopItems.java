package uk.co.longdivision.hackernews.asynctask;

import android.os.AsyncTask;

import java.util.ArrayList;

import uk.co.longdivision.hackernews.model.Item;
import uk.co.longdivision.hackernews.model.Story;


public class GetTopItems extends AsyncTask<Void, Integer, ArrayList<Item>> {

    private TopItemsHandler handler;

    public GetTopItems(TopItemsHandler handler) {
        super();
        this.handler = handler;
    }

    @Override
    protected ArrayList<Item> doInBackground(Void... params) {
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Story(
                "Is Your VirtualBox Reading Your E-Mail? Reconstruction of FrameBuffers from VRAM",
                "foobarz", "exampledomain.com", 92, 15
        ));
        items.add(new Story(
                "“So many people spend their working lives doing jobs they think are unnecessary” ",
                "foobarz", "exampledomain.com", 106, 89
        ));
        items.add(new Story(
                "Super Fuzzy Searching on PostgreSQL",
                "foobarz", "bartlettpublishing.com", 36, 2
        ));
        items.add(new Story(
                "Ask HN: Former freelance client screwed me. Having trouble moving on. Advice?",
                "foobarz", "exampledomain.com", 16, 25
        ));
        items.add(new Story(
                "Ravens Offensive Lineman Publishes Math Paper",
                "foobarz", "npr.org", 432, 110
        ));
        items.add(new Story(
                "Goat Simulator Post Mortem",
                "foobarz", "exampledomain.com", 235, 68
        ));
        items.add(new Story(
                "Java is Pass-by-Value, Dammit",
                "foobarz", "exampledomain.com", 156, 90
        ));
        items.add(new Story(
                "In College and Hiding from Scary Ideas ",
                "foobarz", "exampledomain.com", 121, 54
        ));

        return items;
    }

    @Override
    public void onPostExecute(ArrayList<Item> items) {
        handler.onTopItemsReady(items);
    }
}
