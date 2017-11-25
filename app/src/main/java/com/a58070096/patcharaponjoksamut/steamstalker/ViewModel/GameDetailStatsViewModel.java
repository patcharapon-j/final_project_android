package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;

/**
 * Created by patcharaponjoksamut on 25/11/2017 AD.
 */

public class GameDetailStatsViewModel {

    private GameModel game;

    public GameDetailStatsViewModel(GameModel game) {
        this.game = game;
    }

    public String getGameRank() {
        if(game.getRank() == -1) {
            return "N/A";
        } else {
            return String.valueOf(game.getRank()) + "%";
        }
    }

    public String getGameMetacritic() {
        if(game.getMetacriticScore() == -1) {
            return "N/A";
        } else {
            return String.valueOf(game.getMetacriticScore()) + "/100";
        }
    }

    public String getGamePlayer() {
        return String.format("%,d", game.getPlayerIn2Weeks());
    }

    public String getOwner() {
        return String.format("%,d", game.getOwners());
    }

    public String getRating() {
        return "N/A";
    }
}
