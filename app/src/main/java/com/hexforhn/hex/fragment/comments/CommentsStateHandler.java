package com.hexforhn.hex.fragment.comments;

public interface CommentsStateHandler {
    void onEnterLoading();
    void onEnterLoaded();
    void onEnterRefresh();
    void onEnterUnavailable();
}

