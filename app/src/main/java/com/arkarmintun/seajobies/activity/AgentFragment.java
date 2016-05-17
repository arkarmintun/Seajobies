package com.arkarmintun.seajobies.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.adapter.AgentAdapter;
import com.arkarmintun.seajobies.helper.EndlessRecyclerViewScrollListener;
import com.arkarmintun.seajobies.helper.Helper;
import com.arkarmintun.seajobies.helper.SimpleDividerItemDecoration;
import com.arkarmintun.seajobies.model.Agent;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentFragment extends Fragment {

    private List<Agent> agents = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    public AgentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_agent, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_agent);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(inflater.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.srl_agent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                agents.clear();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Agent");
                query.setLimit(1000);
                query.orderByAscending("name");
                query.selectKeys(Arrays.asList("name"));
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for (int i = 0; i < objects.size(); i++) {
                                if (!objects.get(i).getString("name").equals("Seajobies")) {
                                    Agent agent = new Agent();
                                    agent.name = objects.get(i).getString("name");
                                    agents.add(agent);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Something wrong", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        if (Helper.isNetworkAvailable(getContext())) {
            getData();
        }

        mAdapter = new AgentAdapter(agents);

        mRecyclerView.setAdapter(mAdapter);

        return layout;
    }

    private void getData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Agent");
        Helper.startLoading(getContext(), "Fetching Data...");
        query.setLimit(1000);
        query.orderByAscending("name");
        query.selectKeys(Arrays.asList("name"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        if (!objects.get(i).getString("name").equals("Seajobies")) {
                            Agent agent = new Agent();
                            agent.name = objects.get(i).getString("name");
                            agents.add(agent);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Helper.stopLoading();
            }
        });
    }

}
