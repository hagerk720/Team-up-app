package com.example.teamup;

import static com.example.teamup.R.drawable.buttons_selected;
import static com.example.teamup.R.drawable.login_buttons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.teamup.databinding.ActivityMainBinding;
import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding ;
    private CallbackManager callbackManager ;
    private FirebaseAuth firebaseAuth ;
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

            }
        });
        binding.signUpSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.signUpFragment);
                binding.loginSelectionButton.setBackgroundResource(login_buttons);
                binding.signUpSelectionButton.setBackgroundResource(buttons_selected);


            }
        });
        binding.faceBookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , FacebookAuthActivity.class));
                //finish();
            }
        });


    }
}