package com.a58070096.patcharaponjoksamut.steamstalker.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.AuthenticationViewModel;

public class WelcomeActivity extends AppCompatActivity implements AuthenticationViewModel.AuthenticationCallbacks {

    AuthenticationViewModel authenticationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initalizeVariables();


    }

    private void initalizeVariables() {
        this.authenticationViewModel = new AuthenticationViewModel();
    }

    public void onGetStartedButtonPressed(View view) {
    }

    @Override
    public void onAuthenticationSuccessful() {
        //TODO: Back to Home Activity
    }

    @Override
    public void onAuthenticationFailure() {
        //TODO: Show Error
    }
}
