package com.hexforhn.hex.activity.frontpage;

public interface FrontPageStateHandler {
    void onEnterLoading();
    void onEnterLoaded();
    void onEnterRefresh();
    void onEnterUnavailable();
}
