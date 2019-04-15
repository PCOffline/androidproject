package com.miretsky.ron.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class NewSplashActivity extends AppCompatActivity {

    private final int TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splash);

        SharedPreferences sp = getSharedPreferences("splash", MODE_PRIVATE);
        if (sp.getBoolean("isLoggedIn", false))
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(NewSplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, TIME);
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(NewSplashActivity.this, RegsisterActivity.class);
                    startActivity(intent);
                }
            }, TIME);
            finish();
        }
    }
}