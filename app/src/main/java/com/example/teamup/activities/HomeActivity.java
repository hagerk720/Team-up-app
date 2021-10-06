package com.example.teamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.teamup.R;
import com.example.teamup.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
ActivityHomeBinding activityHomeBinding ;
    NavController navController ;
    DatabaseReference reference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
        Animation animation = AnimationUtils.loadAnimation(this , R.anim.top_header);
        activityHomeBinding.linearLayout.setAnimation(animation);
        Intent intent = getIntent();

        String bundle = intent.getStringExtra("userId");
        if (bundle != null){
            Log.d("id" , bundle);
        }

        activityHomeBinding.homeActivityAccountImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityHomeBinding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        activityHomeBinding.navigationView.setItemIconTintList(null);
        navController = Navigation.findNavController(this, R.id.homeActivity_nav_hostFragment);
        NavigationUI.setupWithNavController(activityHomeBinding.navigationView , navController);
        userName();
    }
    public void userName(){
        reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.child("userName").getValue().toString();
                String userNickname = snapshot.child("userNickname").getValue().toString();
                View HeaderView =   activityHomeBinding.navigationView.getHeaderView(0);
                TextView userNameTv= HeaderView.findViewById(R.id.navigation_userName_tv);
                TextView userPhoto = HeaderView.findViewById(R.id.navigation_profile_image);
                activityHomeBinding.homeActivityAccountImg.setText(userNickname);
                userNameTv.setText(userName) ;
                userPhoto.setText(userNickname);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}