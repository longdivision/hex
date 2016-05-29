package com.hexforhn.hex.asynctask;

import com.hexforhn.hex.model.Story;


public interface StoryHandler {
    void onStoryReady(Story story);
    void onStoryUnavailable();
}
