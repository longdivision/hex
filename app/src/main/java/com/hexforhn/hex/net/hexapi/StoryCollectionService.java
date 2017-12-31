package com.hexforhn.hex.net.hexapi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.hexforhn.hex.model.Story;
import com.hexforhn.hex.net.hexapi.marshall.FrontPageMarshaller;
import com.hexforhn.hex.net.hexapi.util.RetryPolicyFactory;
import org.json.JSONArray;

import java.util.List;


public class StoryCollectionService {

    private final RequestQueue mRequestQueue;
    private final String mApiBaseUrl;

    public enum Collection {
        Top, New, Ask, Show, Jobs
    }

    public StoryCollectionService(RequestQueue requestQueue, String apiBaseUrl) {
        mRequestQueue = requestQueue;
        mApiBaseUrl = apiBaseUrl;
    }

    public List<Story> getStories(Collection collection) {
        String apiUrl = getUrlForCollection(collection);

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(mApiBaseUrl + apiUrl, future, future);
        request.setRetryPolicy(RetryPolicyFactory.build());

        mRequestQueue.add(request);

        try {
            JSONArray response = future.get();
            return FrontPageMarshaller.marshall(response);
        } catch (Exception e) {
            return null;
        }
    }

    private String getUrlForCollection(Collection collection) {
        String collectionsUrl = "/stories/";

        switch(collection) {
            case Top:
                return collectionsUrl + "top";
            case New:
                return collectionsUrl + "new";
            case Ask:
                return collectionsUrl + "ask";
            case Show:
                return collectionsUrl + "show";
            case Jobs:
                return collectionsUrl + "jobs";
            default:
                return collectionsUrl + "top";
        }
    }
}
