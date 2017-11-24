package com.example.tweetreaderapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by piyushjain on 24/11/17.
 */

public class Top10TweetsActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    android.support.v7.widget.RecyclerView mRecycleview;
    JSONArray tweets = new JSONArray();
    final static String ScreeNname = "jio_money";
    final static String type = "trend";
    ProgressBar mProgress;
    TextView mNotweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top10_tweet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reliance Jio(@reliancejio)");
        mRecycleview = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycleview.setHasFixedSize(true);
        mProgress = (ProgressBar) findViewById(R.id.feed_progress);
        mNotweet = (TextView) findViewById(R.id.no_tweet);
        mProgress.setVisibility(View.VISIBLE);
        mNotweet.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(this);
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.setItemAnimator(new DefaultItemAnimator());

        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
        if (androidNetworkUtility.isConnected(this)) {
            new TwitterAsyncTask().execute(ScreeNname,this);
        } else {
            Toast.makeText(this,"No internet Connection",Toast.LENGTH_LONG).show();
        }
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
            try {
                if(tweets!=null){
                    adapter = new Top10TweetAdapter(tweets.getJSONObject(0).getJSONArray("trends"),Top10TweetsActivity.this);
                    mRecycleview.setAdapter(adapter);
                }else
                    mNotweet.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}