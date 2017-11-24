package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import android.net.Uri;
import android.util.Log;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameCacheModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameTileModel;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by patcharaponjoksamut on 23/11/2017 AD.
 */

public class SteamAPIViewModel {

    public interface SteaAPIVIewModelListener {
        void getTop100GameResponse(ArrayList<GameTileModel> allGame);
        void searchGameResponse(ArrayList<GameTileModel> allGame);
    }

    private SteaAPIVIewModelListener listener;

    public void setListener(SteaAPIVIewModelListener listener) {
        this.listener = listener;
    }

    public void getTop100Game() {

        final ArrayList<GameTileModel> allGame = new ArrayList<>();

        AndroidNetworking.get("https://steamspy.com/api.php?request=top100in2weeks")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int count = 1;
                        final int[] recived = {0};
                        Iterator iter = response.keys();
                        while(iter.hasNext()) {
                            final String appid = (String)iter.next();
                            final int finalCount = count;
                            AndroidNetworking.get("http://store.steampowered.com/api/appdetails?appids={appid}&cc=us&filters=basic")
                                    .addPathParameter("appid", appid)
                                    .setPriority(Priority.HIGH)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            recived[0] += 1;
                                            try {
                                                JSONObject data = response.getJSONObject(appid).getJSONObject("data");
                                                GameTileModel game = new GameTileModel(
                                                        data.getString("name"),
                                                        Uri.parse(data.getString("header_image")),
                                                        appid,
                                                        finalCount);
                                                allGame.add(game);

                                                if(recived[0] >= 100) {
                                                    Collections.sort(allGame, new Comparator<GameTileModel>() {
                                                        @Override
                                                        public int compare(GameTileModel gameTileModel, GameTileModel t1) {
                                                            return gameTileModel.getRank() - t1.getRank();
                                                        }
                                                    });
                                                    listener.getTop100GameResponse(allGame);
                                                }

                                            } catch (JSONException e) {
                                                listener.getTop100GameResponse(new ArrayList<GameTileModel>());
                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            Log.d("Debug", anError.toString());
                                            listener.getTop100GameResponse(new ArrayList<GameTileModel>());
                                        }
                                    });
                            count += 1;
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Debug", anError.toString());
                        listener.getTop100GameResponse(new ArrayList<GameTileModel>());
                    }
                });


    }

    public void getTileForGame(ArrayList<String> games) {

        final ArrayList<GameTileModel> allGame = new ArrayList<>();
        final int[] recived = {0};
        final int count = allGame.size();

        for(final String appid: games) {
            AndroidNetworking.get("http://store.steampowered.com/api/appdetails?appids={appid}&cc=us&filters=basic")
                    .addPathParameter("appid", appid)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            recived[0] += 1;
                            try {
                                JSONObject data = response.getJSONObject(appid).getJSONObject("data");
                                GameTileModel game = new GameTileModel(
                                        data.getString("name"),
                                        Uri.parse(data.getString("header_image")),
                                        appid,
                                        0);
                                allGame.add(game);
                                if(recived[0] >= count) {
                                    Collections.sort(allGame, new Comparator<GameTileModel>() {
                                        @Override
                                        public int compare(GameTileModel gameTileModel, GameTileModel t1) {
                                            return gameTileModel.getRank() - t1.getRank();
                                        }
                                    });
                                    listener.searchGameResponse(allGame);
                                }

                            } catch (JSONException e) {
                                Log.d("Debug", e.toString());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d("Debug", anError.getMessage());
                            //listener.searchGameResponse(new ArrayList<GameTileModel>());
                        }
                    });
        }

    }


}
