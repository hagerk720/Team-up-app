package com.example.teamup.homeFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Adapters.NotificationAdapter;
import com.example.teamup.Objects.Notification;
import com.example.teamup.OnItemClickListener;
import com.example.teamup.R;
import com.example.teamup.SendNotificationPack.APIServer;
import com.example.teamup.SendNotificationPack.Client;
import com.example.teamup.SendNotificationPack.Data;
import com.example.teamup.SendNotificationPack.MyCallBack;
import com.example.teamup.SendNotificationPack.MyResponse;
import com.example.teamup.SendNotificationPack.NotificationSender;
import com.example.teamup.SendNotificationPack.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatFragment extends Fragment implements OnItemClickListener {
    RecyclerView recyclerView;
    DatabaseReference reference;
    NotificationAdapter adapter;
    ArrayList<Notification> notifications;
    ArrayList<String> keys;
    public static APIServer apiServer;


    public chatFragment() {
        // Required empty public constructor
    }

    public static chatFragment newInstance(String param1, String param2) {
        chatFragment fragment = new chatFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        apiServer = Client.getClient("https://fcm.googleapis.com/").create(APIServer.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("read", "chat fragment");

        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = v.findViewById(R.id.notification_rv);
        reference = FirebaseDatabase.getInstance().getReference("Notifications")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notifications = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new NotificationAdapter(getContext(), notifications, keys, this);
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot dataSnapshot = snapshot.child("sentID");
                    String sentId = dataSnapshot.getValue().toString();
                    Log.d("read", sentId);
                    readData(new MyCallBack() {
                        @Override
                        public void onCallback(ArrayList<Notification> notification) {
                            checkAcceptOrRequest();
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCallback(String userName) {

                        }

                    }, sentId);

                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }


    @Override
    public void onItemClick(int Position, View v) {
        TextView accept = v.findViewById(R.id.accept_notifiFragment_tv);
        TextView reject = v.findViewById(R.id.reject_notifiFragment_tv);
        if (v == accept) {

            acceptBtn( "accept your request");

        } else if (v == reject) {
            rejectBtn();
            notifications.remove(Position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnEditClick(int Position) {

    }

    public void readData(MyCallBack myCallBack, String sentId) {

        reference = FirebaseDatabase.getInstance().getReference("Users").child(sentId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("snapshot", snapshot.toString());
                String userName = snapshot.child("userName").getValue().toString();
                String userNickname = snapshot.child("userNickname").getValue().toString();
                Notification notification = new Notification(userName, userNickname);
                notifications.add(notification);
                myCallBack.onCallback(notifications);
                myCallBack.onCallback(userName);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void rejectBtn() {
        reference = FirebaseDatabase.getInstance().getReference("Notifications")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.removeValue();
        adapter.notifyDataSetChanged();

    }

    public void acceptBtn( String msg) {
        reference = FirebaseDatabase.getInstance().getReference("Notifications")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot dataSnapshot = snapshot.child("sentID");
                    String sentId = dataSnapshot.getValue().toString();
                    DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
                    Query query = allTokens.orderByKey().equalTo(sentId);
                    Log.d("read", sentId);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Token token = snapshot1.getValue(Token.class);
                                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Log.d("Uid", user);
                                getUserName(new MyCallBack() {
                                    @Override
                                    public void onCallback(ArrayList<Notification> notifications) {

                                    }

                                    @Override
                                    public void onCallback(String userName) {
                                        Data data = new Data(user, "Accept", userName + ":" + msg
                                                , user, R.drawable.facebook_icon);

                                        NotificationSender sender = new NotificationSender(data, token.getToken());
                                        apiServer.sendNotification(sender)
                                                .enqueue(new Callback<MyResponse>() {
                                                    @Override
                                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                                        if (response.code() == 200) {
                                                            if (response.body().success != 1) {
                                                                Log.d("on Response", "failed");
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<MyResponse> call, Throwable t) {

                                                    }
                                                });
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getUserName(MyCallBack myCallBack){
         reference = FirebaseDatabase.getInstance().getReference("Users")
                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userName");
         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                myCallBack.onCallback(snapshot.getValue().toString());
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
    }
    public void checkAcceptOrRequest(){
        reference =FirebaseDatabase.getInstance().getReference("Notifications")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("msg");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.getValue().toString().equals("Accept")){
                  adapter.AcceptedCard();
                  Log.d("accept" , "true");

              }
              else{
                  Log.d("accept" ,snapshot.getValue().toString() );
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}