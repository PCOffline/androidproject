package com.project.platform.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.miretsky.ron.myapp.R;

public class MainActivity extends AppCompatActivity {

    boolean pressed = false;
    ImageButton pause;
    ImageView ball;
    LinearLayout pauseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pauseLayout.setVisibility(View.INVISIBLE);

        pause = findViewById(R.id.pause_btn);
        ball = findViewById(R.id.ball);
        pauseLayout = findViewById(R.id.pauseLayout);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressed = true;

            case MotionEvent.ACTION_MOVE:
                pressed = false;

            case MotionEvent.ACTION_UP:
                pressed = false;
        }
        return pressed;
    }
}

