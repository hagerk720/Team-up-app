package com.example.teamup;

public class User {
    public String email , userName ;

    public User(String email, String userName) {
        this.email = email;
        this . userName = userName;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
    }
}
