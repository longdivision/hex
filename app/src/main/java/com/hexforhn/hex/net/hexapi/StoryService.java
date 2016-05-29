package com.hexforhn.hex.net.hexapi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.hexforhn.hex.model.Story;
import com.hexforhn.hex.net.hexapi.marshall.StoryMarshaller;
import com.hexforhn.hex.net.hexapi.util.RetryPolicyFactory;

import org.json.JSONObject;


public class StoryService {

    private final RequestQueue mRequestQueue;
    private final String mApiBaseUrl;

    public StoryService(RequestQueue requestQueue, String apiUrl) {
        this.mRequestQueue = requestQueue;
        mApiBaseUrl = apiUrl;
    }

    public Story getStory(String storyId) {
        String STORY_PATH = "/story";
        String storyPath = mApiBaseUrl + STORY_PATH + "/" + storyId;

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(storyPath, future, future);
        request.setRetryPolicy(RetryPolicyFactory.build());
        mRequestQueue.add(request);

        try {
            JSONObject response = future.get();
            return StoryMarshaller.marshall(response);
        } catch (Exception e) {
            return null;
        }
    }
}
