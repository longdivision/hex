package com.hexforhn.hex.activity.frontpage;

public interface FrontPageStateHandler {
    void onLoadRequested();
    void onLoadSucceeded();
    void onLoadFailed();
    void onRefreshRequested();
    void onRefreshSucceeded();
    void onRefreshFailed();
}
