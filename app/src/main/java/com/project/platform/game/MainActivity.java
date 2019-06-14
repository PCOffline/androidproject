package com.project.platform.game;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    static Player player;
    ImageButton pause;
    LinearLayout pauseLayout;
    Button resumeBtn;
    Button settingsBtn;
    Button playBtn;
    TextView timer;
    TextView announcement;
    CountDownTimer countDownTimer;
    ImageButton[] images = new ImageButton[3];
    private int stage = 0;
    private int score = 0;
    private int pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pause = findViewById(R.id.pause_btn);
        pauseLayout = findViewById(R.id.pauseLayout);
        resumeBtn = findViewById(R.id.resumeBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
        playBtn = findViewById (R.id.play);
        timer = findViewById (R.id.timer_text);
        announcement = findViewById (R.id.announcement_text);

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
        DatabaseManager.insert(player);

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

        playBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN;
    }

    private void pause() {
        toggleTimer (false);
    }

    private void resume() {
        toggleTimer (true);
    }

    private void toggleTimer(boolean mode) {
        if (mode) {
            countDownTimer = new CountDownTimer (120000, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long l) {
                    timer.setText (l / 60000 + " :" + (l / 1000) % 60);
                }

                @Override
                public void onFinish() {
                    if (stage < 10)
                        loseGame ();
                    else
                        winGame ();
                }
            };
        } else {
            countDownTimer.cancel ();
        }
    }

    private void loseGame() {
        announce (getString ("lose_msg"), Color.RED);

    }

    private void winGame() {
        announce (getString ("win_msg"), Color.GREEN);

    }

    private void nextRound() {
        if (pressed == 3) {
            winRound ();
        } else {
            loseRound ();
        }
        for (int i = 0; i <= 3; i++) {
            //images[i].setBackground (getString ("image" + stage + (i + 1)));
            //images[i].setBackground() --> we need to download images
        }
    }

    private void loseRound() {

    }

    private void winRound() {

    }

    private void announce(final String message, final int color) {
        new Timer ().schedule (new TimerTask () {
            @Override
            public void run() {
                announcement.setText (message);
                announcement.setTextColor (color);
                announcement.setVisibility (View.VISIBLE);
            }
        }, 5000);

    }

    private String getString(String s) {
        String packageName = getPackageName ();
        int resId = getResources ().getIdentifier (s, "string", packageName);
        return getString (resId);
    }

}

