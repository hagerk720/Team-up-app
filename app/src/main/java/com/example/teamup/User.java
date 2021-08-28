package com.example.teamup;

public class User {
    String Email , UserName ;

    public User(String email, String userName) {
        Email = email;
        UserName = userName;
    }

    public User() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}