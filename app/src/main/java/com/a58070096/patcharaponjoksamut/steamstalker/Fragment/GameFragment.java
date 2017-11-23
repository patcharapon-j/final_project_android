package com.a58070096.patcharaponjoksamut.steamstalker.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

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


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(view);
        return view;
    }

}
