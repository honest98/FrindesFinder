package com.example.android.FriendsFinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartPage extends AppCompatActivity {

    private final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartPage.this,MainActivity.class);
                StartPage.this.startActivity(intent);
                StartPage.this.finish();
            }
        },SPLASH_TIME);
    }
}

