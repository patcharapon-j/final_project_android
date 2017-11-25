package com.a58070096.patcharaponjoksamut.steamstalker.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a58070096.patcharaponjoksamut.steamstalker.Activity.HomeActivity;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.NewsTileModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

/**
 * Created by patcharaponjoksamut on 25/11/2017 AD.
 */

public class NewsTileAdapter extends UltimateViewAdapter<NewsTileAdapter.MyViewHolder> {

    private ArrayList<NewsTileModel> allNews;
    private Activity activity;

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.body.setText(allNews.get(position).getBody());
        holder.title.setText(allNews.get(position).getTitle());
        holder.name.setText(allNews.get(position).getName());
        holder.date.setText(allNews.get(position).getDate().toString());
        holder.link = Uri.parse(allNews.get(position).getUrl());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date;
        TextView name;
        TextView title;
        TextView body;
        Uri link;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.news_card_title);
            body = view.findViewById(R.id.news_tile_body);
            date = view.findViewById(R.id.news_card_date);
            name = view.findViewById(R.id.news_card_name);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, link);
            activity.startActivity(myIntent);
        }
    }

    @Override
    public MyViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public MyViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_tile, parent, false);

        return new NewsTileAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getAdapterItemCount() {
        return allNews.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void setAllNews(ArrayList<NewsTileModel> allNews) {
        this.allNews = allNews;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
