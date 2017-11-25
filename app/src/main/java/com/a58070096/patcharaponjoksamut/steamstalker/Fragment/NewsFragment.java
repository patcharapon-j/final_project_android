package com.a58070096.patcharaponjoksamut.steamstalker.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a58070096.patcharaponjoksamut.steamstalker.Adapter.GameTileAdapter;
import com.a58070096.patcharaponjoksamut.steamstalker.Adapter.NewsTileAdapter;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.NewsTileModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.SteamNewsViewModel;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements SteamNewsViewModel.OnGetNewsForAppResponse {

    @BindView(R.id.ultimate_recycler_view)
    UltimateRecyclerView recyclerView;

    @BindView(R.id.activity_indicator_container) View activityIndicatiorContainer;

    private NewsTileAdapter adapter;
    private SteamNewsViewModel steamNewsViewModel;
    private String appid;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView(view);
        setupViewModel();

        getNewsFor("440", "test");

        return view;
    }

    private void setupRecyclerView(View view) {
        adapter = new NewsTileAdapter();
        adapter.setActivity(getActivity());
        adapter.setAllNews(new ArrayList<NewsTileModel>());

        final RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewsFor("440", "Team Test");
            }
        });
    }

    private void getNewsFor(String appId, String name) {
        activityIndicatiorContainer.setVisibility(View.VISIBLE);
        steamNewsViewModel.getNewsForApp(appId, name);
    }

    private void setupViewModel() {
        steamNewsViewModel = new SteamNewsViewModel();
        steamNewsViewModel.setListener(this);
    }

    @Override
    public void onGetNewsResult(ArrayList<NewsTileModel> result) {
        activityIndicatiorContainer.setVisibility(View.INVISIBLE);
        recyclerView.setRefreshing(false);
        if(result == null) {
            StyleableToast toast = new StyleableToast
                    .Builder(getActivity())
                    .text("Too much request to Steam API. Please wait a while (Usually 10 minutes) and try again!")
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorDarkRed))
                    .build();

            toast.show();
        } else {
            adapter.setAllNews(result);
            adapter.notifyDataSetChanged();
        }
    }
}
