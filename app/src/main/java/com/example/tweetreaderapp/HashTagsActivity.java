package com.example.tweetreaderapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rajesh Kumar on 24-11-2017.
 */

public class HashTagsActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    android.support.v7.widget.RecyclerView mRecycleview;
    JSONArray tweets = new JSONArray();
    private static String ScreeNname = "";
    final static String type = "search";
    ProgressBar mProgress;
    TextView mNotweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hashtag);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reliance Jio(@reliancejio)");

        String query = getIntent().getExtras().getString("query");
        query = query.replace("#","");
        query = query.replace(" ","");

        ScreeNname ="q="+query+"&result_type=popular";
      //  ScreeNname = "q=FridayFeeling&result_type=popular";

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
           new TwitterAsyncTask().execute(ScreeNname);

        } else {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_LONG).show();
        }
    }


    private class TwitterAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... screenNames) {
            String result = null;

            if (screenNames.length > 0) {
                result = getTwitterStream(screenNames[0]);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            mProgress.setVisibility(View.GONE);
            Log.e("result is ", "<><><" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("statuses");

                if(jsonArray!=null && jsonArray.length()>0){
                    adapter = new ShowTweetAdapter(jsonArray,HashTagsActivity.this);
                    mRecycleview.setAdapter(adapter);
                }else
                    mNotweet.setVisibility(View.VISIBLE);

             } catch (Exception e) {
                e.printStackTrace();
            }
        }

           private Authenticated jsonToAuthenticated(String rawAuthorization) {
            Authenticated auth = null;
            if (rawAuthorization != null && rawAuthorization.length() > 0) {
                try {
                    Log.e("rawAuthorization", "" + rawAuthorization);
                    Gson gson = new Gson();
                    auth = gson.fromJson(rawAuthorization, Authenticated.class);
                } catch (IllegalStateException ex) {
                    // just eat the exception
                }
            }
            return auth;
        }

        private String getResponseBody(HttpRequestBase request) {
            StringBuilder sb = new StringBuilder();
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpResponse response = httpClient.execute(request);
                int statusCode = response.getStatusLine().getStatusCode();
                String reason = response.getStatusLine().getReasonPhrase();

                if (statusCode == 200) {

                    HttpEntity entity = response.getEntity();
                    InputStream inputStream = entity.getContent();

                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sb.append(line);
                    }
                } else {
                    sb.append(reason);
                }
            } catch (UnsupportedEncodingException ex) {
            } catch (ClientProtocolException ex1) {
            } catch (IOException ex2) {
            }
            return sb.toString();
        }

        private String getTwitterStream(String screenName) {
            String results = null;

            // Step 1: Encode consumer key and secret
            try {
                // URL encode the consumer key and secret
                String urlApiKey = URLEncoder.encode(NewHomeActivity.TWITTER_API_KEY, "UTF-8");
                String urlApiSecret = URLEncoder.encode(NewHomeActivity.TWITTER_API_SECRET, "UTF-8");

                // Concatenate the encoded consumer key, a colon character, and the
                // encoded consumer secret
                String combined = urlApiKey + ":" + urlApiSecret;

                // Base64 encode the string
                String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

                // Step 2: Obtain a bearer token
                HttpPost httpPost = new HttpPost(TwitterAPI.TWITTER_TOKEN_URL);
                httpPost.setHeader("Authorization", "Basic " + base64Encoded);
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
                String rawAuthorization = getResponseBody(httpPost);
                Authenticated auth = jsonToAuthenticated(rawAuthorization);

                // Applications should verify that the value associated with the
                // token_type key of the returned object is bearer
                if (auth != null && auth.token_type.equals("bearer")) {

                    // Step 3: Authenticate API requests with bearer token
                    HttpGet httpGet = new HttpGet(TwitterAPI.TwitterSearchURL + screenName);
//                    HttpGet httpGet = new HttpGet(TwitterStreamURL );

                    // construct a normal HTTPS request and include an Authorization
                    // header with the value of Bearer <>
                    httpGet.setHeader("Authorization", "Bearer " + auth.access_token);
                    httpGet.setHeader("Content-Type", "application/json");
                    // update the results with the body of the response
                    results = getResponseBody(httpGet);
                }
            } catch (UnsupportedEncodingException ex) {
            } catch (IllegalStateException ex1) {
            }
            return results;
        }
    }
}