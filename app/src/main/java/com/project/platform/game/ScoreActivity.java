package com.project.platform.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    TextView firstPlayer, firstScore, secondPlayer, secondScore, thirdPlayer, thirdScore, youPlayer, youScore;
    Button back;
    DatabaseManager databaseManager;

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
        Player second = players.get(1);
        Player third = players.get(2);
        Player player = MainActivity.player;

        firstPlayer.setText(first.getUsername());
        firstScore.setText(first.getScore());
        secondPlayer.setText(second.getUsername());
        secondScore.setText(second.getScore());
        thirdPlayer.setText(third.getUsername());
        thirdScore.setText(third.getScore());
        youPlayer.setText(player.getUsername());
        youScore.setText(player.getScore());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScoreActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
