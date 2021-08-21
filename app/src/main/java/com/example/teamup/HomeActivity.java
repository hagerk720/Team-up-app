package com.example.teamup;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.teamup.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
ActivityHomeBinding activityHomeBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
        Animation animation = AnimationUtils.loadAnimation(this ,R.anim.top_header);
        activityHomeBinding.linearLayout.setAnimation(animation);
        activityHomeBinding.homeActivityAccountImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityHomeBinding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        activityHomeBinding.navigationView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.homeActivity_nav_hostFragment);
        NavigationUI.setupWithNavController(activityHomeBinding.navigationView , navController);
    }
}