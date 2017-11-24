package com.example.tweetreaderapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import org.json.JSONArray;

/**
 * Created by piyushjain on 24/11/17.
 */

public class ShowTweetOptions  extends Fragment {


    android.support.v7.widget.RecyclerView mRecycleview;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;

    Context mContext;
    LinearLayout mLayout;

    Button mTweetui,mTweetapi;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.show_tweet_option, container, false);
        mLayout = (LinearLayout) v.findViewById(R.id.buttonlayout);
        mTweetui =  (Button) v.findViewById(R.id.tweetui);

        mTweetapi = (Button) v.findViewById(R.id.tweetapi);

        mTweetui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mTweetapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

        return v;
    }
}