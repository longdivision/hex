package uk.co.longdivision.hackernews.asynctask;

import java.util.List;

import uk.co.longdivision.hackernews.model.Item;


public interface FrontPageItemsHandler {
    void onFrontPageItemsReady(List<? extends Item> items);
}
