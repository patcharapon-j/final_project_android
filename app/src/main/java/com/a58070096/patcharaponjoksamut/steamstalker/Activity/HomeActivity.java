package com.a58070096.patcharaponjoksamut.steamstalker.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        this.checkForLogin();
    }

    private void showWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        this.startActivity(intent);
    }

    private void checkForLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            this.showWelcomeActivity();
        }
    }
}
