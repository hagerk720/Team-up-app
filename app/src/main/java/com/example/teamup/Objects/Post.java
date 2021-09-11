package com.example.teamup.Objects;

public class Post {
    public String teamTitle , numOfTeam , postDesc ,userName ,userNickname ,postID;

    public Post(String teamTitle, String numOfTeam, String postDesc, String userName, String userNickname, String postID) {
        this.teamTitle = teamTitle;
        this.numOfTeam = numOfTeam;
        this.postDesc = postDesc;
        this.userName = userName;
        this.userNickname = userNickname;
        this.postID = postID;
    }

    public Post(String teamTitle, String numOfTeam, String postDesc) {
        this.teamTitle = teamTitle;
        this.numOfTeam = numOfTeam;
        this.postDesc = postDesc;
    }

    public Post(String teamTitle, String numOfTeam, String postDesc, String userName) {
        this.teamTitle = teamTitle;
        this.numOfTeam = numOfTeam;
        this.postDesc = postDesc;
        this.userName = userName;
    }

    public Post(String teamTitle, String numOfTeam, String postDesc, String userName, String userNickname) {
        this.teamTitle = teamTitle;
        this.numOfTeam = numOfTeam;
        this.postDesc = postDesc;
        this.userName = userName;
        this.userNickname = userNickname;
    }


    public Post() {
    }

    public String getTeamTitle() {
        return teamTitle;
    }

    public void setTeamTitle(String teamTitle) {
        this.teamTitle = teamTitle;
    }

    public String getNumOfTeam() {
        return numOfTeam;
    }

    public void setNumOfTeam(String numOfTeam) {
        this.numOfTeam = numOfTeam;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
