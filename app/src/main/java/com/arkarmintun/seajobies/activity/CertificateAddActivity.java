package com.arkarmintun.seajobies.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.helper.Helper;
import com.github.clans.fab.FloatingActionButton;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CertificateAddActivity extends AppCompatActivity {

    private EditText txtCertificateName, txtCertificateNo;
    private TextView txtIssueDate, txtExpiryDate;
    private int mYear, mMonth, mDay;
    private Date issueDate, expiryDate;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtIssueDate = (TextView) findViewById(R.id.text_issue_date);
        txtExpiryDate = (TextView) findViewById(R.id.text_expiry_date);

        txtIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CertificateAddActivity.this,
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(CertificateAddActivity.this,
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
                txtCertificateName = (EditText) findViewById(R.id.text_certificate_name);
                txtCertificateNo = (EditText) findViewById(R.id.text_certificate_number);

                String certificateName = txtCertificateName.getText().toString();
                String certificateNo = txtCertificateNo.getText().toString();
                if (certificateName.equals("") || certificateNo.equals("")) {
                    Snackbar.make(v, "Please Fill Certificate Name and Number.", Snackbar.LENGTH_LONG).show();
                } else {
                    ParseObject certificate = new ParseObject("Certificate");

                    String strIssueDate = txtIssueDate.getText().toString();
                    String strExpiryDate = txtExpiryDate.getText().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        issueDate = dateFormat.parse(strIssueDate);
                        expiryDate = dateFormat.parse(strExpiryDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Helper.startLoading(CertificateAddActivity.this, "Saving...");
                    certificate.put("certificateName", certificateName);
                    certificate.put("certificateNo", certificateNo);
                    certificate.put("issueDate", issueDate);
                    certificate.put("expiryDate", expiryDate);
                    certificate.put("createdBy", ParseUser.getCurrentUser());
                    certificate.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            Helper.stopLoading();
                            Intent intent = new Intent(CertificateAddActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
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
