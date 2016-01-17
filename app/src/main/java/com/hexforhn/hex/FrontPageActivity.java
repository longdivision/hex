package com.hexforhn.hex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import com.hexforhn.hex.adapter.FrontPageListAdapter;
import com.hexforhn.hex.asynctask.GetFrontPageItems;
import com.hexforhn.hex.asynctask.FrontPageItemsHandler;
import com.hexforhn.hex.decoration.DividerItemDecoration;
import com.hexforhn.hex.listener.ClickListener;
import com.hexforhn.hex.model.Item;
import com.hexforhn.hex.model.Story;
import com.hexforhn.hex.viewmodel.ItemListItemViewModel;
import com.hexforhn.hex.viewmodel.factory.ItemListItemFactory;


public class FrontPageActivity extends AppCompatActivity implements FrontPageItemsHandler,
        ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private List<? extends Item> mItems;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final static String STORY_TITLE_INTENT_EXTRA_NAME = "storyTitle";
    private final static String STORY_ID_INTENT_EXTRA_NAME = "storyId";
    private boolean mRefreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.front_page_layout);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        setupToolbar();
        setupRefreshLayout();
        setupRecyclerView();
        fetchFrontPageItems();
    }

    @Override
    public void onFrontPageItemsReady(List<? extends Item> items) {
        mItems = items;
        List<ItemListItemViewModel> itemListItems = ItemListItemFactory.createItemListItems(items);
        RecyclerView.Adapter mAdapter = new FrontPageListAdapter(itemListItems, this);
        mRecyclerView.setAdapter(mAdapter);

        setRefreshing(false);
        updateRefreshSpinner();
    }

    @Override
    public void onClick(View v, int position, boolean isLongClick) {
        Intent storyIntent = new Intent(this, StoryActivity.class);
        storyIntent.putExtra(STORY_TITLE_INTENT_EXTRA_NAME, ((Story) mItems.get(position))
                .getTitle());
        storyIntent.putExtra(STORY_ID_INTENT_EXTRA_NAME, mItems.get(position).getId());
        startActivity(storyIntent);
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        fetchFrontPageItems();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.front_page);
    }

    private void setupRecyclerView() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setHasFixedSize(true);
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

    private void fetchFrontPageItems() {
        GetFrontPageItems getFrontPageItems = new GetFrontPageItems(this, (HexApplication)
                this.getApplication());
        getFrontPageItems.execute();
    }
}
