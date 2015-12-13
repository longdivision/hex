package uk.co.longdivision.hex.net.hackernewsapi.marshall;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import uk.co.longdivision.hex.model.Item;

public class FrontPageMarshaller {

    public static List<? extends Item> marshall(JSONArray rawItems) {
        List<Item> frontPageItems = new ArrayList<>();

        for (int i = 0; i < rawItems.length(); i++) {
            try {
                frontPageItems.add(ItemMarshaller.marshall(rawItems.getJSONObject(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return frontPageItems;
    }
}
