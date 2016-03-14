package com.hexforhn.hex.fragment.article;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.hexforhn.hex.R;
import com.hexforhn.hex.activity.story.StoryActivity;
import com.hexforhn.hex.util.view.RefreshHandler;
import com.hexforhn.hex.util.view.SwipeRefreshManager;


public class ArticleFragment extends Fragment implements ArticleStateHandler, RefreshHandler, View.OnScrollChangeListener {

    private SwipeRefreshManager mSwipeRefreshManager;
    private String mUrl;
    private WebView mWebView;
    private ArticleState mState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_article, container,
                false);

        setupWebView(rootView);
        setupRefreshLayout(rootView);
        setupArticleUnavailableView(rootView);
        setupState();
        return rootView;
    }

    @Override
    public void onEnterLoadingUrl() {
        requestUrl();
        mSwipeRefreshManager.start();
        mSwipeRefreshManager.disable();
    }

    @Override
    public void onEnterLoadingContent() {
        loadPage();
        mSwipeRefreshManager.start();
        mSwipeRefreshManager.disable();
    }

    @Override
    public void onEnterUrlUnavailable() {
        mSwipeRefreshManager.stop();
        mSwipeRefreshManager.disable();
        showArticleUnavailable();
        hideWebView();
    }

    @Override
    public void onEnterContentUnavailable() {
        mSwipeRefreshManager.stop();
        mSwipeRefreshManager.disable();
        showArticleUnavailable();
        hideWebView();
    }

    @Override
    public void onEnterContentLoaded() {
        mSwipeRefreshManager.stop();
        mSwipeRefreshManager.enable();
        showWebView();
        hideArticleUnavailable();
    }

    public void onUrlReady(String url) {
        mUrl = url;
        mState.sendEvent(ArticleState.Event.URL_PROVIDED);
    }

    public void onUrlUnavailable() {
        mState.sendEvent(ArticleState.Event.URL_UNAVAILABLE);
    }

    @Override
    public void onRefresh() {
        mState.sendEvent(ArticleState.Event.LOAD_REQUESTED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == 0) {
            mSwipeRefreshManager.enable();
        } else {
            mSwipeRefreshManager.disable();
        }
    }

    private void setupState() {
        mState = new ArticleState(this);
        mState.sendEvent(ArticleState.Event.URL_REQUESTED);
    }

    private void setupWebView(View rootView) {
        mWebView = (WebView) rootView.findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mState.sendEvent(ArticleState.Event.LOAD_SUCCEEDED);
            }

            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                mState.sendEvent(ArticleState.Event.LOAD_FAILED);
            }
        });

        mWebView.setOnScrollChangeListener(this);
    }

    private void setupArticleUnavailableView(View rootView) {
        ((TextView) rootView.findViewById((R.id.loading_failed_text)))
                .setText(R.string.error_unableToLoadArticle);
        Button tryAgain = (Button) rootView.findViewById(R.id.try_again);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState.sendEvent(ArticleState.Event.LOAD_REQUESTED);
            }
        });
    }

    private void setupRefreshLayout(View rootView) {
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        mSwipeRefreshManager = new SwipeRefreshManager(refreshLayout, this);
    }

    private void hideWebView() {
        getView().findViewById(R.id.webView).setVisibility(View.GONE);
    }

    private void showWebView() {
        getView().findViewById(R.id.webView).setVisibility(View.VISIBLE);
    }

    private void hideArticleUnavailable() {
        getView().findViewById(R.id.content_unavailable).setVisibility(View.GONE);
    }

    private void showArticleUnavailable() {
        getView().findViewById(R.id.content_unavailable).setVisibility(View.VISIBLE);
    }

    private void loadPage() {
        mWebView.loadUrl(mUrl);
    }

    private void requestUrl() {
        ((StoryActivity) getActivity()).onUrlRequested();
    }

    public boolean handleBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return false;
    }
}
