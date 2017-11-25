package com.a58070096.patcharaponjoksamut.steamstalker.Model;

/**
 * Created by patcharaponjoksamut on 23/11/2017 AD.
 */

public class GameCacheModel {

    private String name;
    private String appId;

    public GameCacheModel(String name, String appId) {
        this.name = name;
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
