package com.hexforhn.hex.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.hexforhn.hex.fragment.CommentsFragment;
import com.hexforhn.hex.fragment.WebViewFragment;

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

