package com.arkarmintun.seajobies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {

            // If user is anonymous, send the user to LoginActivity.class

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {

            // If current user is NOT anonymous user, get current user data from parse

            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {

                // Send logged in users to Welcome.class

                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            } else {

                // Send user to LoginActivity.class


                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
