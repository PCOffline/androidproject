package com.project.platform.Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miretsky.ron.myapp.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button submitButton;
    EditText EmailForgotdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        submitButton = findViewById(R.id.button_submit_forgot_password);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmailForgotdEditText.length() > 0) {
                    Intent intent = new Intent(ForgotPasswordActivity.this, ConfirmCodeActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Email incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });
        EmailForgotdEditText = findViewById(R.id.edit_text_email_forgot_password);

    }
}
