package com.hexforhn.hex.asynctask;

import android.os.AsyncTask;

import com.hexforhn.hex.HexApplication;
import com.hexforhn.hex.model.Item;
import com.hexforhn.hex.net.hexapi.ItemService;


public class GetItem extends AsyncTask<String, Integer, Item> {

    private ItemHandler mHandler;
    private final HexApplication mApplication;

    public GetItem(ItemHandler handler, HexApplication application) {
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
        if (mHandler == null) { return; }

        if (item == null) {
            mHandler.onItemUnavailable();
        } else {
            mHandler.onItemReady(item);
        }
    }

    public void removeHandler() {
        mHandler = null;
    }
}
