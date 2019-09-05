package com.example.android.FriendsFinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        String name = sharedPreferences.getString("userName", "");
        String pass = sharedPreferences.getString("password", "");

        if (!name.equals("") && !pass.equals("")){
            startActivity( new Intent(this, SignIn.class));
        }

    }

    public void signInPage(View view) {
        startActivity(new Intent(this, SignIn.class));
    }

    public void signUpPage(View view) {
        startActivity(new Intent(this, SignUp.class));

    }
}
