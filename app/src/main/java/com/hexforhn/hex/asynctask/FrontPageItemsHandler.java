package com.hexforhn.hex.asynctask;

import com.hexforhn.hex.model.Item;

import java.util.List;


public interface FrontPageItemsHandler {
    void onItemsReady(List<? extends Item> items);
    void onItemsUnavailable();
}
