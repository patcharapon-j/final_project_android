package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;

/**
 * Created by patcharaponjoksamut on 25/11/2017 AD.
 */

public class GameDetailInfoViewModel {

    private GameModel game;

    public GameDetailInfoViewModel(GameModel game) {
        this.game = game;
    }

    public String getGameName() {
        return game.getName();
    }

    public String getGameDescrition() {
        if(game.getDescription().equals("null") || game.getDescription().isEmpty()) {
            return "N/A";
        }
        return game.getDescription();
    }

    public String getGameDeveloper() {
        if(game.getDeveloper().equals("null")) {
            return "N/A";
        }
        String developer = game.getDeveloper();
        developer = developer.replaceAll("\"", "");
        developer = developer.replaceAll("\\[", "");
        developer = developer.replaceAll("]", "");
        return developer;
    }

    public String getGamePublisher() {
        if(game.getPublisher().equals("null")) {
            return "N/A";
        }
        String publisher = game.getPublisher();
        publisher = publisher.replaceAll("\"", "");
        publisher = publisher.replaceAll("\\[", "");
        publisher = publisher.replaceAll("]", "");
        return publisher;
    }

    public String getGameWebsite() {
        if(game.getWebsite().equals("null")) {
            return "N/A";
        }
        return game.getWebsite();
    }

    public String getSupportedOperatingSystem() {
        String output = "";
        if(game.getSupportWindows()) {
            output += "Windows ";
        }

        if(game.getSupportLinux()) {
            output += "Linux ";
        }

        if(game.getSupportMacos()) {
            output += "Mac";
        }

        return output;
    }

    public String getGamePrice() {
        if(game.getPrice() == 0) {
            return "Free";
        } else {
            String output = "";
            output += String.valueOf(game.getPrice() / 100);
            output += ".";
            output += String.format("%02d", game.getPrice() % 100);
            output += "$";

            return output;
        }
    }
}
