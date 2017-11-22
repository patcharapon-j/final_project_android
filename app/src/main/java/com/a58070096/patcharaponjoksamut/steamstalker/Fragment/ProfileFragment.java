package com.a58070096.patcharaponjoksamut.steamstalker.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.ProfileViewModel;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

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

        return view;
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
}
