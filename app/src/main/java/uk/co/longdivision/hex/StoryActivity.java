package uk.co.longdivision.hex;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import uk.co.longdivision.hex.adapter.StorySlidePagerAdapter;
import uk.co.longdivision.hex.asynctask.GetItem;
import uk.co.longdivision.hex.asynctask.ItemHandler;
import uk.co.longdivision.hex.model.Item;
import uk.co.longdivision.hex.model.Story;

public class StoryActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        ItemHandler, TabLayout.OnTabSelectedListener {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Item mItem;
    private TabLayout mTabLayout;

    private enum Page { WEBVIEW, COMMENTS }
    private Page mPage;
    private final static String STORY_TITLE_INTENT_EXTRA_NAME = "storyTitle";
    private final static String STORY_ID_INTENT_EXTRA_NAME = "storyId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadItem();

        setContentView(R.layout.activity_story);

        setupToolbar();

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        setupTabs();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new StorySlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);

        mPage = Page.WEBVIEW;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
        new GetItem(this, appContext).execute(storyId);
    }
}
