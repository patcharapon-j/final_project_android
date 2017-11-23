package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import android.media.Image;
import android.net.Uri;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by patcharaponjoksamut on 22/11/2017 AD.
 */

public class ProfileViewModel {
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

    public String getDisplayName() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            return  currentUser.getDisplayName();
        } else {
            return "";
        }
    }

    public String getEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            return  currentUser.getEmail();
        } else {
            return "";
        }
    }

    public Uri getProfileImage() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            return  currentUser.getPhotoUrl();
        } else {
            return Uri.parse("");
        }
    }
}
