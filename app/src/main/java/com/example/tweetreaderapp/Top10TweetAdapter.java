package com.example.tweetreaderapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by piyushjain on 23/11/17.
 */

public class Top10TweetAdapter extends RecyclerView.Adapter<Top10TweetAdapter.MyViewHolder> {

private JSONArray dataSet;
    Context c;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewName;

    public MyViewHolder(View itemView) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.tweet_name);
          }
}

    public Top10TweetAdapter(JSONArray data, Context context) {
        this.c = context;
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top10_tweet_item, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;

        try {
            textViewName.setText(dataSet.getJSONObject(listPosition).getString("name"));

            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                       // dialogBox(dataSet.getJSONObject(listPosition).getString("name"));
                        Intent intent = new Intent(c,HashTagsActivity.class);
                        intent.putExtra("query",dataSet.getJSONObject(listPosition).getString("name"));
                        c.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.length();
    }


    public void dialogBox(String s) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
        alertDialogBuilder.setMessage(s);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}