package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import android.util.Log;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.NewsQueryModel;
import com.a58070096.patcharaponjoksamut.steamstalker.Model.NewsTileModel;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by patcharaponjoksamut on 25/11/2017 AD.
 */

public class SteamNewsViewModel {

    public interface OnGetNewsForAppResponse {
        void onGetNewsResult(ArrayList<NewsTileModel> result);
    }

    private OnGetNewsForAppResponse listener;

    public void getNewsForApp(final ArrayList<NewsQueryModel> allNewsQuery) {

        final ArrayList<NewsTileModel> result = new ArrayList<>();

        final int[] count = {0};

        for(NewsQueryModel queryModel: allNewsQuery) {
            String appId = queryModel.getAppId();
            final String name = queryModel.getAppName();
            Log.v("Debug", "Start fetching news for " + appId + " " + name);
            AndroidNetworking.get("  http://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?appid={appid}&count=20&maxlength=200")
                    .addPathParameter("appid", appId)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray data = response.getJSONObject("appnews").getJSONArray("newsitems");
                                for(int i=0; i<data.length(); i++) {
                                    NewsTileModel news = new NewsTileModel();
                                    JSONObject newsResponse = data.getJSONObject(i);
                                    news.setBody(newsResponse.getString("contents"));
                                    news.setTitle(newsResponse.getString("title"));
                                    news.setName(name);
                                    news.setDate(new Date(newsResponse.getLong("date") * 1000));
                                    news.setUrl(newsResponse.getString("url"));
                                    result.add(news);
                                }
                            } catch (JSONException e) {
                                Log.v("Debug", e.toString());
                                listener.onGetNewsResult(null);
                                return;
                            }

                            count[0] += 1;
                            if(count[0] >=allNewsQuery.size()) {
                                Collections.sort(result, new Comparator<NewsTileModel>() {
                                    @Override
                                    public int compare(NewsTileModel newsTileModel, NewsTileModel t1) {
                                        return t1.getDate().compareTo(newsTileModel.getDate());
                                    }
                                });
                                listener.onGetNewsResult(result);
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.v("Debug", anError.getErrorCode() + "");
                            listener.onGetNewsResult(null);
                            return;
                        }
                    });
        }
    }

    public void setListener(OnGetNewsForAppResponse listener) {
        this.listener = listener;
    }
}
