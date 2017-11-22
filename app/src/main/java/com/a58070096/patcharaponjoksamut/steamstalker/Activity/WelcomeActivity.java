package com.a58070096.patcharaponjoksamut.steamstalker.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.AuthenticationViewModel;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class WelcomeActivity extends AppCompatActivity implements AuthenticationViewModel.AuthenticationCallbacksListener {

    AuthenticationViewModel authenticationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initializeVariables();
    }

    private void initializeVariables() {
        this.authenticationViewModel = new AuthenticationViewModel();
        this.authenticationViewModel.setListener(this);
    }

    public void onGetStartedButtonPressed(View view) {
        authenticationViewModel.authenticate();
    }

    @Override
    public void onAuthenticationSuccessful() {
        this.finish();
    }

    @Override
    public void onAuthenticationFailure() {
        StyleableToast toast = new StyleableToast
                .Builder(this)
                .text("Login Failed! Please try again")
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorDarkRed))
                .build();

        toast.show();
    }
}
