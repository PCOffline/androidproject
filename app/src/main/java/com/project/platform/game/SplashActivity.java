package com.project.platform.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.miretsky.ron.myapp.R;

public class SplashActivity extends AppCompatActivity {

    private final int TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME);
    }
}