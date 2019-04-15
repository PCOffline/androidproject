package com.miretsky.ron.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPassActivity extends AppCompatActivity {

    Button submitButton;
    EditText passwordEditText;
    EditText confirmPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        submitButton = findViewById(R.id.button_submit_NewPass);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (confirmPasswordText.getText().toString().equals(passwordEditText.getText().toString()) && passwordEditText.length() > 0) {
                    Intent intent = new Intent(NewPassActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(NewPassActivity.this, "Your password has been saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewPassActivity.this, "Sorry password are not equals", Toast.LENGTH_SHORT).show();
                }
            }
        });
        passwordEditText = findViewById(R.id.edit_text_password_NewPass);
        confirmPasswordText = findViewById(R.id.edit_text_password_Confirm_NewPass);
    }
}
