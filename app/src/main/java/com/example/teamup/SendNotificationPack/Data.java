package com.example.teamup.SendNotificationPack;

public class Data {
    String user , title , body  ,sent ;
    int icon  ;

    public Data() {
    }

    public Data(String user, String title, String body, String sent, int icon) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.sent = sent;
        this.icon = icon;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
