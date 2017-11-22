package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by patcharaponjoksamut on 22/11/2017 AD.
 */

public class AuthenticationViewModel {

    public interface AuthenticationCallbacksListener {
        void onAuthenticationSuccessful();
        void onAuthenticationFailure();
    }


    AuthenticationCallbacksListener listener;

    public void authenticate(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Debug", "signInWithCredential:success");
                            listener.onAuthenticationSuccessful();
                        } else {
                            Log.w("Debug", "signInWithCredential:failure", task.getException());
                            listener.onAuthenticationFailure();
                        }
                    }
                });
    }

    public void setListener(AuthenticationCallbacksListener listener) {
        this.listener = listener;
    }
}
