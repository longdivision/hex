package uk.co.longdivision.hackernews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import uk.co.longdivision.hackernews.adapter.HomeAdapter;
import uk.co.longdivision.hackernews.asynctask.GetTopItems;
import uk.co.longdivision.hackernews.asynctask.TopItemsHandler;
import uk.co.longdivision.hackernews.decoration.DividerItemDecoration;
import uk.co.longdivision.hackernews.listener.ClickListener;
import uk.co.longdivision.hackernews.model.Item;
import uk.co.longdivision.hackernews.viewmodel.ItemListItem;
import uk.co.longdivision.hackernews.viewmodel.factory.ItemListItemFactory;


public class HomeActivity extends Activity implements TopItemsHandler, ClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GetTopItems getTopItems = new GetTopItems(this);
        getTopItems.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTopItemsReady(ArrayList<Item> items) {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<ItemListItem> itemListItems = ItemListItemFactory.createItemListItems(items);
        mAdapter = new HomeAdapter(itemListItems, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v, int position, boolean isLongClick) {
        Intent storyIntent = new Intent(this, StoryActivity.class);
        startActivity(storyIntent);
    }
}
