package com.a58070096.patcharaponjoksamut.steamstalker.Activity;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.a58070096.patcharaponjoksamut.steamstalker.Adapter.GameDetailAdapter;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.NewsQueryModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.ProfileGameViewModel;
import com.bumptech.glide.Glide;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;
import cn.hugeterry.coordinatortablayout.listener.LoadHeaderImagesListener;

public class GameDetailActivity extends AppCompatActivity implements ProfileGameViewModel.ProfileGameListener{

    @BindView(R.id.coordinatortablayout)
    CoordinatorTabLayout coordinatorTabLayout;

    @BindView(R.id.gameDetailViewPager)
    ViewPager gameDetailViewPager;

    @BindView(R.id.game_detail_follow_button)
    FloatingActionButton followButton;

    @BindView(R.id.game_detail_like_button)
    FloatingActionButton likeButton;

    private GameModel game;

    ProfileGameViewModel viewModel = ProfileGameViewModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        ButterKnife.bind(this);

        viewModel.setListener(this);

        unpackGameExtra();
        setupCoordinatorLayout();
        setupFloatingButton();

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

    @Override
    public void onFollowedGameResponse(ArrayList<NewsQueryModel> allFollowedGame) {
        setupFloatingButton();
    }

    private void setupFloatingButton() {
        Log.v("Debug", viewModel.getFollowedGameRaw().size() + "");
        if(isFollowedGame(game.getAppId(), viewModel.getFollowedGameRaw())) {
            followButton.setImageResource(R.drawable.urv_floating_action_button_ic_fab_star);
        } else {
            followButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }

    private boolean isFollowedGame(String appId, ArrayList<NewsQueryModel> allFollowedGame) {
        for(NewsQueryModel game: allFollowedGame) {
            Log.v("Debug", "Check for " + game.getAppId() + "and" + appId);
            if(game.getAppId().equals(appId)) {

                return true;
            }
        }

        return false;
    }

    @OnClick(R.id.game_detail_like_button)
    public void onLikeButtonClicked() {

    }

    @OnClick(R.id.game_detail_follow_button)
    public void onFollowButtonClcked() {
        if(isFollowedGame(game.getAppId(), viewModel.getFollowedGameRaw())) {
            viewModel.unFollowGame(game);
            StyleableToast toast = new StyleableToast
                    .Builder(this)
                    .text("UnFollowed " + game.getName())
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .build();

            toast.show();
        } else {
            viewModel.followGame(game);

            StyleableToast toast = new StyleableToast
                    .Builder(this)
                    .text("Followed " + game.getName())
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .build();

            toast.show();
        }

        setupFloatingButton();
    }
}
