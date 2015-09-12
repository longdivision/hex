package uk.co.longdivision.hackernews;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import uk.co.longdivision.hackernews.adapter.StorySlidePagerAdapter;

public class StoryActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mPager;

    private PagerAdapter mPagerAdaper;

    private enum Page { WEBVIEW, COMMENTS }

    private Page page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_story);
        setTitle("");

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdaper = new StorySlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdaper);
        mPager.addOnPageChangeListener(this);

        page = Page.WEBVIEW;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        } else if (item.getItemId() == R.id.action_comments) {
            mPager.setCurrentItem(1);
        } else if (item.getItemId() == R.id.action_webview) {
            mPager.setCurrentItem(0);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        if (page == Page.WEBVIEW) {
            inflater.inflate(R.menu.activity_story_icons_webview_state, menu);
        } else {
            inflater.inflate(R.menu.activity_story_icons_comment_state, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        page = (position == 0) ? Page.WEBVIEW : Page.COMMENTS;

        this.invalidateOptionsMenu();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
