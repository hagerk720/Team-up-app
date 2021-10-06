package com.example.teamup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Objects.Notification;
import com.example.teamup.OnItemClickListener;
import com.example.teamup.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    Context context ;
    ArrayList<Notification> notifications ;
    ArrayList<String> keys ;
    private final OnItemClickListener listener;
    NotificationViewHolder mHolder ;
    int mPosition ;


    public NotificationAdapter(Context context, ArrayList<Notification> notifications,
                               ArrayList<String> keys, OnItemClickListener listener) {
        this.context = context;
        this.notifications = notifications;
        this.keys = keys;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(context).inflate(R.layout.notification_card, parent, false);

        return new NotificationViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        mHolder = holder ;
       // mPosition=position ;
     Notification notification = notifications.get(position);
     holder.userName.setText(notification.getUserName());
     holder.Desc.setText(" request to join your team ");
     holder.userNickName.setText(notification.getUserNickName());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView userName , userNickName , accept , reject , Desc ;
        OnItemClickListener listener ;
        public NotificationViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            userName =itemView.findViewById(R.id.notifi_userName_tv_notifiFragment);
            userNickName = itemView.findViewById(R.id.profile_photo_in_notifiFragment);
            accept = itemView.findViewById(R.id.accept_notifiFragment_tv);
            reject = itemView.findViewById(R.id.reject_notifiFragment_tv);
            Desc = itemView.findViewById(R.id.notifi_desc_tv_notifiFragment);
            this.listener = listener ;
            accept.setOnClickListener(this);
            reject.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition() , v );

        }
    }
    public void AcceptedCard(){
       mPosition = mHolder.getAdapterPosition();
        Notification notification = notifications.get(mPosition);
        mHolder.userName.setText(notification.getUserName());
        mHolder.Desc.setText(" Accept your request");
        mHolder.userNickName.setText(notification.getUserNickName());
        mHolder.reject.setVisibility(View.GONE);
        mHolder.accept.setVisibility(View.GONE);
    }

}
