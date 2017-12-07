package com.a58070096.patcharaponjoksamut.steamstalker.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a58070096.patcharaponjoksamut.steamstalker.Adapter.GameTileAdapter;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameTileModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.NewsQueryModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.ProfileGameViewModel;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.ProfileViewModel;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.SteamAPIViewModel;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment implements SteamAPIViewModel.SteaAPIVIewModelListener, ProfileGameViewModel.ProfileGameListener {

    @BindView(R.id.profile_image)
    CircleImageView imageView;
    @BindView(R.id.display_name_text)
    TextView displayNameTextView;
    @BindView(R.id.email_text)
    TextView emailTextView;
    @BindView(R.id.ultimate_recycler_view)
    UltimateRecyclerView recyclerView;

    private List<GameTileModel> allGameTileModel = new ArrayList<>();
    private SteamAPIViewModel steamViewModel = new SteamAPIViewModel();
    private GameTileAdapter adapter;
    private ProfileGameViewModel profileGameViewModel;

    @Override
    public void getTop100GameResponse(ArrayList<GameTileModel> allGame) {

    }

    @Override
    public void searchGameResponse(ArrayList<GameTileModel> allGame) {
        recyclerView.hideEmptyView();
        adapter.setGameList(allGame);
        adapter.notifyDataSetChanged();
        recyclerView.setRefreshing(false);
    }

    @Override
    public void getGameDetailResponse(GameModel game) {

    }

    @Override
    public void onSteamAccessDenied() {

    }

    @Override
    public void onFollowedGameResponse(ArrayList<NewsQueryModel> allFollowedGame) {
        if(allFollowedGame.size() == 0) {
            recyclerView.setRefreshing(false);
            adapter.setGameList(new ArrayList<GameTileModel>());
            adapter.notifyDataSetChanged();
            recyclerView.showEmptyView();
        } else {
            ArrayList<String> allGameString = new ArrayList<>();
            for(NewsQueryModel game: allFollowedGame) {
                allGameString.add(game.getAppId());
            }
            steamViewModel.getTileForGame(allGameString);
        }
    }

    public interface ProfileFragmentListener {
        void onLogout();
    }

    ProfileViewModel profileViewModel = new ProfileViewModel();
    ProfileFragmentListener listener;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        displayUserInfo();
        setupRecyclerView(view);
        setupViewModel();

        return view;
    }

    private void setupViewModel() {
        profileGameViewModel = ProfileGameViewModel.getInstance();
        profileGameViewModel.setListener(this);
        profileGameViewModel.getFollowedGame();
        steamViewModel.setListener(this);

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
                profileGameViewModel.getFollowedGame();
            }
        });

    }

    @OnClick(R.id.logout_button)
    public void btnPressed() {
        showLogoutConfirmDialog();
    }

    public void setListener(ProfileFragmentListener listener) {
        this.listener = listener;
    }

    private void showLogoutConfirmDialog() {
        new MaterialDialog.Builder(getContext())
                .title("Logout")
                .content("Are you sure you want to Logout?")
                .positiveText("Logout")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        profileViewModel.signOut();
                        listener.onLogout();
                    }
                })
                .negativeText("Cancel")
                .show();
    }

    private void displayUserInfo() {
        displayNameTextView.setText(profileViewModel.getDisplayName());
        emailTextView.setText(profileViewModel.getEmail());
        Glide.with(this).load(profileViewModel.getProfileImage()).into(imageView);
    }

    public ProfileViewModel getProfileViewModel() {
        return profileViewModel;
    }

    public void reloadUserData() {
        displayUserInfo();
        profileGameViewModel = ProfileGameViewModel.getInstance();
        profileGameViewModel.reloadUserData();
        profileGameViewModel.setListener(this);
        profileGameViewModel.getFollowedGame();
    }
}
