package com.a58070096.patcharaponjoksamut.steamstalker.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.a58070096.patcharaponjoksamut.steamstalker.ViewModel.AuthenticationViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements AuthenticationViewModel.AuthenticationCallbacksListener {

    AuthenticationViewModel authenticationViewModel;
    LoginButton loginButton;
    CallbackManager mCallbackManager;
    Boolean isTouchEnabled = true;

    @BindView(R.id.activity_indicator_container) View activityIndicatorView;
    @BindView(R.id.activity_indicator)
    AVLoadingIndicatorView activityIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);

        initializeVariables();
        initializeFacebookButton();
    }

    private void showLoginFailToast() {
        StyleableToast toast = new StyleableToast
                .Builder(this)
                .text("Login Failed! Please try again")
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorDarkRed))
                .build();

        toast.show();
    }

    private void showActivityIndicator() {
        this.activityIndicatorView.setVisibility(View.VISIBLE);
        this.activityIndicator.smoothToShow();
        isTouchEnabled = false;
    }

    private void hideActivityIndicator() {
        this.activityIndicatorView.setVisibility(View.INVISIBLE);
        this.activityIndicator.smoothToHide();
        isTouchEnabled = true;
    }

    private void initializeFacebookButton() {
        // Initialize Facebook Login button
        this.mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("Debug", loginResult.toString());
                authenticationViewModel.authenticate(loginResult.getAccessToken());
                showActivityIndicator();
            }

            @Override
            public void onCancel() {
                Log.i("Debug", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Debug", error.toString());
                showLoginFailToast();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        this.mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeVariables() {
        this.authenticationViewModel = new AuthenticationViewModel();
        this.authenticationViewModel.setListener(this);
    }

    public void onGetStartedButtonPressed(View view) {
        loginButton.performClick();
    }

    @Override
    public void onAuthenticationSuccessful() {
        hideActivityIndicator();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
        startActivity(intent);
    }

    @Override
    public void onAuthenticationFailure() {
        hideActivityIndicator();
        showLoginFailToast();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isTouchEnabled) {
            return super.onTouchEvent(event);
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
