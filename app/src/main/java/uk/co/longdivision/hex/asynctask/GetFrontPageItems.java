package uk.co.longdivision.hex.asynctask;

import android.os.AsyncTask;

import java.util.List;

import uk.co.longdivision.hex.HexApplication;
import uk.co.longdivision.hex.model.Item;
import uk.co.longdivision.hex.net.hackernewsapi.FrontPageService;


public class GetFrontPageItems extends AsyncTask<Void, Integer, List<? extends Item>> {

    private final HexApplication mApplication;
    private final FrontPageItemsHandler mHandler;

    public GetFrontPageItems(FrontPageItemsHandler handler, HexApplication application) {
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
