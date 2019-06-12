package com.project.platform.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    private final String secureKey = new RandomString().nextString();
    Button submitEmail;
    EditText email;
    Button submitCode;
    EditText code;
    Button submitPassword;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Send Code to Mail

        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.email);
        submitEmail = findViewById(R.id.submit_email);
        code = findViewById(R.id.code);
        submitCode = findViewById(R.id.submit_code);
        password = findViewById(R.id.password);
        submitPassword = findViewById(R.id.submit_password);
        final ForgotPasswordActivity context = ForgotPasswordActivity.this;

        submitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.length() > 0 && email.getText().toString().contains("@")) {
                    sendEmail();
                    changeView(1);
                } else {
                    Toast.makeText(context, "Invalid Mail", Toast.LENGTH_LONG).show();
                }
            }
        });

        submitCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText().toString().equals(secureKey))
                    changeView(2);
                else {
                    Toast.makeText(context, "Invalid Code", Toast.LENGTH_LONG).show();
                }
            }
        });

        submitPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = context.password.getText().toString();
                Player player = MainActivity.player;

                DatabaseManager.update(player, new Player(player.getUsername(), password));
                player.setPassword(password);
            }
        });

    }

    protected void sendEmail() {

        String[] TO = {email.getText().toString()};
        String[] CC = {};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setDataAndType(Uri.parse("mailto:"), "text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Password Reset");
        emailIntent.putExtra(Intent.EXTRA_TEXT, secureKey);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ForgotPasswordActivity.this,
                    "There is no email client installed.", Toast.LENGTH_LONG).show();
        }
    }

    private void changeView(int view) {
        switch (view) {
            case 0:
                break;
            case 1:
                email.setVisibility(View.INVISIBLE);
                submitEmail.setVisibility(View.INVISIBLE);
                code.setVisibility(View.INVISIBLE);
                submitCode.setVisibility(View.INVISIBLE);
                break;
            case 2:
                code.setVisibility(View.INVISIBLE);
                submitCode.setVisibility(View.INVISIBLE);
                password.setVisibility(View.VISIBLE);
                submitPassword.setVisibility(View.VISIBLE);
                break;
        }
    }
}
