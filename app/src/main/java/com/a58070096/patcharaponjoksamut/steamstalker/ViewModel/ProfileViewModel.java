package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by patcharaponjoksamut on 22/11/2017 AD.
 */

public class ProfileViewModel {
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }
}
