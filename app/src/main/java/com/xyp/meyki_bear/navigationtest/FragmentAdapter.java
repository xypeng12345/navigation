package com.xyp.meyki_bear.navigationtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by CYW on 2017/1/12 0012.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private String[] titles;
    private String[] types;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentAdapter(FragmentManager fm, Fragment[] fragments, String[] titles, String[] types) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
        this.types = types;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments[position % fragments.length];
        String type = types[position % types.length];
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("number", ""+position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % titles.length];
    }

}
