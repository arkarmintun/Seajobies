package com.arkarmintun.seajobies.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.helper.Helper;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignupActivity extends AppCompatActivity {

    private EditText inputName;
    private EditText inputEmail;
    private EditText inputPassword;
    private String txtName;
    private String txtEmail;
    private String txtPassword;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // Short Usage for Snack
    public void Snacker(String msg) {
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        Snackbar.make(scrollView, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputName = (EditText) findViewById(R.id.text_name);
        inputEmail = (EditText) findViewById(R.id.text_email);
        inputPassword = (EditText) findViewById(R.id.text_password);

        AppCompatButton btnSignup = (AppCompatButton) findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName = inputName.getText().toString();
                txtEmail = inputEmail.getText().toString();
                txtPassword = inputPassword.getText().toString();

                if (Helper.isNetworkAvailable(v.getContext())) {
                    if (txtName.equals("") || txtEmail.equals("") || txtPassword.equals("")) {
                        Snacker("Please complete the login form.");
                    } else {
                        Helper.startLoading(v.getContext(), "Signing up...");

                        ParseUser user = new ParseUser();
                        user.setUsername(txtName);
                        user.setEmail(txtEmail);
                        user.setPassword(txtPassword);
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Snacker("Successfully signed up, please verify your email.");
                                } else {
                                    Snacker(e.getMessage());
                                }
                                Helper.stopLoading();
                            }
                        });
                    }
                } else {
                    Snacker("Network Unavailable.");
                }
            }
        });
    }
}
