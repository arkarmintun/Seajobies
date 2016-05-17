package com.arkarmintun.seajobies.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.helper.Helper;
import com.arkarmintun.seajobies.model.Agent;
import com.github.clans.fab.FloatingActionButton;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CertificateEditActivity extends AppCompatActivity {

    private EditText txtCertificateName, txtCertificateNo;
    private TextView txtIssueDate, txtExpiryDate;
    private int mYear, mMonth, mDay;
    private Date issueDate, expiryDate;
    String objectId, strIssueDate, strExpiryDate;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtCertificateName = (EditText) findViewById(R.id.text_certificate_name);
        txtCertificateNo = (EditText) findViewById(R.id.text_certificate_number);
        txtIssueDate = (TextView) findViewById(R.id.text_issue_date);
        txtExpiryDate = (TextView) findViewById(R.id.text_expiry_date);

        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        txtCertificateName.setText(intent.getStringExtra("txtCertificateName"));
        txtCertificateNo.setText(intent.getStringExtra("txtCertificateNo"));

        strIssueDate = intent.getStringExtra("txtIssueDate");
        strExpiryDate = intent.getStringExtra("txtExpiryDate");

        Log.e("ISSUE DATE ", strIssueDate);


        txtIssueDate.setText(strIssueDate);
        txtExpiryDate.setText(strExpiryDate);

        txtIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CertificateEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtIssueDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CertificateEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtExpiryDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        FloatingActionButton fabSaveCertificate = (FloatingActionButton) findViewById(R.id.fab_save_certificate);
        fabSaveCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.startLoading(CertificateEditActivity.this, "Saving...");
                final String certificateName = txtCertificateName.getText().toString();
                final String certificateNo = txtCertificateNo.getText().toString();

                if (certificateName.equals("") || certificateNo.equals("")) {
                    Snackbar.make(v, "Please Fill Certificate Name and Number.", Snackbar.LENGTH_LONG).show();
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Certificate");

                    query.getInBackground(objectId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, com.parse.ParseException e) {
                            if (e == null) {
                                String strIssueDate = txtIssueDate.getText().toString();
                                String strExpiryDate = txtExpiryDate.getText().toString();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                try {
                                    issueDate = dateFormat.parse(strIssueDate);
                                    expiryDate = dateFormat.parse(strExpiryDate);
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }

                                object.put("certificateName", certificateName);
                                object.put("certificateNo", certificateNo);
                                object.put("issueDate", issueDate);
                                object.put("expiryDate", expiryDate);
                                object.put("createdBy", ParseUser.getCurrentUser());

                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(com.parse.ParseException e) {
                                        Toast.makeText(CertificateEditActivity.this, "Successfully Updated.", Toast.LENGTH_LONG).show();
                                        finish();
                                        Helper.stopLoading();
                                    }
                                });
                            } else {
                                Toast.makeText(CertificateEditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
