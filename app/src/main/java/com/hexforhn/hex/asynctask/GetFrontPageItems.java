package com.hexforhn.hex.asynctask;

import android.os.AsyncTask;

import com.hexforhn.hex.HexApplication;
import com.hexforhn.hex.model.Item;
import com.hexforhn.hex.net.hexapi.FrontPageService;

import java.util.List;


public class GetFrontPageItems extends AsyncTask<Void, Integer, List<? extends Item>> {

    private final HexApplication mApplication;
    private FrontPageItemsHandler mHandler;

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
        if (mHandler == null) {
            return;
        }

        if (items == null) {
            mHandler.onItemsUnavailable();
        } else {
            mHandler.onItemsReady(items);
        }
    }

    public void removeHandler() {
        this.mHandler = null;
    }
}
