package com.example.teamup.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface APIServer {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAJZkL7aA:APA91bENj1jslnKUFwEkMhHFJ6peETzYLifbonNpbwddTOFWEy2UgchN_909xvU9IJpZCYYxIavIrFuP61WFAA9ZCRaxtAz90nTMY3FEFyay54i4ER02GKQh9ePo9qbCpxFVsbfBDSet"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification (@Body NotificationSender body );
}
