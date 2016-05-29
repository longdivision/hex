package com.hexforhn.hex.net.hexapi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.hexforhn.hex.model.Item;
import com.hexforhn.hex.model.Story;
import com.hexforhn.hex.net.hexapi.marshall.FrontPageMarshaller;
import com.hexforhn.hex.net.hexapi.util.RetryPolicyFactory;

import org.json.JSONArray;

import java.util.List;


public class StoryCollectionService {

    private final RequestQueue mRequestQueue;
    private final String mApiUrl;

    public StoryCollectionService(RequestQueue requestQueue, String apiUrl) {
        mRequestQueue = requestQueue;
        mApiUrl = apiUrl;
    }

    public List<Story> getStories() {
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(mApiUrl , future, future);
        request.setRetryPolicy(RetryPolicyFactory.build());

        mRequestQueue.add(request);

        try {
            JSONArray response = future.get();
            return FrontPageMarshaller.marshall(response);
        } catch (Exception e) {
            return null;
        }
    }
}
