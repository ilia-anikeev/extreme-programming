package com.listManager.model;

import lombok.Getter;

@Getter
public class User {

    private Integer userID;

    private final String username;

    private final String password;

    public User (String username, String password){
        this.username = username;
        this.password = password;
    }

    public User (String username, String password, int userID){
        this.username = username;
        this.password = password;
        this.userID = userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}