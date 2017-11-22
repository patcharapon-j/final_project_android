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

import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.GameFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.NewsFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.Fragment.ProfileFragment;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        getSupportActionBar().hide();

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
        AHBottomNavigationItem gameItem = new AHBottomNavigationItem(R.string.text_videogame, R.drawable.ic_videogame_asset_black_24dp, R.color.white);
        AHBottomNavigationItem profileItem = new AHBottomNavigationItem(R.string.text_person, R.drawable.ic_person_black_24dp, R.color.white);

        bottomNavigationView.addItem(newsItem);
        bottomNavigationView.addItem(gameItem);
        bottomNavigationView.addItem(profileItem);

        bottomNavigationView.setDefaultBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        bottomNavigationView.setBehaviorTranslationEnabled(false);
        bottomNavigationView.setAccentColor(getResources().getColor(R.color.white));
        bottomNavigationView.setInactiveColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigationView.setForceTint(true);
        bottomNavigationView.setTranslucentNavigationEnabled(true);

        bottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigationView.setCurrentItem(0);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment newFragment;
        newFragment = new NewsFragment();
        ft.replace(R.id.fragment_container, newFragment);
        ft.commit();

        bottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment newFragment;

                switch (position) {
                    case 0:
                        newFragment = new NewsFragment();
                        ft.replace(R.id.fragment_container, newFragment);
                        break;
                    case 1:
                        newFragment = new GameFragment();
                        ft.replace(R.id.fragment_container, newFragment);
                        break;
                    case 2:
                        newFragment = new ProfileFragment();
                        ft.replace(R.id.fragment_container, newFragment);
                        break;
                }

                ft.commit();
                return true;
            }
        });

    }
}
