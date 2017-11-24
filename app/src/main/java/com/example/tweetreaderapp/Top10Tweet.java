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

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by piyushjain on 23/11/17.
 */

public class Top10Tweet extends Fragment {


    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    android.support.v7.widget.RecyclerView mRecycleview;
    JSONArray tweets = new JSONArray();
    final static String ScreeNname = "Car_Trade";
    final static String type = "trend";

    public Top10Tweet() {
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
        if (androidNetworkUtility.isConnected(getContext())) {
            new TwitterAsyncTask().execute(ScreeNname,this);
        } else {
            Toast.makeText(getContext(),"No internet Connection",Toast.LENGTH_LONG).show();
        }
        View v  = inflater.inflate(R.layout.tweet_ui, container, false);
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

//            tweets =twitterTweets;
//            try {
//                if(tweets!=null)
//               // adapter = new Top10TweetAdapter(tweets.getJSONObject(0).getJSONArray("trends"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            mRecycleview.setAdapter(adapter);

        }
    }
}