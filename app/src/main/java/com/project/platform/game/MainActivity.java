package com.project.platform.game;

import android.annotation.SuppressLint;
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

public class MainActivity extends AppCompatActivity {

    static Player player;
    ImageButton pause;
    LinearLayout pauseLayout;
    TextView timer;
    TextView announcement;
    RelativeLayout imagesView;
    CountDownTimer countDownTimer;
    Button play;
    ImageButton[] images;
    DatabaseManager databaseManager;

    private TextView scoreTxt;

    private int stage = 0;
    private int score = 0;
    private int pressed;
    private int correct;
    private long leftMillis = 120000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseManager = new DatabaseManager (this);
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

        databaseManager.deleteAll ();

        player = new Player (name, password, this);
        databaseManager.insert (player);

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
                    score = 0;
                    nextRound ();
                }
            };
            countDownTimer.start ();
        } else {
            countDownTimer.cancel ();
        }
    }

    @SuppressLint("DefaultLocale")
    private void nextRound() {
        if (stage == 0) {
            stage++;
            scoreTxt.setText ("0");
            correct = new Random ().nextInt (4);
            buttonsVisible (true);

        } else if (stage == 10) {
            buttonsVisible (false);
            countDownTimer.cancel ();
            timer.setVisibility (View.GONE);
            scoreTxt.setVisibility (View.GONE);
            //startActivity(new Intent(MainActivity.this, ScoreActivity.class));
            //finish();
        } else if (stage < 10 && timer.getText ().toString ().equals ("0:00")) {

        } else {
            if (correct == pressed) {
                score++;
                scoreTxt.setText (String.format ("%d", score));
            }
            stage++;

                /*for (int i = 0; i <= 3; i++) {
                    images[i].setImageResource (getDrawable (stage + (i + 1) + ""));
                }*/
            correct = new Random ().nextInt (4);
            buttonsVisible (true);
            }
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
        stage = 0;
        score = 0;
        play.setVisibility (View.GONE);
        timer.setVisibility (View.VISIBLE);
        scoreTxt.setVisibility (View.VISIBLE);
        timer.setVisibility (View.VISIBLE);
        toggleTimer (true);
        nextRound ();
    }

    private void buttonsVisible(boolean mode) {
        int visibility = mode ? View.VISIBLE : View.GONE;
        imagesView.setVisibility (visibility);
        pause.setVisibility (visibility);
        for (int i = 0; i < images.length; i++) {
            if (i == correct)
                images[correct].setImageResource (getDrawable (stage + "4"));
            else {
                int j = i + 1;
                images[i].setImageResource (getDrawable (!(stage + "" + j + "").equals ("4") ? stage + "" + j + "" : i + ""));
            }
        }
    }
}

