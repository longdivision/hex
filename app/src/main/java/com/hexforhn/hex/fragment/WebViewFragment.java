package com.hexforhn.hex.fragment;

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

        setupWebView(rootView);
        setupRefreshLayout(rootView);
        setupArticleUnavailableView(rootView);

        return rootView;
    }

    public void onUrlReady(String url) {
        if (mUrl != null) { return; }

        mUrl = url;
        reloadPage();
    }

    public void onUrlUnavailable() {
        if (mUrl == null) {
            setRefreshing(false);
            updateRefreshSpinner();
            showArticleUnavailable();
        }
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        updateRefreshSpinner();
        reloadPage();
    }

    @Override
    public void onDestroyView() {
        mSwipeRefreshLayout.setOnRefreshListener(null);
        super.onDestroy();
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
                showWebView();
                setRefreshing(false);
                updateRefreshSpinner();
            }

            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                showArticleUnavailable();
            }
        });
    }

    private void setupRefreshLayout(View rootView) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.webview_layout);

        setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateRefreshSpinner();
            }
        }, 500);
    }

    private void setupArticleUnavailableView(View rootView) {
        ((TextView) rootView.findViewById((R.id.loading_failed_text)))
                .setText(R.string.unable_to_load_article);
        Button tryAgain = (Button) rootView.findViewById(R.id.try_again);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUrl == null) {
                    requestUrl();
                } else {
                    reloadPage();
                }
            }
        });
    }

    private void setRefreshing(boolean refreshing) {
        mRefreshing = refreshing;
    }

    private void updateRefreshSpinner() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(mRefreshing);
        }
    }

    private void showWebView() {
        getView().findViewById(R.id.webview_layout).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.content_unavailable).setVisibility(View.GONE);
    }

    private void showArticleUnavailable() {
        getView().findViewById(R.id.webview_layout).setVisibility(View.GONE);
        getView().findViewById(R.id.content_unavailable).setVisibility(View.VISIBLE);
    }

    private void reloadPage() {
        mWebView.loadUrl(mUrl);
    }

    private void requestUrl() {
        ((StoryActivity) getActivity()).onUrlRequested();
    }
}
