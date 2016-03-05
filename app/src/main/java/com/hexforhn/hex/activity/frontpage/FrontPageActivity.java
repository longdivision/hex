package com.hexforhn.hex.activity.frontpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hexforhn.hex.HexApplication;
import com.hexforhn.hex.R;
import com.hexforhn.hex.activity.story.StoryActivity;
import com.hexforhn.hex.adapter.FrontPageListAdapter;
import com.hexforhn.hex.asynctask.FrontPageItemsHandler;
import com.hexforhn.hex.asynctask.GetFrontPageItems;
import com.hexforhn.hex.decoration.DividerItemDecoration;
import com.hexforhn.hex.listener.ClickListener;
import com.hexforhn.hex.model.Item;
import com.hexforhn.hex.model.Story;
import com.hexforhn.hex.util.view.RefreshHandler;
import com.hexforhn.hex.util.view.SwipeRefreshManager;
import com.hexforhn.hex.viewmodel.ItemListItemViewModel;
import com.hexforhn.hex.viewmodel.factory.ItemListItemFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FrontPageActivity extends AppCompatActivity implements FrontPageItemsHandler,
        FrontPageStateHandler, ClickListener, RefreshHandler {

    private RecyclerView mRecyclerView;
    private List<? extends Item> mItems = new ArrayList<>();
    private SwipeRefreshManager mSwipeRefreshManager;
    private final static String STORY_TITLE_INTENT_EXTRA_NAME = "storyTitle";
    private final static String STORY_ID_INTENT_EXTRA_NAME = "storyId";
    private GetFrontPageItems mGetFrontPageItems;
    private FrontPageState mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        setupToolbar();
        setupRecyclerView();
        setupItemsUnavailableView();
        setupRefreshLayout();
        setupState();
    }

    @Override
    public void onEnterLoading() {
        fetchFrontPageItems();
        mSwipeRefreshManager.start();
    }

    @Override
    public void onEnterLoaded() {
        displayItems(mItems);
        hideContentUnavailable();
        showItems();
        mSwipeRefreshManager.stop();
        mSwipeRefreshManager.enable();
    }

    @Override
    public void onEnterRefresh() {
        mSwipeRefreshManager.start();
        mSwipeRefreshManager.disable();
        fetchFrontPageItems();
    }

    @Override
    public void onEnterUnavailable() {
        mSwipeRefreshManager.stop();
        if (mItems.isEmpty()) {
            hideItems();
            mSwipeRefreshManager.disable();
            showContentUnavailable();
        } else {
            mSwipeRefreshManager.enable();
            showRefreshFailedSnackbar();
        }
    }

    @Override
    public void onItemsReady(List<? extends Item> items) {
        setItems(items);
        mState.sendEvent(FrontPageState.Event.LOAD_SUCCEEDED);
    }

    @Override
    public void onItemsUnavailable() {
        mState.sendEvent(FrontPageState.Event.LOAD_FAILED);
    }

    @Override
    public void onClick(View v, int position, boolean isLongClick) {
        openStoryAtPosition(position);
    }

    @Override
    public void onRefresh() {
        mState.sendEvent(FrontPageState.Event.LOAD_REQUESTED);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) { actionBar.setTitle(R.string.front_page); }
    }

    private void setupRefreshLayout() {
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshManager = new SwipeRefreshManager(refreshLayout, this);
    }

    private void setupRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.items);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.Adapter mAdapter = new FrontPageListAdapter(Collections.EMPTY_LIST, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupItemsUnavailableView() {
        TextView loadingFailed = (TextView) findViewById(R.id.loading_failed_text);
        loadingFailed.setText(R.string.unable_to_load_front_page);

        Button tryAgain = (Button) findViewById(R.id.try_again);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mState.sendEvent(FrontPageState.Event.LOAD_REQUESTED);
            }
        });
    }

    private void setupState() {
        mState = new FrontPageState(this);
        mState.sendEvent(FrontPageState.Event.LOAD_REQUESTED);
    }

    private void displayItems(List<? extends Item> items) {
        List<ItemListItemViewModel> itemListItems = ItemListItemFactory.createItemListItems(items);
        RecyclerView.Adapter mAdapter = new FrontPageListAdapter(itemListItems, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showItems() {
        findViewById(R.id.items).setVisibility(View.VISIBLE);
    }

    private void hideItems() {
        findViewById(R.id.items).setVisibility(View.GONE);
    }

    private void showContentUnavailable() {
        findViewById(R.id.content_unavailable).setVisibility(View.VISIBLE);
    }

    private void hideContentUnavailable() {
        findViewById(R.id.content_unavailable).setVisibility(View.GONE);
    }

    private void fetchFrontPageItems() {
        if (mGetFrontPageItems != null) {
            mGetFrontPageItems.removeHandler();
        }

        mGetFrontPageItems = new GetFrontPageItems(this, (HexApplication) this.getApplication());
        mGetFrontPageItems.execute();
    }

    private void setItems(List<? extends Item> items) {
        mItems = items;
    }

    private void openStoryAtPosition(int position) {
        Intent storyIntent = new Intent(this, StoryActivity.class);
        Story story =  (Story) mItems.get(position);
        storyIntent.putExtra(STORY_TITLE_INTENT_EXTRA_NAME, story.getTitle());
        storyIntent.putExtra(STORY_ID_INTENT_EXTRA_NAME, story.getId());

        startActivity(storyIntent);
    }

    private void showRefreshFailedSnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.front_page_container),
                R.string.unable_to_load_front_page, Snackbar.LENGTH_LONG);
        TextView snackbarTextView = (TextView) snackbar.getView()
                .findViewById(android.support.design.R.id.snackbar_text);
        snackbarTextView.setTextColor(Color.WHITE);

        snackbar.show();
    }
}
