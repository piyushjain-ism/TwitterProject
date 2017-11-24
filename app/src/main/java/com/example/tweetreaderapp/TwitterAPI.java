package com.example.tweetreaderapp;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class TwitterAPI {

    private String twitterApiKey;
    private String twitterAPISecret;
    final static String TWITTER_TOKEN_URL = "https://api.twitter.com/oauth2/token";
    final static String TWITTER_STREAM_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";
    final static String TWITTER_TREND_URL = "https://api.twitter.com/1.1/trends/place.json?id=23424848";
    final static String TwitterSearchURL = "https://api.twitter.com/1.1/search/tweets.json?";


    public TwitterAPI(String twitterAPIKey, String twitterApiSecret){
        this.twitterApiKey = twitterAPIKey;
        this.twitterAPISecret = twitterApiSecret;
    }

    public JSONArray getTwitterTweets(String screenName,String type) {
        JSONArray twitterTweetArrayList = null;
        try {
            String twitterUrlApiKey = URLEncoder.encode(twitterApiKey, "UTF-8");
            String twitterUrlApiSecret = URLEncoder.encode(twitterAPISecret, "UTF-8");
            String twitterKeySecret = twitterUrlApiKey + ":" + twitterUrlApiSecret;
            String twitterKeyBase64 = Base64.encodeToString(twitterKeySecret.getBytes(), Base64.NO_WRAP);
            TwitterAuthToken twitterAuthToken = getTwitterAuthToken(twitterKeyBase64);
            twitterTweetArrayList = getTwitterTweets(screenName, twitterAuthToken,type);
        } catch (UnsupportedEncodingException ex) {
        } catch (IllegalStateException ex1) {
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return twitterTweetArrayList;
    }

    public JSONArray getTwitterTweets(String screenName,
                                                    TwitterAuthToken twitterAuthToken,String type) throws JSONException, UnsupportedEncodingException {
        String twitterTweets=null;
        if (twitterAuthToken != null && twitterAuthToken.token_type.equals("bearer")) {
            HttpGet httpGet = null;
            if(type=="tweet")
            httpGet = new HttpGet(TWITTER_STREAM_URL + screenName);
            else if(type=="trend"){
                httpGet = new HttpGet(TWITTER_TREND_URL);
                //String encodedUrl = URLEncoder.encode("ChurchVsNationalists", "UTF-8");
               // httpGet = new HttpGet(TwitterSearchURL + encodedUrl);
            } httpGet.setHeader("Authorization", "Bearer " + twitterAuthToken.access_token);
            httpGet.setHeader("Content-Type", "application/json");
            HttpUtil httpUtil = new HttpUtil();
             twitterTweets = httpUtil.getHttpResponse(httpGet);
        }

        return new JSONArray(twitterTweets);
    }

    public TwitterAuthToken getTwitterAuthToken(String twitterKeyBase64) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(TWITTER_TOKEN_URL);
        httpPost.setHeader("Authorization", "Basic " + twitterKeyBase64);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
        HttpUtil httpUtil = new HttpUtil();
        String twitterJsonResponse = httpUtil.getHttpResponse(httpPost);
        return convertJsonToTwitterAuthToken(twitterJsonResponse);
    }

    private TwitterAuthToken convertJsonToTwitterAuthToken(String jsonAuth) {
        TwitterAuthToken twitterAuthToken = null;
        if (jsonAuth != null && jsonAuth.length() > 0) {
            try {
                Gson gson = new Gson();
                twitterAuthToken = gson.fromJson(jsonAuth, TwitterAuthToken.class);
            } catch (IllegalStateException ex) { }
        }
        return twitterAuthToken;
    }


    private class TwitterAuthToken {
        String token_type;
        String access_token;
    }
}