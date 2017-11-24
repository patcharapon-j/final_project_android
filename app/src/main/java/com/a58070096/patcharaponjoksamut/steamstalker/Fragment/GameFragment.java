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
public class GameFragment extends Fragment implements SteamAPIViewModel.SteaAPIVIewModelListener, AllGameDataCache.SearchForGameResult {

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.ultimate_recycler_view)
    UltimateRecyclerView recyclerView;

    @BindView(R.id.activity_indicator_container) View activityIndicatiorContainer;

    private List<GameTileModel> allGameTileModel;
    private SteamAPIViewModel steamViewModel = new SteamAPIViewModel();
    private GameTileAdapter adapter;

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, view);

        initializeViewModel();
        setupSearchView();

        loadGameTileData();
        setupRecyclerView(view);

        return view;
    }

    private void setupSearchView() {
        AllGameDataCache.getInstance().setListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.isEmpty() || s.equals("")) {

                } else {
                    activityIndicatiorContainer.setVisibility(View.VISIBLE);
                    AllGameDataCache.getInstance().searchForGame(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initializeViewModel() {
        steamViewModel.setListener(this);
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

    }

    @Override
    public void getTop100GameResponse(ArrayList<GameTileModel> allGame) {
        adapter.setGameList(allGame);
        adapter.notifyDataSetChanged();
        recyclerView.setRefreshing(false);
        activityIndicatiorContainer.setVisibility(View.INVISIBLE);

    }

    @Override
    public void searchGameResponse(ArrayList<GameTileModel> allGame) {
        recyclerView.hideEmptyView();
        adapter.setGameList(allGame);
        adapter.notifyDataSetChanged();
        recyclerView.setRefreshing(false);
        activityIndicatiorContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getGameDetailResponse(GameModel game) {

    }

    @Override
    public void onSteamAccessDenied() {
        StyleableToast toast = new StyleableToast
                .Builder(getActivity())
                .text("Too much request to Steam API. Please wait a while and try again!")
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorDarkRed))
                .build();
    }


    @Override
    public void onFoundGame(ArrayList<String> result) {
        Log.v("Debug", result.toString());
        steamViewModel.getTileForGame(result);
    }

    @Override
    public void onNotFound() {
        Log.v("Debug", "Not Found");
        adapter.setGameList(new ArrayList<GameTileModel>());
        adapter.notifyDataSetChanged();
        recyclerView.setRefreshing(false);
        recyclerView.showEmptyView();
        activityIndicatiorContainer.setVisibility(View.INVISIBLE);
    }
}
