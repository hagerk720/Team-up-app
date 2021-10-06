package com.example.teamup.homeFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.teamup.Adapters.RecyclerViewAdapter;
import com.example.teamup.Objects.Notification;
import com.example.teamup.Objects.Post;
import com.example.teamup.OnItemClickListener;
import com.example.teamup.R;
import com.example.teamup.SendNotificationPack.APIServer;
import com.example.teamup.SendNotificationPack.Client;
import com.example.teamup.SendNotificationPack.Data;
import com.example.teamup.SendNotificationPack.MyCallBack;
import com.example.teamup.SendNotificationPack.MyResponse;
import com.example.teamup.SendNotificationPack.NotificationSender;
import com.example.teamup.SendNotificationPack.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnItemClickListener{
    TextView textView ;
    RecyclerView recyclerView ;
    DatabaseReference reference ;
    RecyclerViewAdapter adapter ;
    ArrayList<Post> postList ;
    ArrayList<String> keys ;
    NavController navController;
    public static boolean clicked = false ;
    public static APIServer apiServer ;
    String receiverId ;
    //final String Url = "https://fcm.googleapis.com/fcm/send" ;
    private static RequestQueue mRequestQueue ;


    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
       // mRequestQueue = Volley.newRequestQueue(getContext());
        apiServer= Client.getClient("https://fcm.googleapis.com/").create(APIServer.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        // animation
        textView = v.findViewById(R.id.textview);
        Animation animation = AnimationUtils.loadAnimation(getContext() , R.anim.top_header);
        textView.setAnimation(animation);
        //-------------------------------------------------------
        recyclerView = v.findViewById(R.id.homeFragment_recyclerView_posts);
        reference = FirebaseDatabase.getInstance().getReference("Posts");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postList = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new RecyclerViewAdapter(getContext(), postList ,this);
        recyclerView.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Post post = data.getValue(Post.class);
                        postList.add(post);
                        keys.add(data.getKey());
                        adapter.notifyDataSetChanged();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String token = task.getResult();
                UpdateToken(token);
                Log.d("your token" , token );
            }
        });
        ;
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        FloatingActionButton Home_fragment_add_post_btn = view.findViewById(R.id.homeFragment_add_post_btn);
        Home_fragment_add_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections directions = HomeFragmentDirections
                        .actionHomeFragmentToAddPostFragment()
                        .setEditPost("null");
                navController.navigate(directions);
            }
        });
    }

    @Override
    public void onItemClick(int Position , View v ) {
        Toast.makeText(getContext(), "item clicked" + Position, Toast.LENGTH_SHORT).show();
        TextView join = v.findViewById(R.id.join_team_postHomeFragment_tv);


        if (v != join ){
            if (!clicked){
                rearrangeCard(v);
                clicked = true ;
            }
            else{
                closedCard(v);
                clicked =false;
            }
        }
        else{
            Toast.makeText(getContext(), "join clicked", Toast.LENGTH_SHORT).show();
            Post post = postList.get(Position);
            receiverId = post.getPostID();
            getUserName(new MyCallBack() {
                @Override
                public void onCallback(ArrayList<Notification> notifications) {

                }

                @Override
                public void onCallback(String userName) {
                    sendNotification(receiverId , userName , " request to join your team");

                }

            });

            Log.d("receiver id " , receiverId);
            join.setText("UnJoin");
        }

    }

    @Override
    public void OnEditClick(int Position) {

    }


    public static void rearrangeCard(View v){
        TextView numOfTeam = v.findViewById(R.id.num_team_postHomeFragment_tv);
        TextView description = v.findViewById(R.id.team_description_postHomeFragment_tv);
        ViewGroup.LayoutParams params =v.getLayoutParams();
        params.height = 1000 ;
        v.findViewById(R.id.cardView).setLayoutParams(params);
        numOfTeam.setVisibility(View.VISIBLE) ;
        ConstraintLayout constraintLayout = v.findViewById(R.id.constraint);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.team_name_postHomeFragment_tv, ConstraintSet.START,R.id.post_userName_tv ,ConstraintSet.START ,-850 );
        constraintSet.connect(R.id.team_name_postHomeFragment_tv, ConstraintSet.TOP, R.id.post_userName_tv ,ConstraintSet.BOTTOM ,0 );
        constraintSet.connect(R.id.num_team_postHomeFragment_tv, ConstraintSet.BOTTOM, R.id.constraint ,ConstraintSet.BOTTOM ,-400 );
        constraintSet.connect(R.id.join_team_postHomeFragment_tv, ConstraintSet.BOTTOM, R.id.constraint ,ConstraintSet.BOTTOM ,-400 );
        constraintSet.applyTo(constraintLayout);
    }
    public static void closedCard(View v){
        TextView numOfTeam = v.findViewById(R.id.num_team_postHomeFragment_tv);
        TextView description = v.findViewById(R.id.team_description_postHomeFragment_tv);

        ViewGroup.LayoutParams params =v.getLayoutParams();
        params.height = 500 ;
        v.findViewById(R.id.cardView).setLayoutParams(params);
        numOfTeam.setVisibility(View.GONE) ;
        ConstraintLayout constraintLayout = v.findViewById(R.id.constraint);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.team_name_postHomeFragment_tv, ConstraintSet.START,R.id.post_userName_tv ,ConstraintSet.END ,280 );
        constraintSet.connect(R.id.team_name_postHomeFragment_tv, ConstraintSet.TOP, R.id.constraint,ConstraintSet.TOP ,35 );
        constraintSet.connect(R.id.join_team_postHomeFragment_tv, ConstraintSet.BOTTOM, R.id.constraint ,ConstraintSet.BOTTOM ,0 );
        constraintSet.applyTo(constraintLayout);
    }
    private void UpdateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
    }
    public static void sendNotification(String receiver , String name , String msg ){
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query =  allTokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                   Token token = snapshot1.getValue(Token.class);
                   String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                   Log.d("Uid" , user) ;
                    Data data = new Data(user , "Request" , name + ":" +msg
                            ,user ,R.drawable.facebook_icon);

                   NotificationSender sender = new NotificationSender(data , token.getToken());
                    apiServer.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200 ){
                                        if (response.body().success != 1){
                                            Log.d("on Response" , "failed");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

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
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.child("userName").getValue().toString();

                myCallBack.onCallback(userName);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}