package uk.co.longdivision.hackernews;

import android.app.Application;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;


public class HackerNewsApplication extends Application {

    private RequestQueue mRequestQueue;

    public void onCreate() {
        super.onCreate();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            int MEGABYTE = 1024 * 1024;
            Cache cache = new DiskBasedCache(getCacheDir(), MEGABYTE);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }

        return mRequestQueue;
    }
}
