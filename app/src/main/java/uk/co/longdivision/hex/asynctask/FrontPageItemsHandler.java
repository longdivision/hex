package uk.co.longdivision.hex.asynctask;

import java.util.List;

import uk.co.longdivision.hex.model.Item;


public interface FrontPageItemsHandler {
    void onFrontPageItemsReady(List<? extends Item> items);
}
