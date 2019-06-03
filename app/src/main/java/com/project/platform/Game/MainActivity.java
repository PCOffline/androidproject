package com.project.platform.Game;

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

    boolean pressed = false;
    ImageButton pause;
    ImageView ball;
    LinearLayout pauseLayout;
    Button resumeBtn;
    Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pause = findViewById(R.id.pause_btn);
        ball = findViewById(R.id.ball);
        pauseLayout = findViewById(R.id.pauseLayout);
        resumeBtn = findViewById(R.id.resumeBtn);
        settingsBtn = findViewById(R.id.settingsBtn);

        Toast.makeText(this, new Player("", "").getId(), Toast.LENGTH_LONG).show();

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
        this.ball.setX(event.getX());
        return event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE;
    }
}

