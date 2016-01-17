package com.hexforhn.hex.asynctask;

import java.util.List;

import com.hexforhn.hex.model.Item;


public interface FrontPageItemsHandler {
    void onFrontPageItemsReady(List<? extends Item> items);
}
