package com.hexforhn.hex.activity.story;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hexforhn.hex.HexApplication;
import com.hexforhn.hex.R;
import com.hexforhn.hex.adapter.StorySlidePagerAdapter;
import com.hexforhn.hex.asynctask.GetItem;
import com.hexforhn.hex.asynctask.ItemHandler;
import com.hexforhn.hex.fragment.article.ArticleFragment;
import com.hexforhn.hex.fragment.comments.CommentsFragment;
import com.hexforhn.hex.model.Comment;
import com.hexforhn.hex.model.Item;
import com.hexforhn.hex.model.Story;
import com.hexforhn.hex.viewmodel.CommentViewModel;

import java.util.ArrayList;
import java.util.List;

public class StoryActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        ItemHandler, TabLayout.OnTabSelectedListener, StoryStateHandler {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Item mItem;
    private TabLayout mTabLayout;
    private enum Page { WEBVIEW, COMMENTS }
    private Page mPage;
    private final static String STORY_TITLE_INTENT_EXTRA_NAME = "storyTitle";
    private final static String STORY_ID_INTENT_EXTRA_NAME = "storyId";
    private GetItem mGetItem;
    private StoryState mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story);

        setupToolbar();

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        setupTabs();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new StorySlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);

        mPage = Page.WEBVIEW;

        mState = new StoryState(this);
        mState.sendEvent(StoryState.Event.LOAD_REQUESTED);
    }

    @Override
    public void onEnterLoading() {
        loadItem();
    }

    @Override
    public void onEnterLoaded() {
        this.provideUrlToWebViewFragment(((Story) mItem).getUrl());
        this.provideCommentsToCommentFragment(((Story) mItem).getComments());
    }

    @Override
    public void onEnterUnavailable() {
        showRefreshFailedSnackbar();
        ((ArticleFragment) ((StorySlidePagerAdapter) mPagerAdapter).getItem(0))
                .onUrlUnavailable();
        ((CommentsFragment) ((StorySlidePagerAdapter) mPagerAdapter).getItem(1))
                .onCommentsUnavailable();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        } else if (item.getItemId() == R.id.action_share) {
            handleShareRequest();
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_story_icons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mTabLayout.getTabAt(position).select();
        mPage = Page.values()[position];
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onItemReady(Item item) {
        this.mItem = item;
        mState.sendEvent(StoryState.Event.LOAD_SUCCEEDED);
    }

    public void onItemUnavailable() {
        mState.sendEvent(StoryState.Event.LOAD_FAILED);
    }

    public void onCommentRefreshRequested() {
        mState.sendEvent(StoryState.Event.LOAD_REQUESTED);
    }

    public void onUrlRequested() {
        mState.sendEvent(StoryState.Event.LOAD_REQUESTED);
    }

    private void provideCommentsToCommentFragment(List<Comment> comments) {
        List<CommentViewModel> viewComments = new ArrayList<>();

        for (Comment comment : comments) {
            addCommentToList(comment, viewComments, 0);
        }

        ((CommentsFragment) ((StorySlidePagerAdapter) mPagerAdapter).getItem(1))
                .onCommentsReady(viewComments);
    }

    private void provideUrlToWebViewFragment(String url) {
        ((ArticleFragment) ((StorySlidePagerAdapter) mPagerAdapter).getItem(0)).onUrlReady(url);
    }

    private void addCommentToList(Comment comment, List<CommentViewModel> list, int depth) {
        list.add(new CommentViewModel(comment.getUser(), comment.getText(), depth,
                comment.getCommentCount(), comment.getDate()));

        for(com.hexforhn.hex.model.Comment childComment : comment.getChildComments()) {
            addCommentToList(childComment, list, depth + 1);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mPager.setCurrentItem(tab.getPosition());
        mPage = Page.values()[tab.getPosition()];
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    private void setupToolbar() {
        String storyTitle = this.getIntent().getStringExtra(STORY_TITLE_INTENT_EXTRA_NAME);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(storyTitle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupTabs() {
        mTabLayout.addTab(mTabLayout.newTab().setText("Article"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Comments"));
        mTabLayout.setOnTabSelectedListener(this);
    }

    private void handleShareRequest() {
        if (mItem == null) {
            return;
        }

        String intentMessage = getString(R.string.shareArticle);
        String url = ((Story) mItem).getUrl();

        if (mPage.equals(Page.COMMENTS)) {
            intentMessage = getString(R.string.shareComments);
            url = ((Story) mItem).getCommentsUrl();
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(shareIntent, intentMessage));
    }

    private void loadItem() {
        String storyId = this.getIntent().getStringExtra(STORY_ID_INTENT_EXTRA_NAME);
        HexApplication appContext = (HexApplication) this.getApplicationContext()
                .getApplicationContext();

        if (mGetItem != null) {
            mGetItem.removeHandler();
        }

        mGetItem = new GetItem(this, appContext);
        mGetItem.execute(storyId);
    }

    protected void onDestroy () {
        super.onDestroy();
        mGetItem.removeHandler();
    }

    private void showRefreshFailedSnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.comments),
                R.string.unable_to_load_story, Snackbar.LENGTH_LONG);
        TextView snackbarTextView = (TextView) snackbar.getView()
                .findViewById(android.support.design.R.id.snackbar_text);
        snackbarTextView.setTextColor(Color.WHITE);

        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        ArticleFragment articleFragment = (ArticleFragment) (((StorySlidePagerAdapter)
                mPagerAdapter).getItem(0));
        
        boolean backHandled = articleFragment.handleBack();

        if (!backHandled) {
            super.onBackPressed();
        }
    }
}
