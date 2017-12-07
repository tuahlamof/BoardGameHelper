package com.android.crystal.boardgamehelper;

/**
 * Created by huiyu on 11/7/17.
 */

public class GameHistoryModel {
    private String gameName;
    private String gameResult;
    private String gameTime;
    private String gameRole;

    public String getGameName() {return gameName;}
    public String getGameResult() {return gameResult;}
    public String getGameTime() {return gameTime;}
    public String getGameRole() {return gameRole;}

    public void setGameName(String gameName) {this.gameName = gameName;}
    public void setGameResult(String gameResult) {this.gameResult = gameResult;}
    public void setGameTime(String gameTime) {this.gameTime = gameTime;}
    public void setGameRole(String gameRole) {this.gameRole = gameRole;}
}
