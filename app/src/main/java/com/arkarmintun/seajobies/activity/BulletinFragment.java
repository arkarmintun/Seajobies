package com.arkarmintun.seajobies.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.adapter.BulletinAdapter;
import com.arkarmintun.seajobies.helper.EndlessRecyclerViewScrollListener;
import com.arkarmintun.seajobies.helper.Helper;
import com.arkarmintun.seajobies.helper.Timestamp;
import com.arkarmintun.seajobies.model.Bulletin;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BulletinFragment extends Fragment {

    private List<Bulletin> feeds = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    public BulletinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_bulletin, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_bulletin);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(inflater.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Bulletin");
                query.orderByDescending("createdAt");
                query.include("createdBy");
                query.setLimit(10);
                query.setSkip(totalItemsCount);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for (int i = 0; i < objects.size(); i++) {
                                final Bulletin feed = new Bulletin();
                                feed.content = objects.get(i).getString("content");
                                feed.url = objects.get(i).getString("url");

                                if (objects.get(i).getParseFile("photo") != null) {
                                    feed.photoUrl = objects.get(i).getParseFile("photo").getUrl();
                                } else {
                                    feed.photoUrl = "";
                                }

                                feed.timestamp = Timestamp.getTimeAgo(objects.get(i).getCreatedAt());

                                ParseObject agent = objects.get(i).getParseObject("createdBy");
                                feed.name = agent.getString("name");

                                feeds.add(feed);
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.srl_bulletin);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                feeds.clear();
                mAdapter.notifyDataSetChanged();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Bulletin");
                query.orderByDescending("createdAt");
                query.include("createdBy");
                query.setLimit(10);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for (int i = 0; i < objects.size(); i++) {
                                final Bulletin feed = new Bulletin();
                                feed.content = objects.get(i).getString("content");
                                feed.url = objects.get(i).getString("url");

                                if (objects.get(i).getParseFile("photo") != null) {
                                    feed.photoUrl = objects.get(i).getParseFile("photo").getUrl();
                                } else {
                                    feed.photoUrl = "";
                                }

                                feed.timestamp = Timestamp.getTimeAgo(objects.get(i).getCreatedAt());

                                ParseObject agent = objects.get(i).getParseObject("createdBy");
                                feed.name = agent.getString("name");

                                feeds.add(feed);
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        if (Helper.isNetworkAvailable(getContext())) {
            getData();
        }

        mAdapter = new BulletinAdapter(feeds);

        mRecyclerView.setAdapter(mAdapter);

        return layout;
    }

    private void getData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Bulletin");
        query.orderByDescending("createdAt");
        query.include("createdBy");
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        final Bulletin feed = new Bulletin();
                        feed.content = objects.get(i).getString("content");
                        feed.url = objects.get(i).getString("url");

                        if (objects.get(i).getParseFile("photo") != null) {
                            feed.photoUrl = objects.get(i).getParseFile("photo").getUrl();
                        } else {
                            feed.photoUrl = "";
                        }

                        feed.timestamp = Timestamp.getTimeAgo(objects.get(i).getCreatedAt());

                        ParseObject agent = objects.get(i).getParseObject("createdBy");
                        feed.name = agent.getString("name");

                        feeds.add(feed);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
