package com.a58070096.patcharaponjoksamut.steamstalker.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.a58070096.patcharaponjoksamut.steamstalker.Adapter.GameTileAdapter;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameTileModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.ultimate_recycler_view)
    UltimateRecyclerView recyclerView;

    private List<GameTileModel> allGameTileModel;


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, view);

        loadGameTileData();
        setupRecyclerView(view);

        return view;
    }

    private void loadGameTileData() {
        allGameTileModel = new ArrayList<>();
        allGameTileModel.add(new GameTileModel("We Slay Monsters", Uri.parse("http://cdn.akamai.steamstatic.com/steam/apps/332540/ss_2b346066ec4e674c51f952ac443d4472813fd8e5.600x338.jpg?t=1498799828")));
        allGameTileModel.add(new GameTileModel("We Slay Monsters", Uri.parse("http://cdn.akamai.steamstatic.com/steam/apps/332540/ss_2b346066ec4e674c51f952ac443d4472813fd8e5.600x338.jpg?t=1498799828")));
        allGameTileModel.add(new GameTileModel("We Slay Monsters", Uri.parse("http://cdn.akamai.steamstatic.com/steam/apps/332540/ss_2b346066ec4e674c51f952ac443d4472813fd8e5.600x338.jpg?t=1498799828")));
        allGameTileModel.add(new GameTileModel("We Slay Monsters", Uri.parse("http://cdn.edgecast.steamstatic.com/steam/apps/243780/header.jpg?t=1484564592")));
        allGameTileModel.add(new GameTileModel("We Slay Monsters", Uri.parse("http://cdn.akamai.steamstatic.com/steam/apps/332540/ss_2b346066ec4e674c51f952ac443d4472813fd8e5.600x338.jpg?t=1498799828")));
        allGameTileModel.add(new GameTileModel("We Slay Monsters", Uri.parse("http://cdn.akamai.steamstatic.com/steam/apps/332540/ss_2b346066ec4e674c51f952ac443d4472813fd8e5.600x338.jpg?t=1498799828")));
        allGameTileModel.add(new GameTileModel("We Slay Monsters", Uri.parse("http://cdn.akamai.steamstatic.com/steam/apps/332540/ss_2b346066ec4e674c51f952ac443d4472813fd8e5.600x338.jpg?t=1498799828")));

    }

    private void setupRecyclerView(View view) {
        GameTileAdapter adapter = new GameTileAdapter(allGameTileModel);
        adapter.setActivity(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
