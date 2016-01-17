package com.hexforhn.hex.net.hackernewsapi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import com.hexforhn.hex.model.Item;
import com.hexforhn.hex.net.hackernewsapi.marshall.ItemMarshaller;
import com.hexforhn.hex.net.hackernewsapi.util.RetryPolicyFactory;


public class ItemService {

    private final RequestQueue mRequestQueue;

    public ItemService(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    public Item getItem(String itemId) {
        String API_URL = "https://hex-api.herokuapp.com";
        String STORY_PATH = "/story";
        String itemPath = API_URL + STORY_PATH + "/" + itemId;

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(itemPath, future, future);
        request.setRetryPolicy(RetryPolicyFactory.build());
        mRequestQueue.add(request);

        try {
            JSONObject response = future.get();
            return ItemMarshaller.marshall(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
