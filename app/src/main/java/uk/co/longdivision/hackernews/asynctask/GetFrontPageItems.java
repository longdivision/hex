package uk.co.longdivision.hackernews.asynctask;

import android.os.AsyncTask;

import java.util.List;

import uk.co.longdivision.hackernews.HackerNewsApplication;
import uk.co.longdivision.hackernews.model.Item;
import uk.co.longdivision.hackernews.net.hackernewsapi.FrontPageService;


public class GetFrontPageItems extends AsyncTask<Void, Integer, List<? extends Item>> {

    private final HackerNewsApplication mApplication;
    private final FrontPageItemsHandler mHandler;

    public GetFrontPageItems(FrontPageItemsHandler handler, HackerNewsApplication application) {
        super();
        this.mApplication = application;
        this.mHandler = handler;
    }

    @Override
    protected List<? extends Item> doInBackground(Void... params) {
        return (new FrontPageService(mApplication.getRequestQueue())).getTopItems();
    }

    @Override
    public void onPostExecute(List<? extends Item> items) {
        mHandler.onFrontPageItemsReady(items);
    }
}
