package com.arkarmintun.seajobies.activity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.adapter.AgentAdapter;
import com.arkarmintun.seajobies.adapter.CertificateAdapter;
import com.arkarmintun.seajobies.helper.Helper;
import com.arkarmintun.seajobies.helper.SimpleDividerItemDecoration;
import com.arkarmintun.seajobies.model.Agent;
import com.arkarmintun.seajobies.model.Certificate;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private List<Certificate> certificates = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView txtUserProfileName, txtUserProfileEmail;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);

        Typeface robotoRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");

        ParseUser user = ParseUser.getCurrentUser();

        txtUserProfileName = (TextView) layout.findViewById(R.id.user_profile_name);
        txtUserProfileName.setText(user.getUsername());
        txtUserProfileName.setTypeface(robotoRegular);

        txtUserProfileEmail = (TextView) layout.findViewById(R.id.user_profile_email);
        txtUserProfileEmail.setText(user.getEmail());
        txtUserProfileEmail.setTypeface(robotoRegular);

        TextView rvTitle = (TextView) layout.findViewById(R.id.rv_title);
        rvTitle.setTypeface(robotoRegular);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_certificate);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(inflater.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (Helper.isNetworkAvailable(getContext())) {
            getData();
        }

        mAdapter = new CertificateAdapter(certificates);

        mRecyclerView.setAdapter(mAdapter);

        return layout;
    }

    private void getData() {
        certificates.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Certificate");
        query.whereEqualTo("createdBy", ParseUser.getCurrentUser());
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Certificate certificate = new Certificate();
                        certificate.objectId = objects.get(i).getObjectId();
                        certificate.certificateName = objects.get(i).getString("certificateName");
                        certificate.certificateNo = objects.get(i).getString("certificateNo");
                        certificate.issueDate = certificate.convertDatetoString(objects.get(i).getDate("issueDate"));
                        certificate.expiryDate = certificate.convertDatetoString(objects.get(i).getDate("expiryDate"));
                        certificates.add(certificate);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
