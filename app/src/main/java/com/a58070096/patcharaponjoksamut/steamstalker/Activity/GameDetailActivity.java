package com.a58070096.patcharaponjoksamut.steamstalker.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.a58070096.patcharaponjoksamut.steamstalker.Adapter.GameDetailAdapter;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;
import cn.hugeterry.coordinatortablayout.listener.LoadHeaderImagesListener;

public class GameDetailActivity extends AppCompatActivity {

    @BindView(R.id.coordinatortablayout)
    CoordinatorTabLayout coordinatorTabLayout;

    @BindView(R.id.gameDetailViewPager)
    ViewPager gameDetailViewPager;

    GameModel game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        ButterKnife.bind(this);

        unpackGameExtra();
        setupCoordinatorLayout();

    }

    private void unpackGameExtra() {
        game = Parcels.unwrap(getIntent().getParcelableExtra("game"));
    }

    private void setupCoordinatorLayout() {

        GameDetailAdapter adapter = new GameDetailAdapter(getSupportFragmentManager());
        adapter.setGame(game);

        gameDetailViewPager.setAdapter(adapter);

        coordinatorTabLayout.setTitle(game.getName())
                .setupWithViewPager(gameDetailViewPager)
                .setTranslucentStatusBar(this)
                .setLoadHeaderImagesListener(new LoadHeaderImagesListener() {
                    @Override
                    public void loadHeaderImages(ImageView imageView, TabLayout.Tab tab) {
                        Log.v("Debug", "Loag header image with url" + game.getHeaderImageUrl().toString());
                        Glide.with(GameDetailActivity.this).load(game.getHeaderImageUrl()).into(imageView);
                    }
                });
    }
}
