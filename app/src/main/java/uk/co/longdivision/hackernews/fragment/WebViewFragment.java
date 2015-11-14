package uk.co.longdivision.hackernews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import uk.co.longdivision.hackernews.HackerNewsApplication;
import uk.co.longdivision.hackernews.R;
import uk.co.longdivision.hackernews.asynctask.GetItem;
import uk.co.longdivision.hackernews.asynctask.ItemHandler;
import uk.co.longdivision.hackernews.model.Item;
import uk.co.longdivision.hackernews.model.Story;


public class WebViewFragment extends Fragment implements ItemHandler {

    private final static String STORY_ID_INTENT_EXTRA_NAME = "storyId";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadItem();

        ViewGroup rootView = (ViewGroup) inflater.inflate( R.layout.fragment_webview, container,
                false);

        WebView webView = (WebView) rootView.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        return rootView;
    }

    @Override
    public void onItemReady(Item item) {
        Story story = (Story) item;
        WebView webView = (WebView) getView().findViewById(R.id.webView);
        webView.loadUrl(story.getUrl());
    }

    private void loadItem() {
        String storyId = this.getActivity().getIntent().getStringExtra(STORY_ID_INTENT_EXTRA_NAME);
        HackerNewsApplication appContext = (HackerNewsApplication) this.getContext()
                .getApplicationContext();
        new GetItem(this, appContext).execute(storyId);
    }
}
