package uk.co.longdivision.hex.net.hackernewsapi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;

import java.util.List;

import uk.co.longdivision.hex.model.Item;
import uk.co.longdivision.hex.net.hackernewsapi.marshall.FrontPageMarshaller;
import uk.co.longdivision.hex.net.hackernewsapi.util.RetryPolicyFactory;


public class FrontPageService {

    private final RequestQueue mRequestQueue;

    public FrontPageService(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    public List<? extends Item> getTopItems() {
        String API_URL = "https://hex-api.herokuapp.com";
        String FRONT_PAGE_PATH = "/front-page";
        String frontPagePath = API_URL + FRONT_PAGE_PATH;

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(frontPagePath, future, future);
        request.setRetryPolicy(RetryPolicyFactory.build());

        mRequestQueue.add(request);

        try {
            JSONArray response = future.get();
            return FrontPageMarshaller.marshall(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
