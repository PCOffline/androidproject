package com.project.platform.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    Button login;
    EditText password;
    EditText confirmPassword;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.register);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        username = findViewById(R.id.username);
        login = findViewById(R.id.login);

        final SharedPreferences.Editor editor = getSharedPreferences("Register", MODE_PRIVATE).edit();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = username.getText().toString();
                if (s.length() > 0 && DatabaseManager.findByName(s) == null) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString()) && password.length() > 0) {
                        editor.putBoolean("isLoggedIn", true).apply();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}