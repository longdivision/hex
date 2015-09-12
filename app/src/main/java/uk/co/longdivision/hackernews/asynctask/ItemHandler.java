package uk.co.longdivision.hackernews.asynctask;

import uk.co.longdivision.hackernews.model.Item;


public interface ItemHandler {
    void onItemReady(Item item);
}
