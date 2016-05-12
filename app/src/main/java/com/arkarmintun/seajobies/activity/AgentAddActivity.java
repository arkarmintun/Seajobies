package com.arkarmintun.seajobies.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.model.Agent;
import com.parse.ParseObject;

import org.json.JSONArray;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AgentAddActivity extends AppCompatActivity {

    private String txtCompanyName;
    private String txtManagingDirector;
    private String txtAddress;
    private JSONArray txtPhoneNumbers;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final EditText textCompanyName = (EditText) findViewById(R.id.text_company_name);
        final EditText textManagingDirector = (EditText) findViewById(R.id.text_managing_director);
        final EditText textAddress = (EditText) findViewById(R.id.text_address);
        final EditText textPhoneNumbers = (EditText) findViewById(R.id.text_phone_numbers);
        AppCompatButton btnAdd = (AppCompatButton) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agent agent = new Agent();
                txtCompanyName = textCompanyName.getText().toString();
                txtManagingDirector = textManagingDirector.getText().toString();
                txtAddress = textAddress.getText().toString();
                txtPhoneNumbers = agent.convertStringToJsonPhoneNumbers(textPhoneNumbers.getText().toString());
                Log.e("PHONE NUMBERS", txtPhoneNumbers.toString());

                if (txtCompanyName.equals("") && txtManagingDirector.equals("") && txtAddress.equals("")) {
                    Snackbar.make(v, "Please fill the form.", Snackbar.LENGTH_LONG).show();
                } else {
                    ParseObject poAgent = new ParseObject("Agent");
                    poAgent.put("name", txtCompanyName);
                    poAgent.put("managingDirector", txtManagingDirector);
                    poAgent.put("address", txtAddress);
                    poAgent.put("phoneNumbers", txtPhoneNumbers);
                    poAgent.saveInBackground();
                    textCompanyName.setText("");
                    textManagingDirector.setText("");
                    textAddress.setText("");
                    textPhoneNumbers.setText("");
                    Snackbar.make(v, "Company Successfully added.", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
