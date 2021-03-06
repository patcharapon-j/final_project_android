package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import android.net.Uri;
import android.util.Log;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameCacheModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;
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
        void getGameDetailResponse(GameModel game);
        void onSteamAccessDenied();
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
                                            if(anError.getErrorCode() == 403 || anError.getErrorCode() == 429) {
                                                listener.onSteamAccessDenied();
                                                return;
                                            }
                                            Log.v("Debug", String.valueOf(anError.getErrorCode()));
                                            Log.d("Debug", anError.getMessage());
                                            listener.getTop100GameResponse(new ArrayList<GameTileModel>());
                                        }
                                    });
                            count += 1;
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if(anError.getErrorCode() == 403 || anError.getErrorCode() == 429) {
                            listener.onSteamAccessDenied();
                            return;
                        }
                        Log.v("Debug", String.valueOf(anError.getErrorCode()));
                        Log.d("Debug", anError.getMessage());
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
                                if(data.getString("type").equals("game")){
                                    allGame.add(game);
                                }

                                if(recived[0] >= count) {
                                    Collections.sort(allGame, new Comparator<GameTileModel>() {
                                        @Override
                                        public int compare(GameTileModel gameTileModel, GameTileModel t1) {
                                            return gameTileModel.getName().compareTo(t1.getName());
                                        }
                                    });
                                    listener.searchGameResponse(allGame);
                                    return;
                                }

                            } catch (JSONException e) {
                                Log.d("Debug", e.toString());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            if(anError.getErrorCode() == 403 || anError.getErrorCode() == 429) {
                                listener.onSteamAccessDenied();
                                return;
                            }
                            Log.v("Debug", String.valueOf(anError.getErrorCode()));
                            Log.d("Debug", anError.getMessage());
                            listener.searchGameResponse(new ArrayList<GameTileModel>());
                            return;
                        }
                    });
        }

    }

    public void getGameDetails(String appId) {
        getGameDetailFromSteam(appId);
    }

    private void getGameDetailFromSteam(final String appId) {
        AndroidNetworking.get("http://store.steampowered.com/api/appdetails?appids={appid}&cc=us")
                .addPathParameter("appid", appId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        GameModel game = new GameModel();
                        try {
                            JSONObject data = response.getJSONObject(appId).getJSONObject("data");
                            game.setType(data.getString("type"));
                            game.setName(data.getString("name"));
                            game.setAppId(data.getString("steam_appid"));
                            game.setDescription(data.getString("short_description"));
                            game.setHeaderImageUrl(Uri.parse(data.getString("header_image")));
                            game.setWebsite(data.getString("website"));
                            game.setDeveloper(data.getString("developers"));
                            game.setPublisher(data.getString("publishers"));
                            try {
                                int score = data.getJSONObject("metacritic").getInt("score");
                                game.setMetacriticScore(score);
                            } catch (Exception e){
                                game.setMetacriticScore(-1);
                            }
                            game.setReleaseDate(data.getJSONObject("release_date").getString("date"));
                            game.setComingSoon(data.getJSONObject("release_date").getBoolean("coming_soon"));
                            game.setSupportLinux(data.getJSONObject("platforms").getBoolean("linux"));
                            game.setSupportMacos(data.getJSONObject("platforms").getBoolean("mac"));
                            game.setSupportWindows(data.getJSONObject("platforms").getBoolean("windows"));
                            getGameDetailsFromSteamSpy(appId, game);
                        } catch (JSONException e) {
                            Log.v("Debugs1", e.getMessage());
                            listener.getGameDetailResponse(null);
                            return;
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if(anError.getErrorCode() == 403 || anError.getErrorCode() == 429) {
                            Log.v("Debugs2", "Steam Block");
                            listener.onSteamAccessDenied();
                            return;
                        }
                        Log.v("Debugs2", String.valueOf(anError.getErrorCode()));
                        Log.v("Debugs2", anError.getMessage());
                        listener.getGameDetailResponse(null);
                        return;
                    }
                });
    }


    private void getGameDetailsFromSteamSpy(String appId, final GameModel game) {
        AndroidNetworking.get("https://steamspy.com/api.php?request=appdetails&appid={appid}")
                .addPathParameter("appid", appId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            try {
                                game.setRank(response.getInt("score_rank"));
                            } catch (Exception e) {
                                game.setRank(-1);
                            }
                            game.setOwners(response.getInt("owners"));
                            game.setPlayerIn2Weeks(response.getInt("players_2weeks"));
                            game.setPrice(response.getInt("price"));
                        } catch (JSONException e) {
                            Log.v("Debugs3", e.getMessage());
                            listener.getGameDetailResponse(null);
                            return;
                        }

                        listener.getGameDetailResponse(game);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if(anError.getErrorCode() == 403 || anError.getErrorCode() == 429) {
                            listener.onSteamAccessDenied();
                            return;
                        }
                        Log.v("Debugs4", String.valueOf(anError.getErrorCode()));
                        Log.v("Debugs4", anError.getMessage());
                        listener.getGameDetailResponse(null);
                        return;
                    }
                });
    }


}
