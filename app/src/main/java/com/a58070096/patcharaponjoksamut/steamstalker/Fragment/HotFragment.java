package com.a58070096.patcharaponjoksamut.steamstalker.Fragment;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.SearchView;
import android.widget.TextView;

import com.a58070096.patcharaponjoksamut.steamstalker.Adapter.GameTileAdapter;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameCacheModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameTileModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.Singleton.AllGameDataCache;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.SteamAPIViewModel;
import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment implements SteamAPIViewModel.SteaAPIVIewModelListener {

    @BindView(R.id.ultimate_recycler_view_hot)
    UltimateRecyclerView recyclerView;

    @BindView(R.id.game_bar_text)
    TextView gameBarTextView;

    @BindView(R.id.activity_indicator_container) View activityIndicatiorContainer;

    private List<GameTileModel> allGameTileModel;
    private SteamAPIViewModel steamViewModel = new SteamAPIViewModel();
    private GameTileAdapter adapter;

    public HotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);

        initializeViewModel();

        loadGameTileData();
        setupRecyclerView(view);

        activityIndicatiorContainer.setVisibility(View.VISIBLE);
        steamViewModel.getTop100Game();

        return view;
    }

    private void initializeViewModel() {
        steamViewModel.setListener(this);
        activityIndicatiorContainer.setVisibility(View.VISIBLE);
        steamViewModel.getTop100Game();
    }

    private void loadGameTileData() {
        allGameTileModel = new ArrayList<>();
    }

    private void setupRecyclerView(View view) {
        adapter = new GameTileAdapter(allGameTileModel);
        adapter.setActivity(getActivity());
        final RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activityIndicatiorContainer.setVisibility(View.VISIBLE);
                steamViewModel.getTop100Game();
            }
        });

    }

    @Override
    public void getTop100GameResponse(ArrayList<GameTileModel> allGame) {
        adapter.setGameList(allGame);
        adapter.notifyDataSetChanged();
        recyclerView.setRefreshing(false);
        gameBarTextView.setText(R.string.default_game_bar_text);
        activityIndicatiorContainer.setVisibility(View.INVISIBLE);

    }

    @Override
    public void searchGameResponse(ArrayList<GameTileModel> allGame) {
        //None
    }

    @Override
    public void getGameDetailResponse(GameModel game) {

    }

    @Override
    public void onSteamAccessDenied() {
        StyleableToast toast = new StyleableToast
                .Builder(getActivity())
                .text("Too much request to Steam API. Please wait a while (Usually 10 minutes) and try again!")
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorDarkRed))
                .build();

        toast.show();

        activityIndicatiorContainer.setVisibility(View.INVISIBLE);
    }

}
