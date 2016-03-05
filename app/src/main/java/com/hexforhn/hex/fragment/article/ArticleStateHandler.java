package com.hexforhn.hex.fragment.article;

public interface ArticleStateHandler {
    void onEnterLoadingUrl();
    void onEnterLoadingContent();
    void onEnterUrlUnavailable();
    void onEnterContentUnavailable();
    void onEnterContentLoaded();
}
