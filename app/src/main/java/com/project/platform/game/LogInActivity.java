package com.project.platform.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    Button loginBtn;
    Button forgotPassBtn;
    EditText password;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        SharedPreferences sp = getSharedPreferences("loginActivity", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        loginBtn = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgotPassBtn = findViewById(R.id.forgot_password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = LogInActivity.this.username.getText().toString();
                String password = LogInActivity.this.password.getText().toString();
                if (username.length() > 0
                        && username.contains("@")
                        && password.length() > 0
                        && DatabaseManager.findByName("").getPassword().equals(password)) {
                    // If all credentials are valid
                    editor.putBoolean("isLoggedIn", true).apply();
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LogInActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}