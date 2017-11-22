package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

/**
 * Created by patcharaponjoksamut on 22/11/2017 AD.
 */

public class AuthenticationViewModel {

    public interface AuthenticationCallbacksListener {
        void onAuthenticationSuccessful();
        void onAuthenticationFailure();
    }

    AuthenticationCallbacksListener listener;

    public void authenticate() {
        this.listener.onAuthenticationFailure();
    }

    public void setListener(AuthenticationCallbacksListener listener) {
        this.listener = listener;
    }
}
