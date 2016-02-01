package com.hexforhn.hex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hexforhn.hex.R;


public class WebViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mRefreshing;
    private String mUrl;
    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_webview, container,
                false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.webview_layout);

        setupWebView(rootView);
        setupRefreshLayout();

        return rootView;
    }

    public void onUrlReady(String url) {
        if (mUrl != null) { return; }

        mUrl = url;
        mWebView.loadUrl(url);
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        updateRefreshSpinner();
        mWebView.loadUrl(mUrl);
    }

    private void setupWebView(View rootView) {
        mWebView = (WebView) rootView.findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

        mWebView.setWebViewClient(new WebViewClient() {
            public void onLoadResource(WebView view, String url) {
                setRefreshing(false);
                updateRefreshSpinner();
            }
        });
    }

    private void setupRefreshLayout() {
        setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateRefreshSpinner();
            }
        }, 500);
    }

    private void setRefreshing(boolean refreshing) {
        mRefreshing = refreshing;
    }

    private void updateRefreshSpinner() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(mRefreshing);
        }
    }

    @Override
    public void onDestroyView() {
        mSwipeRefreshLayout.setOnRefreshListener(null);
        super.onDestroy();
    }
}
