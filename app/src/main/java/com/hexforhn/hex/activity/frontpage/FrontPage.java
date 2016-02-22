package com.hexforhn.hex.activity.frontpage;

public interface FrontPage {
    void onEnterLoading();
    void onEnterLoaded();
    void onEnterRefresh();
    void onEnterUnavailable();
}
