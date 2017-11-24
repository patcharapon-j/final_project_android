package com.a58070096.patcharaponjoksamut.steamstalker.Model;

import android.net.Uri;

import org.parceler.Parcel;

/**
 * Created by patcharaponjoksamut on 24/11/2017 AD.
 */

@Parcel
public class GameModel {
    String name;
    String appId;
    String description;
    Uri headerImageUrl;
    String website;
    String developer;
    String publisher;
    Boolean supportWindows;
    Boolean supportLinux;
    Boolean SupportMacos;
    int metacriticScore;
    String releaseDate;
    Boolean isComingSoon;
    int rank;
    int owners;
    int playerIn2Weeks;
    int price;
    String type;

    public GameModel() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(Uri headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Boolean getSupportWindows() {
        return supportWindows;
    }

    public void setSupportWindows(Boolean supportWindows) {
        this.supportWindows = supportWindows;
    }

    public Boolean getSupportLinux() {
        return supportLinux;
    }

    public void setSupportLinux(Boolean supportLinux) {
        this.supportLinux = supportLinux;
    }

    public Boolean getSupportMacos() {
        return SupportMacos;
    }

    public void setSupportMacos(Boolean supportMacos) {
        SupportMacos = supportMacos;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getComingSoon() {
        return isComingSoon;
    }

    public void setComingSoon(Boolean comingSoon) {
        isComingSoon = comingSoon;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getOwners() {
        return owners;
    }

    public void setOwners(int owners) {
        this.owners = owners;
    }

    public int getPlayerIn2Weeks() {
        return playerIn2Weeks;
    }

    public void setPlayerIn2Weeks(int playerIn2Weeks) {
        this.playerIn2Weeks = playerIn2Weeks;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
