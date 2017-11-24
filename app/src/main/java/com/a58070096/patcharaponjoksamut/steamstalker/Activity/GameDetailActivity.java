package com.a58070096.patcharaponjoksamut.steamstalker.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a58070096.patcharaponjoksamut.steamstalker.Adapter.GameDetailAdapter;
import com.a58070096.patcharaponjoksamut.steamstalker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

public class GameDetailActivity extends AppCompatActivity {

    @BindView(R.id.coordinatortablayout)
    CoordinatorTabLayout coordinatorTabLayout;

    @BindView(R.id.gameDetailViewPager)
    ViewPager gameDetailViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        ButterKnife.bind(this);

        setupCoordinatorLayout();

    }

    private void setupCoordinatorLayout() {

        gameDetailViewPager.setAdapter(new GameDetailAdapter(getSupportFragmentManager()));

        coordinatorTabLayout.setTitle("Demo")
                .setupWithViewPager(gameDetailViewPager);
    }
}
