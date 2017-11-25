package com.a58070096.patcharaponjoksamut.steamstalker.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.GameDetailInfoFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.GameDetailStatsFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.NewsFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.NewsQueryModel;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.GameDetailStatsViewModel;

import java.util.ArrayList;

/**
 * Created by patcharaponjoksamut on 24/11/2017 AD.
 */

public class GameDetailAdapter extends FragmentPagerAdapter {

    private final int ROW = 3;
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
            case 2:
                NewsFragment newsFragment = new NewsFragment();
                ArrayList<NewsQueryModel> query = new ArrayList<>();
                NewsQueryModel model = new NewsQueryModel();
                model.setAppName(game.getName());
                model.setAppId(game.getAppId());
                query.add(model);
                newsFragment.setAllNewsQuery(query);
                newsFragment.setMode(-1);
                return newsFragment;

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
            case 2:
                return "News";
        }
        return null;
    }

    public void setGame(GameModel game) {
        this.game = game;
    }
}
