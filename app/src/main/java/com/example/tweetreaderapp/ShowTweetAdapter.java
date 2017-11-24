package com.example.tweetreaderapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by piyushjain on 23/11/17.
 */

public class ShowTweetAdapter  extends RecyclerView.Adapter<ShowTweetAdapter.MyViewHolder> {

    private JSONArray dataSet;
    Context c;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView image;
        TextView textTitle;
        TextView tweetId;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.createdDate);
            this.image = (ImageView) itemView.findViewById(R.id.thumb);
            this.textTitle = (TextView) itemView.findViewById(R.id.Title);
            this.tweetId = (TextView) itemView.findViewById(R.id.tweetid);
          //  this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public ShowTweetAdapter(JSONArray data,Context c) {
        this.c = c;
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView title = holder.textTitle;
        ImageView image = holder.image;
        TextView tweetid = holder.tweetId;
        String url = "";
        try {
            url = dataSet.getJSONObject(listPosition).getJSONObject("users").getJSONArray("media").getJSONObject(0).getString("media_url_https");
            //url = dataSet.getJSONObject(listPosition).getJSONObject("users").getString("profile_image_url_https");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Glide
                .with(c)
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .crossFade()
                .into(image);



        try {
            tweetid.setText("Tweet Id : "+dataSet.getJSONObject(listPosition).getInt("id"));
            title.setText(dataSet.getJSONObject(listPosition).getString("text"));
            textViewName.setText("Created On : "+dataSet.getJSONObject(listPosition).getString("created_at"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewVersion.setText(dataSet.get(listPosition).getVersion());
       // imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.length();
    }
}