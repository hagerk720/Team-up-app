package com.example.teamup.Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
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

    protected Post(Parcel in) {
        teamTitle = in.readString();
        numOfTeam = in.readString();
        postDesc = in.readString();
        userName = in.readString();
        userNickname = in.readString();
        postID = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teamTitle);
        dest.writeString(numOfTeam);
        dest.writeString(postDesc);
        dest.writeString(userName);
        dest.writeString(userNickname);
        dest.writeString(postID);
    }
}
