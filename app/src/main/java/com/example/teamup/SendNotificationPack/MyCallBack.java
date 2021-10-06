package com.example.teamup.SendNotificationPack;

import com.example.teamup.Objects.Notification;

import java.util.ArrayList;

public interface MyCallBack {
    void onCallback(ArrayList<Notification> notifications);
    void onCallback(String userName);
}
