package com.project.platform.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    TextView firstPlayer, firstScore, secondPlayer, secondScore, thirdPlayer, thirdScore, youPlayer, youScore;
    Button back;
    DatabaseManager databaseManager;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        firstPlayer = findViewById(R.id.first_player);
        firstScore = findViewById(R.id.first_score);
        secondPlayer = findViewById(R.id.second_player);
        secondScore = findViewById(R.id.second_score);
        thirdPlayer = findViewById(R.id.third_player);
        thirdScore = findViewById(R.id.third_score);
        youPlayer = findViewById(R.id.you_username);
        youScore = findViewById(R.id.you_score);
        back = findViewById(R.id.back);
        databaseManager = new DatabaseManager(this);

        List<Player> players = databaseManager.sortByScore();
        Player first = players.get(0);
        Player second = players.size() > 2 ? players.get(1) : null;
        Player third = players.size() > 3 ? players.get(2) : null;
        Player player = MainActivity.player;

        firstPlayer.setText(first.getUsername());
        firstScore.setText(String.format("%d", first.getScore()));
        if (second != null) {
            secondPlayer.setText(second.getUsername());
            firstScore.setText(String.format("%d", second.getScore()));
        } else {
            secondPlayer.setVisibility(View.GONE);
            secondScore.setVisibility(View.GONE);
        }

        if (third != null) {
            thirdPlayer.setText(third.getUsername());
            thirdScore.setText(String.format("%d", third.getScore()));
        } else {
            thirdPlayer.setVisibility(View.GONE);
            thirdScore.setVisibility(View.GONE);
        }
        youPlayer.setText(player.getUsername());
        youScore.setText(String.format("%d", player.getScore()));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScoreActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
