package com.example.tweetreaderapp;


import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.v7.recyclerview.R.attr.layoutManager;
import static android.support.v7.recyclerview.R.styleable.RecyclerView;


/**
 * Created by piyushjain on 23/11/17.
 */

public class ShowTweet extends Fragment {



  android.support.v7.widget.RecyclerView mRecycleview;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;

    Context mContext;
   final static String ScreeNname = "reliancejio";
    final static String type = "tweet";
    JSONArray tweets = new JSONArray();
    ProgressBar mProgress;
    TextView mNotweet;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v  = inflater.inflate(R.layout.show_tweet, container, false);

        mProgress = (ProgressBar) v.findViewById(R.id.feed_progress);
        mNotweet = (TextView) v.findViewById(R.id.no_tweet);
        mProgress.setVisibility(View.VISIBLE);
        mNotweet.setVisibility(View.GONE);

        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
        if (androidNetworkUtility.isConnected(getContext())) {
            new TwitterAsyncTask().execute(ScreeNname,this);
        } else {
            Toast.makeText(getContext(),"No internet Connection",Toast.LENGTH_LONG).show();
        }
       mRecycleview = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecycleview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.setItemAnimator(new DefaultItemAnimator());
        return v;
    }
    public class TwitterAsyncTask extends AsyncTask<Object, Void, JSONArray> {


        @Override
        protected JSONArray doInBackground(Object... params) {
            JSONArray twitterTweets = null;
                TwitterAPI twitterAPI = new TwitterAPI(NewHomeActivity.TWITTER_API_KEY,NewHomeActivity.TWITTER_API_SECRET);
                twitterTweets = twitterAPI.getTwitterTweets(ScreeNname,type);
            return twitterTweets;
        }

        @Override
        protected void onPostExecute(JSONArray twitterTweets) {

            mProgress.setVisibility(View.GONE);
            tweets =twitterTweets;
            if(tweets!=null) {
                adapter = new ShowTweetAdapter(tweets,getContext());
                mRecycleview.setAdapter(adapter);
            }else {
                mNotweet.setVisibility(View.VISIBLE);
            }

        }
    }

}
