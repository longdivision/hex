package com.hexforhn.hex.asynctask;

import android.os.AsyncTask;

import com.hexforhn.hex.HexApplication;
import com.hexforhn.hex.model.Story;
import com.hexforhn.hex.net.hexapi.StoryCollectionService;

import java.util.List;


public class GetTopStories extends AsyncTask<Void, Integer, List<Story>> {

    private final HexApplication mApplication;
    private StoriesHandler mHandler;

    public GetTopStories(StoriesHandler handler, HexApplication application) {
        super();
        this.mApplication = application;
        this.mHandler = handler;
    }

    @Override
    protected List<Story> doInBackground(Void... params) {
        return (new StoryCollectionService(mApplication.getRequestQueue(), mApplication.getApiBaseUrl() + "/stories/top"))
                .getStories();
    }

    @Override
    public void onPostExecute(List<Story> stories) {
        if (mHandler == null) {
            return;
        }

        if (stories == null) {
            mHandler.onStoriesUnavailable();
        } else {
            mHandler.onStoriesReady(stories);
        }
    }

    public void removeHandler() {
        this.mHandler = null;
    }
}
