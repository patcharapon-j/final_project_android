package com.a58070096.patcharaponjoksamut.steamstalker.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameTileModel;
import com.a58070096.patcharaponjoksamut.steamstalker.R;
import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

/**
 * Created by patcharaponjoksamut on 23/11/2017 AD.
 */

public class GameTileAdapter extends UltimateViewAdapter<GameTileAdapter.MyViewHolder> {

    private List<GameTileModel> gameList;
    private Activity activity;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.game_tile_image);
            name = view.findViewById(R.id.game_tile_text);
        }
    }

    public GameTileAdapter(List<GameTileModel> gameList) {
        this.gameList = gameList;
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
                .inflate(R.layout.game_tile, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getAdapterItemCount() {
        return gameList.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GameTileModel gameTileModel = gameList.get(position);
        holder.name.setText(gameTileModel.getName());
        Glide.with(activity).load(gameTileModel.getImgUrl()).into(holder.image);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setGameList(List<GameTileModel> gameList) {
        this.gameList = gameList;
    }
}
