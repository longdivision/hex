package com.hexforhn.hex.asynctask;

import com.hexforhn.hex.model.Story;

import java.util.List;


public interface StoriesHandler {
    void onStoriesReady(List<Story> stories);
    void onStoriesUnavailable();
}
