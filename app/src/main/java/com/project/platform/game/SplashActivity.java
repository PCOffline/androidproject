package com.project.platform.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private final int TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splash);

        new Handler ().postDelayed (new Runnable () {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences ("Splash", MODE_PRIVATE);
                if (sp.getBoolean ("isLoggedIn", false)) {
                    startActivity (new Intent (SplashActivity.this, MainActivity.class));
                    finish ();
                } else {
                    startActivity (new Intent (SplashActivity.this, RegisterActivity.class));
                    finish ();
                }
            }
        }, TIME);
    }
}