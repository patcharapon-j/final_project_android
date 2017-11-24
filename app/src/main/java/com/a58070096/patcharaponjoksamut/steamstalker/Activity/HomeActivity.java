package com.a58070096.patcharaponjoksamut.steamstalker.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.GameFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.NewsFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.ProfileFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameTileModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.SteamAPIViewModel;
import com.androidnetworking.AndroidNetworking;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jacksonandroidnetworking.JacksonParserFactory;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements ProfileFragment.ProfileFragmentListener, SteamAPIViewModel.SteaAPIVIewModelListener {

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigationView;

    @BindView(R.id.game_container) View gameContainer;
    @BindView(R.id.profile_container) View profileContainer;
    @BindView(R.id.news_container) View newsContainer;
    @BindView(R.id.hot_container) View hotContainer;
    @BindView(R.id.activity_indicator_container) View activityIndicatorContainer;


    private SteamAPIViewModel steamAPIViewModel = new SteamAPIViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        ButterKnife.bind(this);

        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.profile_fragment);
        profileFragment.setListener(this);


        getSupportActionBar().hide();

        steamAPIViewModel.setListener(this);

        this.initializeBottomNavigationBar();
        this.checkForLogin();
    }

    private void showWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        this.startActivity(intent);
    }

    private void checkForLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            this.showWelcomeActivity();
        }
    }

    private void initializeBottomNavigationBar() {
        AHBottomNavigationItem newsItem = new AHBottomNavigationItem(R.string.text_web, R.drawable.ic_web_black_24dp, R.color.white);
        final AHBottomNavigationItem hotItem = new AHBottomNavigationItem(R.string.text_hot, R.drawable.ic_whatshot_black_24dp, R.color.white);
        AHBottomNavigationItem gameItem = new AHBottomNavigationItem(R.string.text_videogame, R.drawable.ic_videogame_asset_black_24dp, R.color.white);
        AHBottomNavigationItem profileItem = new AHBottomNavigationItem(R.string.text_person, R.drawable.ic_person_black_24dp, R.color.white);

        bottomNavigationView.addItem(newsItem);
        bottomNavigationView.addItem(hotItem);
        bottomNavigationView.addItem(gameItem);
        bottomNavigationView.addItem(profileItem);

        bottomNavigationView.setDefaultBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        bottomNavigationView.setBehaviorTranslationEnabled(false);
        bottomNavigationView.setAccentColor(getResources().getColor(R.color.white));
        bottomNavigationView.setInactiveColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigationView.setForceTint(true);
        bottomNavigationView.setTranslucentNavigationEnabled(true);

        bottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigationView.setCurrentItem(1);

        bottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                switch (position) {
                    case 0:
                        newsContainer.setVisibility(View.VISIBLE);
                        hotContainer.setVisibility(View.INVISIBLE);
                        gameContainer.setVisibility(View.INVISIBLE);
                        profileContainer.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        newsContainer.setVisibility(View.INVISIBLE);
                        hotContainer.setVisibility(View.VISIBLE);
                        gameContainer.setVisibility(View.INVISIBLE);
                        profileContainer.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        newsContainer.setVisibility(View.INVISIBLE);
                        hotContainer.setVisibility(View.INVISIBLE);
                        gameContainer.setVisibility(View.VISIBLE);
                        profileContainer.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        newsContainer.setVisibility(View.INVISIBLE);
                        hotContainer.setVisibility(View.INVISIBLE);
                        gameContainer.setVisibility(View.INVISIBLE);
                        profileContainer.setVisibility(View.VISIBLE);
                        break;
                }

                ft.commit();
                return true;
            }
        });

    }

    public void showGameDetail(String appId) {
        this.activityIndicatorContainer.setVisibility(View.VISIBLE);
        StyleableToast toast = new StyleableToast
                .Builder(this)
                .text("Load Data for " + appId)
                .build();
        steamAPIViewModel.getGameDetails(appId);

    }

    @Override
    public void onLogout() {
        this.checkForLogin();
    }

    @Override
    public void getTop100GameResponse(ArrayList<GameTileModel> allGame) {}

    @Override
    public void searchGameResponse(ArrayList<GameTileModel> allGame) {}

    @Override
    public void getGameDetailResponse(GameModel game) {
        this.activityIndicatorContainer.setVisibility(View.INVISIBLE);
        if(game == null) {
            StyleableToast toast = new StyleableToast
                    .Builder(this)
                    .text("Load Failed! Please Try Again later")
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorDarkRed))
                    .build();

            toast.show();
        } else {
            Intent intent = new Intent(this, GameDetailActivity.class);
            intent.putExtra("game", Parcels.wrap(game));
            this.startActivity(intent);
        }
    }

    @Override
    public void onSteamAccessDenied() {
        StyleableToast toast = new StyleableToast
                .Builder(this)
                .text("Too much request to Steam API. Please wait a while (Usually 10 minutes) and try again!")
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorDarkRed))
                .build();

        toast.show();

        activityIndicatorContainer.setVisibility(View.INVISIBLE);
    }
}
