package com.a58070096.patcharaponjoksamut.steamstalker.Singleton;

import android.util.Log;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameCacheModel;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by patcharaponjoksamut on 23/11/2017 AD.
 */

public class AllGameDataCache {

    private static AllGameDataCache instance = new AllGameDataCache();

    public interface SearchForGameResult {
        void onFoundGame(ArrayList<String> result);
        void onNotFound();
    }

    private ArrayList<GameCacheModel> allGame;
    private SearchForGameResult listener;

    public void searchForGame(final String name) {
        Log.v("Debug", "Seatching for " + name);
        if(allGame == null) {
            allGame = new ArrayList<>();
            Log.v("Debug", "First Time Start pull data");
            AndroidNetworking.get("http://api.steampowered.com/ISteamApps/GetAppList/v0002")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray data = response.getJSONObject("applist").getJSONArray("apps");
                                for(int i=0; i<data.length(); i++) {
                                    JSONObject app = data.getJSONObject(i);
                                    GameCacheModel game = new GameCacheModel(app.getString("name"), app.getString("appid"));
                                    allGame.add(game);
                                }
                                Log.v("Debug", "Pull Data Finished");
                                lookupGame(name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });


        } else {
            lookupGame(name);
        }
    }

    private void lookupGame(String name) {
        ArrayList<String> result = new ArrayList<>();
        for(GameCacheModel game: allGame) {
            String lowName = name.toLowerCase();
            String gameName = game.getName().toLowerCase();
            if(gameName.contains(lowName)) {
                result.add(game.getAppId());
            }
        }

        if(result.isEmpty()) {
            listener.onNotFound();
        } else {
            listener.onFoundGame(result);
        }
    }

    public static AllGameDataCache getInstance() {
        return instance;
    }

    public void setListener(SearchForGameResult listener) {
        this.listener = listener;
    }
}
