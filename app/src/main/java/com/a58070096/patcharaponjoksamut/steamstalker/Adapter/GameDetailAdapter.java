package com.a58070096.patcharaponjoksamut.steamstalker.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.GameDetailInfoFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.GameDetailStatsFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.GameDetailStatsViewModel;

/**
 * Created by patcharaponjoksamut on 24/11/2017 AD.
 */

public class GameDetailAdapter extends FragmentPagerAdapter {

    private final int ROW = 2;
    private GameModel game;

    public GameDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GameDetailInfoFragment gameDetailInfoFragment = new GameDetailInfoFragment();
                gameDetailInfoFragment.setGame(game);
                return gameDetailInfoFragment;
            case 1:
                GameDetailStatsFragment gameDetailStatsFragment = new GameDetailStatsFragment();
                gameDetailStatsFragment.setGame(game);
                return gameDetailStatsFragment;

        }

        return new Fragment();
    }

    @Override
    public int getCount() {
        return ROW;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Info";
            case 1:
                return "Stats";
        }
        return null;
    }

    public void setGame(GameModel game) {
        this.game = game;
    }
}
