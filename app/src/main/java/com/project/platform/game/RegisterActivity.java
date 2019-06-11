package com.project.platform.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miretsky.ron.myapp.R;

public class RegisterActivity extends AppCompatActivity {

    Button submitButton;
    Button submitButton2;
    EditText passwordEditText;
    EditText confirmEditText;
    EditText EmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsister);
        submitButton = findViewById(R.id.button_submit_regsister);
        passwordEditText = findViewById(R.id.edit_text_password);
        confirmEditText = findViewById(R.id.edit_text_confirm);
        EmailEditText = findViewById(R.id.edit_text_email);
        submitButton2 = findViewById(R.id.button_submit_LogIn_Main);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (EmailEditText.getText().toString().length() > 0) {

                    if (passwordEditText.getText().toString().equals(confirmEditText.getText().toString()) && passwordEditText.length() > 0) {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Invalid E-Mail Address", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submitButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}