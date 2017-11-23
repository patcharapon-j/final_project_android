package com.a58070096.patcharaponjoksamut.steamstalker.Model;

import android.net.Uri;

/**
 * Created by patcharaponjoksamut on 23/11/2017 AD.
 */

public class GameTileModel {

    private String name;
    private Uri imgUrl;

    public GameTileModel(String name, Uri imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
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

    public void setImgUrl(Uri imgUrl) {
        this.imgUrl = imgUrl;
    }
}
