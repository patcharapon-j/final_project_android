package com.a58070096.patcharaponjoksamut.steamstalker.Model;

import android.net.Uri;

/**
 * Created by patcharaponjoksamut on 23/11/2017 AD.
 */

public class GameTileModel {

    private String name;
    private Uri imgUrl;
    private String appId;
    private int rank;

    public GameTileModel(String name, Uri imgUrl, String appId, int rank) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.appId = appId;
        this.rank = rank;
    }

    public String getAppId() {
        return appId;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImgUrl() {
        return imgUrl;
    }

}
