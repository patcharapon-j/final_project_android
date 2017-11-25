package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import android.media.Image;
import android.net.Uri;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by patcharaponjoksamut on 22/11/2017 AD.
 */

public class ProfileViewModel{

    private FirebaseUser currentUser;

    public ProfileViewModel() {
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

    public String getDisplayName() {
        if(currentUser != null) {
            return  currentUser.getDisplayName();
        } else {
            return "";
        }
    }

    public String getEmail() {
        if(currentUser != null) {
            return  currentUser.getEmail();
        } else {
            return "";
        }
    }

    public Uri getProfileImage() {
        if(currentUser != null) {
            return  currentUser.getPhotoUrl();
        } else {
            return Uri.parse("");
        }
    }

    public void setCurrentUser(FirebaseUser currentUser) {
        this.currentUser = currentUser;
    }
}
