package com.example.teamup.Objects;

public class Notification {
    String userName , userNickName ;

    public Notification() {
    }

    public Notification(String userName, String userNickName) {
        this.userName = userName;
        this.userNickName = userNickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
}
