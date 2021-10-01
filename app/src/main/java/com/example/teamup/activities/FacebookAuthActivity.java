package com.example.teamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.teamup.Objects.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class FacebookAuthActivity extends MainActivity {
    private CallbackManager callbackManager ;
    private FirebaseAuth firebaseAuth ;
    private FirebaseAuth.AuthStateListener authStateListener ;
    private AccessTokenTracker accessTokenTracker ;
    public DatabaseReference reference ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        Toast.makeText(FacebookAuthActivity.this, "here", Toast.LENGTH_SHORT).show();
        firebaseAuth =FirebaseAuth.getInstance();
        LoginManager.getInstance().logInWithReadPermissions(this , Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(FacebookAuthActivity.this, "on Success", Toast.LENGTH_SHORT).show();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(FacebookAuthActivity.this, "on Error", Toast.LENGTH_SHORT).show();
                    }
                });
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 FirebaseUser user = firebaseAuth.getCurrentUser();
                // if (user != null) Toast.makeText(FacebookAuthActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(FacebookAuthActivity.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    //Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    addToUsers(user);
                    updateUI(user);
                }
                else{
                    Toast.makeText(FacebookAuthActivity.this, "signInWithCredential:failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FacebookAuthActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent intent = new Intent(FacebookAuthActivity.this , HomeActivity.class);
            intent.putExtra("userName" , user.getDisplayName());
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@NonNull Intent data) {

            callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }
    public void addToUsers(FirebaseUser user){
        User u = new User(user.getEmail() , user.getDisplayName() ,"hk") ;
        FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid()).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "user add tp users", Toast.LENGTH_SHORT).show();
            }
        });
    }
}