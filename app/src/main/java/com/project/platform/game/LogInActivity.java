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

    Button submitButton;
    Button submitButton2;
    EditText passwordEditText;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        SharedPreferences sp = getSharedPreferences("loginActivity", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        submitButton = findViewById(R.id.button_submit_LogIn);
        emailEditText = findViewById(R.id.edit_text_email_LogIn);
        passwordEditText = findViewById(R.id.edit_text_password_LogIn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailEditText.getText().toString().length() > 0
                        && emailEditText.getText().toString().contains("@")
                        && passwordEditText.getText().toString().length() > 0) {
                    //if password & mail are correct
                    editor.putBoolean("isLoggedIn", true);
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LogInActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submitButton2 = findViewById(R.id.button_submit_LogIn2);
        submitButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}