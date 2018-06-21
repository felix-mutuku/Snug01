package com.snugjar.truthordare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class InternetActivity extends AppCompatActivity {
    Button buttonRetry, buttonSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        buttonRetry = findViewById(R.id.buttonRetry);
        buttonSkip = findViewById(R.id.buttonSkip);

        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InternetActivity.this, SplashActivity.class));
                InternetActivity.this.finish();
            }
        });

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InternetActivity.this, WelcomeActivity.class));
                InternetActivity.this.finish();
            }
        });
    }
}
