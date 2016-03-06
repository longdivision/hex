package com.hexforhn.hex.net.hexapi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.hexforhn.hex.model.Item;
import com.hexforhn.hex.net.hexapi.marshall.ItemMarshaller;
import com.hexforhn.hex.net.hexapi.util.RetryPolicyFactory;

import org.json.JSONObject;


public class ItemService {

    private final RequestQueue mRequestQueue;
    private final String mApiBaseUrl;

    public ItemService(RequestQueue requestQueue, String apiUrl) {
        this.mRequestQueue = requestQueue;
        mApiBaseUrl = apiUrl;
    }

    public Item getItem(String itemId) {
        String STORY_PATH = "/story";
        String itemPath = mApiBaseUrl + STORY_PATH + "/" + itemId;

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(itemPath, future, future);
        request.setRetryPolicy(RetryPolicyFactory.build());
        mRequestQueue.add(request);

        try {
            JSONObject response = future.get();
            return ItemMarshaller.marshall(response);
        } catch (Exception e) {
            return null;
        }
    }
}
