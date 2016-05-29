package com.hexforhn.hex.asynctask;

import android.os.AsyncTask;

import com.hexforhn.hex.HexApplication;
import com.hexforhn.hex.model.Story;
import com.hexforhn.hex.net.hexapi.StoryService;


public class GetStory extends AsyncTask<String, Integer, Story> {

    private StoryHandler mHandler;
    private final HexApplication mApplication;

    public GetStory(StoryHandler handler, HexApplication application) {
        super();
        this.mApplication = application;
        this.mHandler = handler;
    }

    @Override
    protected Story doInBackground(String... params) {
        return (new StoryService(mApplication.getRequestQueue(), mApplication.getApiBaseUrl()))
                .getStory(params[0]);
    }

    @Override
    public void onPostExecute(Story story) {
        if (mHandler == null) { return; }

        if (story == null) {
            mHandler.onStoryUnavailable();
        } else {
            mHandler.onStoryReady(story);
        }
    }

    public void removeHandler() {
        mHandler = null;
    }
}
