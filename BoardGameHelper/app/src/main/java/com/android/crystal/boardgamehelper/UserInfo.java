package com.android.crystal.boardgamehelper;

import java.util.ArrayList;

/**
 * Created by huiyu on 12/5/17.
 */

public class UserInfo {
    private String nickName = "me";
    private int id;
    private int roomNum;
    private int roleCode;
    private int seatId;
    private boolean isHost = false;
    private boolean hasRescue = false;
    private boolean isDead = false;
    private boolean hasPoison = false;

    private static UserInfo userInstance;
    private static ArrayList<UserInfo> usersArray;

    public void setHasPoison(boolean hasPoison) {
        this.hasPoison = hasPoison;
    }
    public boolean getHasPoison() {
        return this.hasPoison;
    }
    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
    public boolean getDead() {
        return this.isDead;
    }
    public void setHasRescue(boolean hasRescue) {
        this.hasRescue = hasRescue;
    }
    public boolean getHasRescue() {
        return this.hasRescue;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
    public int getSeatId() {
        return this.seatId;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }
    public int getRoomNum() {
        return this.roomNum;
    }
    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }
    public int getRoleCode() {
        //    平民      1
        //    狼人      2
        //    预言家    3
        //    女巫      4
        //    守卫      5
        return this.roleCode;
    }
    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }
    public boolean getHost() {
        return this.isHost;
    }
    public static UserInfo getUserInstance() {
        if (userInstance == null) {
            userInstance = new UserInfo();

        }
        return userInstance;
    }
    public static ArrayList<UserInfo> getUsersArray() {
        if (usersArray == null) {
            usersArray = new ArrayList<>();
            //usersArray.add(getUserInstance());
        }
        return usersArray;
    }
}
