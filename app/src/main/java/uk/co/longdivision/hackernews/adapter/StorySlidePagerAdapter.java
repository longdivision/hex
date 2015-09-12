package uk.co.longdivision.hackernews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import uk.co.longdivision.hackernews.fragment.CommentsFragment;
import uk.co.longdivision.hackernews.fragment.WebViewFragment;

public class StorySlidePagerAdapter extends FragmentStatePagerAdapter {

    public StorySlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return (position == 0) ? new WebViewFragment() : new CommentsFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}

