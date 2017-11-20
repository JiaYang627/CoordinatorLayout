package com.jiayang.coordinatorlayout.toolbarhidden;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by 张 奎 on 2017-11-19 14:11.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {


    private final List<Fragment> mFragments;
    private final List<String> mTitles;

    public FragmentAdapter(FragmentManager fm , List<String> titles , List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
