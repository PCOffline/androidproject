package com.project.platform.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton pause;
    LinearLayout pauseLayout;
    Button resumeBtn;
    Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pause = findViewById(R.id.pause_btn);
        pauseLayout = findViewById(R.id.pauseLayout);
        resumeBtn = findViewById(R.id.resumeBtn);
        settingsBtn = findViewById(R.id.settingsBtn);

        pauseLayout.setVisibility(View.INVISIBLE);


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseLayout.setVisibility(View.VISIBLE);
            }
        });

        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseLayout.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN;
    }
}

