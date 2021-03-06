package com.a58070096.patcharaponjoksamut.steamstalker.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.GameDetailInfoViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDetailInfoFragment extends Fragment {

    @BindView(R.id.game_detail_info_name)
    TextView nameTextView;

    @BindView(R.id.game_detail_info_description)
    TextView descriptionTextView;

    @BindView(R.id.game_detail_info_developer)
    TextView developerTextView;

    @BindView(R.id.game_detail_info_publisher)
    TextView publisherTextView;

    @BindView(R.id.game_detail_info_website)
    TextView websiteTextView;

    @BindView(R.id.game_detail_info_support)
    TextView supportTextView;

    @BindView(R.id.game_detail_info_price)
    TextView priceTextView;

    @BindView(R.id.game_detail_info_date)
    TextView dateTextView;

    private GameModel game;
    private GameDetailInfoViewModel gameDetailInfoViewModel;

    public GameDetailInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_detail_info, container, false);
        ButterKnife.bind(this, view);
        prepareUIData();
        return view;
    }

    public void setGame(GameModel game) {
        this.game = game;
        this.gameDetailInfoViewModel = new GameDetailInfoViewModel(game);
    }

    public void prepareUIData() {
        nameTextView.setText(gameDetailInfoViewModel.getGameName());
        descriptionTextView.setText(Html.fromHtml(gameDetailInfoViewModel.getGameDescrition()));
        developerTextView.setText(gameDetailInfoViewModel.getGameDeveloper());
        publisherTextView.setText(gameDetailInfoViewModel.getGamePublisher());
        if(!gameDetailInfoViewModel.getGameWebsite().equals("N/A")) {
            websiteTextView.setText(Html.fromHtml("<u>"+gameDetailInfoViewModel.getGameWebsite()+"</u>"));
        } else {
            websiteTextView.setText("N/A");
        }
        supportTextView.setText(gameDetailInfoViewModel.getSupportedOperatingSystem());
        priceTextView.setText(gameDetailInfoViewModel.getGamePrice());
        dateTextView.setText(gameDetailInfoViewModel.getGameDate());

    }

    @OnClick(R.id.game_detail_info_website)
    public void onGameDetailWebsiteClicked() {
        if(!gameDetailInfoViewModel.getGameWebsite().equals("N/A")) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(gameDetailInfoViewModel.getGameWebsite()));
            startActivity(myIntent);
        }
    }

    @OnClick(R.id.game_detail_info_goto)
    public void onGameGotoSteamClicked() {
        Uri uri = Uri.parse("http://store.steampowered.com/app/" + game.getAppId());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
