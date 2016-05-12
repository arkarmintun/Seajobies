package com.arkarmintun.seajobies.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arkarmintun.seajobies.R;
import com.github.clans.fab.FloatingActionButton;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AgentDetailActivity extends AppCompatActivity {

    private TextView txtCompanyName;
    private TextView txtManagingDirector;
    private TextView txtAddress;
    private TextView txtPhoneNumbers;
    private FloatingActionButton fabEditAgent;
    private String objectId;
    private ArrayList<String> data = new ArrayList<String>();
    private String[] phnumbers;
    private RelativeLayout rlPhoneNumbers;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        final Intent intent = getIntent();
        String companyName = intent.getStringExtra("companyName");

        txtCompanyName = (TextView) findViewById(R.id.text_company_name);
        txtManagingDirector = (TextView) findViewById(R.id.text_managing_director);
        txtAddress = (TextView) findViewById(R.id.text_address);
        txtPhoneNumbers = (TextView) findViewById(R.id.text_phone_numbers);
        fabEditAgent = (FloatingActionButton) findViewById(R.id.fab_edit_agent);
        rlPhoneNumbers = (RelativeLayout) findViewById(R.id.rl_phone_numbers);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Agent");
        query.whereEqualTo("name", companyName);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    objectId = object.getObjectId();

                    JSONArray array = object.getJSONArray("phoneNumbers");
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            data.add(array.getString(i));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    phnumbers = data.toArray(new String[data.size()]);

                    txtCompanyName.setText(object.getString("name"));
                    txtManagingDirector.setText(object.getString("managingDirector"));
                    txtAddress.setText(object.getString("address"));
                    txtPhoneNumbers.setText(data.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
                } else {
                    Log.e("Message", e.getMessage());
                }
            }
        });

        rlPhoneNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AgentDetailActivity.this);
                builder.setTitle("Choose a phone number...");
                builder.setItems(phnumbers, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedPhNumber = Arrays.asList(phnumbers).get(which);
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + selectedPhNumber));
                        startActivity(callIntent);
                    }
                });
                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });

        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("_Role");
        query1.whereEqualTo("name", "Administrator");
        query1.whereEqualTo("users", user);
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    fabEditAgent.setVisibility(View.VISIBLE);
                }
            }
        });

        fabEditAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AgentDetailActivity.this, AgentEditActivity.class);
                intent1.putExtra("objectId", objectId);
                intent1.putExtra("companyName", txtCompanyName.getText());
                intent1.putExtra("managingDirector", txtManagingDirector.getText());
                intent1.putExtra("address", txtAddress.getText());
                intent1.putExtra("phoneNumbers", txtPhoneNumbers.getText());
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
