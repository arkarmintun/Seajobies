package com.arkarmintun.seajobies.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.model.Agent;
import com.github.clans.fab.FloatingActionButton;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AgentEditActivity extends AppCompatActivity {

    private EditText txtCompanyName;
    private EditText txtManagingDirector;
    private EditText txtAddress;
    private EditText txtPhoneNumbers;
    private FloatingActionButton fabSaveAgent;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        final Intent intent = getIntent();
        final String objectId = intent.getStringExtra("objectId");
        String companyName = intent.getStringExtra("companyName");
        String managingDirector = intent.getStringExtra("managingDirector");
        String address = intent.getStringExtra("address");
        String phoneNumber = intent.getStringExtra("phoneNumbers");

        txtCompanyName = (EditText) findViewById(R.id.text_company_name);
        txtManagingDirector = (EditText) findViewById(R.id.text_managing_director);
        txtAddress = (EditText) findViewById(R.id.text_address);
        txtPhoneNumbers = (EditText) findViewById(R.id.text_phone_numbers);

        txtCompanyName.setText(companyName);
        txtManagingDirector.setText(managingDirector);
        txtAddress.setText(address);
        txtPhoneNumbers.setText(phoneNumber);

        fabSaveAgent = (FloatingActionButton) findViewById(R.id.fab_save_agent);
        fabSaveAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Agent");
                query.getInBackground(objectId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            Agent agent = new Agent();
                            object.put("name", txtCompanyName.getText().toString());
                            object.put("managingDirector", txtManagingDirector.getText().toString());
                            object.put("address", txtAddress.getText().toString());
                            object.put("phoneNumbers", agent.convertStringToJsonPhoneNumbers(txtPhoneNumbers.getText().toString()));
                            object.saveInBackground();
                            Toast.makeText(AgentEditActivity.this, "Successfully Updated.", Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(AgentEditActivity.this, AgentDetailActivity.class);
                            intent1.putExtra("companyName", txtCompanyName.getText().toString());
                            startActivity(intent1);
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
