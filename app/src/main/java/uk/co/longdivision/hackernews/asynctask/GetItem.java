package uk.co.longdivision.hackernews.asynctask;

import android.os.AsyncTask;

import uk.co.longdivision.hackernews.HackerNewsApplication;
import uk.co.longdivision.hackernews.model.Item;
import uk.co.longdivision.hackernews.net.hackernewsapi.ItemService;


public class GetItem extends AsyncTask<String, Integer, Item> {

    private ItemHandler mHandler;
    private final HackerNewsApplication mApplication;

    public GetItem(ItemHandler handler, HackerNewsApplication application) {
        super();
        this.mApplication = application;
        this.mHandler = handler;
    }

    @Override
    protected Item doInBackground(String... params) {
        return (new ItemService(mApplication.getRequestQueue())).getItem(params[0]);
    }

    @Override
    public void onPostExecute(Item item) {
        mHandler.onItemReady(item);
    }
}
