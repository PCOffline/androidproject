package com.project.platform.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miretsky.ron.myapp.R;

public class ConfirmCodeActivity extends AppCompatActivity {

    Button submitButton;
    EditText CodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);
        submitButton = findViewById(R.id.button_submit_Confirm_Code);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CodeEditText.length() > 0) {
                    Intent intent = new Intent(ConfirmCodeActivity.this, NewPassActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ConfirmCodeActivity.this, "Code incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });
        CodeEditText = findViewById(R.id.edit_text_password_Confirm_Code);
    }
}