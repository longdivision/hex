package uk.co.longdivision.hex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import uk.co.longdivision.hex.HexApplication;
import uk.co.longdivision.hex.R;
import uk.co.longdivision.hex.asynctask.GetItem;
import uk.co.longdivision.hex.asynctask.ItemHandler;
import uk.co.longdivision.hex.model.Item;
import uk.co.longdivision.hex.model.Story;


public class WebViewFragment extends Fragment implements ItemHandler,
        SwipeRefreshLayout.OnRefreshListener {

    private final static String STORY_ID_INTENT_EXTRA_NAME = "storyId";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mRefreshing;
    private GetItem mItemFetcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadItem();

        ViewGroup rootView = (ViewGroup) inflater.inflate( R.layout.fragment_webview, container,
                false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(
                R.id.webview_layout);

        WebView webView = (WebView) rootView.findViewById(R.id.webView);
        setupWebView(webView);

        setupRefreshLayout();

        return rootView;
    }

    @Override
    public void onItemReady(Item item) {
        Story story = (Story) item;
        WebView webView = (WebView) getView().findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                setRefreshing(false);
                updateRefreshSpinner();
            }
        });

        webView.loadUrl(story.getUrl());
    }

    private void loadItem() {
        String storyId = this.getActivity().getIntent().getStringExtra(STORY_ID_INTENT_EXTRA_NAME);
        HexApplication appContext = (HexApplication) this.getContext()
                .getApplicationContext();
        mItemFetcher = new GetItem(this, appContext);
        mItemFetcher.execute(storyId);
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        updateRefreshSpinner();
        loadItem();
    }

    private void setupWebView(WebView webView) {
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
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
        mItemFetcher.removeHandler();
        mSwipeRefreshLayout.setOnRefreshListener(null);
        super.onDestroy();
    }
}
