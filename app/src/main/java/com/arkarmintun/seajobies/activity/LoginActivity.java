package com.arkarmintun.seajobies.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.helper.Helper;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private EditText inputNameOrEmail;
    private EditText inputPassword;
    private String txtNameOrEmail;
    private String txtPassword;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // Short Usage for Toast
    public void Toaster(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    // Short Usage for Snack
    public void Snacker(String msg) {
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        Snackbar.make(scrollView, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputNameOrEmail = (EditText) findViewById(R.id.text_name_or_email);
        inputPassword = (EditText) findViewById(R.id.text_password);
        AppCompatButton btnLogin = (AppCompatButton) findViewById(R.id.btn_login);
        AppCompatButton btnGotoSignup = (AppCompatButton) findViewById(R.id.btn_goto_signup);

        // when the user clicks LOGIN BUTTON
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNameOrEmail = inputNameOrEmail.getText().toString();
                txtPassword = inputPassword.getText().toString();

                // check network (internet)
                if (Helper.isNetworkAvailable(v.getContext())) {

                    // check fields are not blanks
                    if (txtNameOrEmail.equals("") || txtPassword.equals("")) {
                        Snacker("Please complete the login form.");
                    } else {

                        // Proceeding the login process
                        Helper.startLoading(v.getContext(), "Logging in...");

                        // if the user login with email, fetch their username cuz parse use username
                        if (txtNameOrEmail.contains("@")) {
                            Log.e("Login", "Login with email");
                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                            query.whereEqualTo("email", txtNameOrEmail);
                            query.getFirstInBackground(new GetCallback<ParseUser>() {
                                @Override
                                public void done(ParseUser object, ParseException e) {
                                    if (e == null) {
                                        String txtUsername = object.getString("username");
                                        ParseUser.logInInBackground(txtUsername, txtPassword, new LogInCallback() {
                                            @Override
                                            public void done(ParseUser user, ParseException e) {
                                                if (user != null) {
                                                    // If user exist and authenticated, send user to WelcomeActivity.class
                                                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                                                    startActivity(intent);
                                                    Toaster("Successfully logged in.");
                                                    finish();
                                                } else {
                                                    Snacker("Invalid username/password.");
                                                }
                                            }
                                        });
                                    } else {
                                        Snacker("Invalid username/password.");
                                    }
                                    Helper.stopLoading();
                                }
                            });
                        } else {
                            Log.e("Login", "Login with username");
                            ParseUser.logInInBackground(txtNameOrEmail, txtPassword, new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null) {
                                        // If user exist and authenticated, send user to WelcomeActivity.class
                                        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                                        startActivity(intent);
                                        Toaster("Successfully logged in.");
                                        finish();
                                    } else {
                                        Snacker("Invalid username/password.");
                                    }
                                    Helper.stopLoading();
                                }
                            });
                        }
                    }
                } else {
                    Snacker("Network Unavailable.");
                }
            }
        });

        btnGotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
