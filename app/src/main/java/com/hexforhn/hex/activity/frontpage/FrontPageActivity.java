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
import com.hexforhn.hex.viewmodel.ItemListItemViewModel;
import com.hexforhn.hex.viewmodel.factory.ItemListItemFactory;

import java.util.List;


public class FrontPageActivity extends AppCompatActivity implements FrontPageItemsHandler,
        FrontPageStateHandler, ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private List<? extends Item> mItems;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final static String STORY_TITLE_INTENT_EXTRA_NAME = "storyTitle";
    private final static String STORY_ID_INTENT_EXTRA_NAME = "storyId";
    private final static int MINIMUM_SPINNER_VISIBLE_PERIOD_MS = 500;
    private boolean mRefreshing;
    private GetFrontPageItems mGetFrontPageItems;
    private FrontPageStateMachine mStateMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_front_page);

        setupToolbar();
        setupRefreshLayout();
        setupRecyclerView();
        setupItemsUnavailableView();
        setupStateMachine();
        fetchFrontPageItems();
    }

    @Override
    public void onLoadRequested() {
        fetchFrontPageItems();
        startRefreshIndicator();
    }

    @Override
    public void onLoadSucceeded() {
        stopRefreshIndicator();
        displayItems(mItems);
        hideContentUnavailable();
        showItems();
        enableRefresh();
    }

    @Override
    public void onLoadFailed() {
        stopRefreshIndicator();
        disableRefresh();
        hideItems();
        showContentUnavailable();
    }

    @Override
    public void onRefreshRequested() {
        startRefreshIndicator();
        fetchFrontPageItems();
    }

    @Override
    public void onRefreshSucceeded() {
        displayItems(mItems);
        stopRefreshIndicator();
        enableRefresh();
        hideContentUnavailable();
        showItems();
    }

    @Override
    public void onRefreshFailed() {
        stopRefreshIndicator();
        showRefreshFailedSnackbar();
    }

    @Override
    public void onItemsReady(List<? extends Item> items) {
        setItems(items);
        mStateMachine.transitionTo(FrontPageStateMachine.State.ITEMS_LOADED);
    }

    @Override
    public void onItemsUnavailable() {
        mStateMachine.transitionTo(FrontPageStateMachine.State.ITEMS_UNAVAILABLE);
    }

    @Override
    public void onClick(View v, int position, boolean isLongClick) {
        openStoryAtPosition(position);
    }

    @Override
    public void onRefresh() {
        mStateMachine.transitionTo(FrontPageStateMachine.State.REFRESHING);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) { actionBar.setTitle(R.string.front_page); }
    }

    private void setupRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.front_page);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        startRefreshIndicator();
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateRefreshSpinner();
            }
        }, MINIMUM_SPINNER_VISIBLE_PERIOD_MS);
    }

    private void setupRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.items);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setHasFixedSize(true);
    }

    private void setupItemsUnavailableView() {
        TextView loadingFailed = (TextView) findViewById(R.id.loading_failed_text);
        loadingFailed.setText(R.string.unable_to_load_front_page);

        Button tryAgain = (Button) findViewById(R.id.try_again);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateMachine.transitionTo(FrontPageStateMachine.State.REFRESHING);
            }
        });
    }

    private void setupStateMachine() {
        mStateMachine = new FrontPageStateMachine(this, false);
        mStateMachine.transitionTo(FrontPageStateMachine.State.ITEMS_LOADING);
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

    private void enableRefresh() {
        mSwipeRefreshLayout.setEnabled(true);
    }

    private void disableRefresh() {
        mSwipeRefreshLayout.setEnabled(false);
    }

    private void startRefreshIndicator() {
        mRefreshing = true;
        updateRefreshSpinner();
    }

    private void stopRefreshIndicator() {
        mRefreshing = false;
        updateRefreshSpinner();
    }

    private void updateRefreshSpinner() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(mRefreshing);
        }
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
