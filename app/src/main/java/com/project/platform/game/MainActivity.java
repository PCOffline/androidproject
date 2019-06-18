package com.project.platform.game;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    Button add;
    Button logout;

    private TextView scoreTxt;

    private int stage = -1;
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
        add = findViewById(R.id.add);
        logout = findViewById(R.id.logout);
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

        player = new Player (name, password, this);
        try {
        databaseManager.insert (player);
        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, "This account already exists", Toast.LENGTH_LONG).show();
        }

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

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("New Player");
                    LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    final EditText username = new EditText(MainActivity.this);
                    username.setInputType(InputType.TYPE_CLASS_TEXT);
                    username.setHint("Username");
                    final EditText password = new EditText(MainActivity.this);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setHint("Password");
                    final EditText score = new EditText(MainActivity.this);
                    score.setInputType(InputType.TYPE_CLASS_NUMBER);
                    score.setHint("Score");
                    linearLayout.addView(username);
                    linearLayout.addView(password);
                    linearLayout.addView(score);
                    builder.setView(linearLayout);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = username.getText().toString();
                            String pass = password.getText().toString();
                            int scr = Integer.parseInt(score.getText().toString());
                            databaseManager.insert(new Player(databaseManager.sortByScore().size(), name, pass, scr));
                            Toast.makeText(MainActivity.this, "Added to Database", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor sp = getSharedPreferences("pref", MODE_PRIVATE).edit();
                    sp.putBoolean("isLoggedIn", false);
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    finish();
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
        if (stage == -1) {
            stage++;
            scoreTxt.setText ("0");
            correct = new Random ().nextInt (4);
            buttonsVisible (true);
            nextImage();
        } else if (stage == 10) {
            buttonsVisible (false);
            add.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            stage = 0;
            countDownTimer.cancel ();
            timer.setVisibility (View.GONE);
            scoreTxt.setVisibility (View.GONE);
            databaseManager.update(player, new Player(player.getId(), player.getUsername(), player.getPassword(), player.getScore() + score));
            player.setScore(player.getScore() + score);
            startActivity(new Intent(MainActivity.this, ScoreActivity.class));
            finish();
        } else if (stage < 10 && timer.getText ().toString ().equals ("0:00")) {
            buttonsVisible(false);
            add.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            score = 0;
            stage = 0;
            timer.setVisibility(View.GONE);
            scoreTxt.setVisibility(View.GONE);
            startActivity(new Intent(MainActivity.this, ScoreActivity.class));
            finish();
        } else {
            if (correct == pressed) {
                score++;
                scoreTxt.setText (String.format ("%d", score));
            }
            stage++;
            correct = new Random ().nextInt (4);
            nextImage();
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
        logout.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
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
    }

    private void nextImage() {
        for (int i = 0; i < images.length; i++) {
            if (i == correct)
                images[correct].setImageResource(getDrawable(stage + "4"));
            else {
                int j = i + 1;
                String s = stage + "" + j;
                images[i].setImageResource(getDrawable(s.charAt(s.length() - 1) != '4' ? stage + "" + j + "" : stage + "" + i));
            }
        }
    }
}

