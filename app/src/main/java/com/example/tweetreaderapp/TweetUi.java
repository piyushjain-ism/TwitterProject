package com.example.tweetreaderapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.json.JSONArray;

/**
 * Created by piyushjain on 24/11/17.
 */

public class TweetUi extends Fragment {


    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    android.support.v7.widget.RecyclerView mRecycleview;

    public TweetUi() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();

        View v  = inflater.inflate(R.layout.tweet_ui, container, false);
        mRecycleview = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecycleview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.setItemAnimator(new DefaultItemAnimator());

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("reliancejio")
                .build();

        final TweetTimelineRecyclerViewAdapter adapter = new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                .setTimeline(userTimeline)
                .build();

        mRecycleview.setAdapter(adapter);
        return v;
    }

}