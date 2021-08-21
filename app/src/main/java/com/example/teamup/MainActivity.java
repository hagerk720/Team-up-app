package com.example.teamup;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teamup.databinding.ActivityMainBinding;

import static com.example.teamup.R.drawable.buttons_selected;
import static com.example.teamup.R.drawable.login_buttons;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(findViewById(R.id.fragment2));
        binding.loginSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.logInFragment);

                binding.loginSelectionButton.setBackgroundResource(buttons_selected);
                binding.signUpSelectionButton.setBackgroundResource(login_buttons);
                binding.mainActivityLogInBtn.setText("LOG IN");

            }
        });
        binding.signUpSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.signUpFragment);
                binding.loginSelectionButton.setBackgroundResource(login_buttons);
                binding.signUpSelectionButton.setBackgroundResource(buttons_selected);
                binding.mainActivityLogInBtn.setText("SIGN UP");
            }
        });
        binding.mainActivityLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}