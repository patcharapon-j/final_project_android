package com.a58070096.patcharaponjoksamut.steamstalker.Model;

import android.net.Uri;

import java.util.Date;

/**
 * Created by patcharaponjoksamut on 25/11/2017 AD.
 */

public class NewsTileModel {

    private String title;
    private String body;
    private String url;
    private String name;
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body.replaceAll("\\<.*?>","");;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
