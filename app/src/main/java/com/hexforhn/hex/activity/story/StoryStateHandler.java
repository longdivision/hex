package com.hexforhn.hex.activity.story;

public interface StoryStateHandler {
    void onEnterLoading();
    void onEnterLoaded();
    void onEnterUnavailable();
}
