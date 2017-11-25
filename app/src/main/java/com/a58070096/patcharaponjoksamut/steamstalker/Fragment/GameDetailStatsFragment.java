package com.a58070096.patcharaponjoksamut.steamstalker.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.GameDetailStatsViewModel;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDetailStatsFragment extends Fragment {

    @BindView(R.id.game_detail_info_meta)
    TextView metaTextView;

    @BindView(R.id.game_detail_info_rank)
    TextView rankTextView;

    @BindView(R.id.game_detail_info_owner)
    TextView ownerTextView;

    @BindView(R.id.game_detail_info_player)
    TextView playerTextView;

    @BindView(R.id.game_detail_info_rating)
    TextView ratingTextView;

    private GameModel game;
    private GameDetailStatsViewModel gameDetailStatsViewModel;

    public GameDetailStatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_detail_stats, container, false);
        ButterKnife.bind(this, view);
        prepareUIData();
        return view;
    }

    public void prepareUIData() {
        metaTextView.setText(gameDetailStatsViewModel.getGameMetacritic());
        rankTextView.setText(gameDetailStatsViewModel.getGameRank());
        ownerTextView.setText(gameDetailStatsViewModel.getOwner());
        playerTextView.setText(gameDetailStatsViewModel.getGamePlayer());
        ratingTextView.setText(gameDetailStatsViewModel.getRating());
    }

    public void setGame(GameModel game) {
        this.game = game;
        this.gameDetailStatsViewModel = new GameDetailStatsViewModel(game);
    }

}
