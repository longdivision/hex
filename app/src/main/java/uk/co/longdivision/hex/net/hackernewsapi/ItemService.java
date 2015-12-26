package uk.co.longdivision.hex.net.hackernewsapi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import uk.co.longdivision.hex.model.Item;
import uk.co.longdivision.hex.net.hackernewsapi.marshall.ItemMarshaller;


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
