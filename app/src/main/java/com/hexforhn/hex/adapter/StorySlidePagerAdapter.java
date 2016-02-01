package com.hexforhn.hex.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hexforhn.hex.fragment.CommentsFragment;
import com.hexforhn.hex.fragment.WebViewFragment;

import java.util.ArrayList;

public class StorySlidePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> mFragments;

    public StorySlidePagerAdapter(FragmentManager fm) {
        super(fm);

        mFragments = new ArrayList<>();
        mFragments.add(new WebViewFragment());
        mFragments.add(new CommentsFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

