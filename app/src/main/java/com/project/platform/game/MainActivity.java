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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    static Player player;
    ImageButton pause;
    LinearLayout pauseLayout;
    TextView timer;
    TextView announcement;
    RelativeLayout imagesView;
    CountDownTimer countDownTimer;
    Button play;
    long p = 120000;
    ImageButton[] images;
    private int stage = 0;
    private int score = 0;
    private int pressed;
    private int correct;
    private long leftMillis = 120000;
    private TextView scoreTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        pauseLayout = findViewById (R.id.pauseLayout);
        timer = findViewById (R.id.timer_text);
        announcement = findViewById (R.id.announcement_text);
        play = findViewById (R.id.play);
        scoreTxt = findViewById (R.id.score_text);
        pause = findViewById (R.id.pause_btn);
        images = new ImageButton[]{findViewById (R.id.image1), findViewById (R.id.image2), findViewById (R.id.image3), findViewById (R.id.image4)};
        imagesView = findViewById (R.id.images);
        String name;
        String password;
        if (savedInstanceState == null) {
            Bundle extras = getIntent ().getExtras ();
            if (extras == null) {
                name = null;
                password = null;
            } else {
                name = extras.getString ("username");
                password = extras.getString ("password");
            }
        } else {
            name = (String) savedInstanceState.getSerializable ("username");
            password = (String) savedInstanceState.getSerializable ("password");
        }

        player = new Player (name, password);
        //DatabaseManager.insert (player);

        correct = new Random ().nextInt (4);

        for (int i = 0; i < images.length; i++) {
            final int finalI = i;
            images[i].setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    pressed = finalI;
                    nextRound ();
                }
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction () == MotionEvent.ACTION_DOWN;
    }


    private void toggleTimer(boolean mode) {
        if (mode) {
            countDownTimer = new CountDownTimer (leftMillis, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long l) {
                    timer.setText (l / 60000 + ":" + (l / 1000) % 60);
                    leftMillis = l;
                }

                @Override
                public void onFinish() {
                    if (stage < 10)
                        loseGame ();
                    else
                        winGame ();
                }
            };
            countDownTimer.start ();
        } else {
            countDownTimer.cancel ();
        }
    }

    private void loseGame() {
        announce (getString (R.string.lose_msg), Color.RED);

    }

    private void winGame() {
        announce (getString (R.string.lose_msg), Color.GREEN);
        nextRound ();
    }

    private void nextRound() {
        for (ImageButton img : images) {
            img.setEnabled (false);
        }
        if (correct == pressed) {
            score++;
            scoreTxt.setText (score);
            announce ("Correct!", Color.GREEN);
        } else {
            announce ("Wrong!", Color.RED);
        }
        stage++;

                /*for (int i = 0; i <= 3; i++) {
                    images[i].setImageResource (getDrawable (stage + (i + 1) + ""));
                }*/
        correct = new Random ().nextInt (4);
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

    private int getDrawable(String s) {
        String packageName = getPackageName ();
        return getResources ().getIdentifier ("image" + s, "drawable", packageName);
    }

  /*  public void settings(View view) {

    }*/

    public void resume(View view) {
        pauseLayout.setVisibility (View.GONE);
        buttonsVisible (true);
        toggleTimer (true);
    }

    public void pause(View view) {
        pauseLayout.setVisibility (View.VISIBLE);
        buttonsVisible (false);
        toggleTimer (false);
    }

    public void play(View view) {
        play.setVisibility (View.GONE);
        timer.setVisibility (View.VISIBLE);
        buttonsVisible (true);
        scoreTxt.setVisibility (View.VISIBLE);
        timer.setVisibility (View.VISIBLE);
        toggleTimer (true);

    }

    private void buttonsVisible(boolean mode) {
        int visibility = mode ? View.VISIBLE : View.GONE;
        imagesView.setVisibility (visibility);
        pause.setVisibility (visibility);
    }
}

