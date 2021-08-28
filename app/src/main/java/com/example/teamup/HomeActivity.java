package com.example.teamup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

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
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        Toast.makeText(getApplicationContext(), userName, Toast.LENGTH_SHORT).show();
        View HeaderView =   activityHomeBinding.navigationView.getHeaderView(0);
        TextView userNameTv= HeaderView.findViewById(R.id.navigation_userName_tv);
        userNameTv.setText(userName) ;

    }
}