package com.a58070096.patcharaponjoksamut.steamstalker.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by patcharaponjoksamut on 24/11/2017 AD.
 */

public class GameDetailAdapter extends FragmentPagerAdapter {

    private final int ROW = 2;

    public GameDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new Fragment();
    }

    @Override
    public int getCount() {
        return ROW;
    }

}
