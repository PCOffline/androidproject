package com.project.platform.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ImageButton pause;
    LinearLayout pauseLayout;
    Button resumeBtn;
    Button settingsBtn;
    static Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pause = findViewById(R.id.pause_btn);
        pauseLayout = findViewById(R.id.pauseLayout);
        resumeBtn = findViewById(R.id.resumeBtn);
        settingsBtn = findViewById(R.id.settingsBtn);

        String name;
        String password;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                name = null;
                password = null;
            } else {
                name = extras.getString("username");
                password = extras.getString("password");
            }
        } else {
            name = (String) savedInstanceState.getSerializable("username");
            password = (String) savedInstanceState.getSerializable("password");
        }

        player = new Player(name, password);

        pauseLayout.setVisibility(View.INVISIBLE);


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
                pauseLayout.setVisibility(View.VISIBLE);
            }
        });

        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resume();
                pauseLayout.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN;
    }

    private void pause() {

    }

    private void resume() {

    }
}

