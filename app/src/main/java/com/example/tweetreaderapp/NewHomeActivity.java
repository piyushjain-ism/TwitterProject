package com.example.tweetreaderapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.twitter.sdk.android.core.Twitter;

/**
 * Created by piyushjain on 24/11/17.
 */

public class NewHomeActivity extends AppCompatActivity {


   final static String TWITTER_API_KEY = "SWQ49sXSN5A9Udpqyj9mUuPW5";
    final static String TWITTER_API_SECRET = "UYGWF3kNue55PbsUhMfNIH5mVGtXEIm3bDMWpuarXo4pkf3WHi";
    Button mShowtweet,mTop10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Twitter.initialize(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reliance Jio(@reliancejio)");

        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
        if (!androidNetworkUtility.isConnected(this))
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_LONG).show();

            mShowtweet = (Button) findViewById(R.id.showtweet);

            mTop10 = (Button) findViewById(R.id.top10);

            mShowtweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(NewHomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            mTop10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(NewHomeActivity.this, Top10TweetsActivity.class);
                    startActivity(intent);
                }
            });


        }
    }


