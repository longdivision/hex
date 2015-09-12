package uk.co.longdivision.hackernews.asynctask;

import java.util.ArrayList;

import uk.co.longdivision.hackernews.model.Item;

public interface TopItemsHandler {
    void onTopItemsReady(ArrayList<Item> items);
}
