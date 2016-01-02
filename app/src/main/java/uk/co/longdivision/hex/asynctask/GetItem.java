package uk.co.longdivision.hex.asynctask;

import android.os.AsyncTask;

import uk.co.longdivision.hex.HexApplication;
import uk.co.longdivision.hex.model.Item;
import uk.co.longdivision.hex.net.hackernewsapi.ItemService;


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
        if (mHandler != null) {
            mHandler.onItemReady(item);
        }
    }

    public void removeHandler() {
        mHandler = null;
    }
}
