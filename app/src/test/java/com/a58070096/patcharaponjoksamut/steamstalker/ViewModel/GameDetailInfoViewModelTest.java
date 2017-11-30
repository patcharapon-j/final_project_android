package com.a58070096.patcharaponjoksamut.steamstalker.ViewModel;

import com.a58070096.patcharaponjoksamut.steamstalker.Model.GameModel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by patcharaponjoksamut on 30/11/2017 AD.
 */
public class GameDetailInfoViewModelTest {

    @Test
    public void gameDetailWebsiteNullTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setWebsite("null");
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("N/A", viewModel.getGameWebsite());
    }

    @Test
    public void gameDetailDeveloperNullTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setDeveloper("null");
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("N/A", viewModel.getGameDeveloper());
    }

    @Test
    public void gameDetailPublisherNullTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setPublisher("null");
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("N/A", viewModel.getGamePublisher());
    }

    @Test
    public void gameDetailDescriptionNullTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setDescription("null");
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("N/A", viewModel.getGameDescrition());
    }

    @Test
    public void gameDetailPriceCoversionNormalTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setPrice(1299);
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("12.99$", viewModel.getGamePrice());
    }

    @Test
    public void gameDetailPriceCoversionCentTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setPrice(99);
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("0.99$", viewModel.getGamePrice());
    }

    @Test
    public void gameDetailPriceCoversionFreeTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setPrice(0);
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("Free", viewModel.getGamePrice());
    }

    @Test
    public void gameDetailPriceCoversionLeadingZeroTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setPrice(1009);
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("10.09$", viewModel.getGamePrice());
    }

    @Test
    public void gameDetailSupportedPlatformOneTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setSupportWindows(true);
        gameModel.setSupportMacos(false);
        gameModel.setSupportLinux(false);
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("Windows ", viewModel.getSupportedOperatingSystem());
    }

    @Test
    public void gameDetailSupportedPlatformTwoTest() throws Exception {
        GameModel gameModel = new GameModel();
        gameModel.setSupportWindows(true);
        gameModel.setSupportMacos(true);
        gameModel.setSupportLinux(false);
        GameDetailInfoViewModel viewModel = new GameDetailInfoViewModel(gameModel);
        assertEquals("Windows Mac", viewModel.getSupportedOperatingSystem());
    }

}