package com.hexforhn.hex.net.hexapi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.hexforhn.hex.model.Item;
import com.hexforhn.hex.net.hexapi.marshall.FrontPageMarshaller;
import com.hexforhn.hex.net.hexapi.util.RetryPolicyFactory;

import org.json.JSONArray;

import java.util.List;


public class FrontPageService {

    private final RequestQueue mRequestQueue;
    private final String mApiBaseUrl;

    public FrontPageService(RequestQueue requestQueue, String apiUrl) {
        mRequestQueue = requestQueue;
        mApiBaseUrl = apiUrl;
    }

    public List<? extends Item> getTopItems() {
        String FRONT_PAGE_PATH = "/front-page";
        String frontPagePath = mApiBaseUrl + FRONT_PAGE_PATH;

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(frontPagePath, future, future);
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
