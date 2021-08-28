package com.example.teamup.signFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teamup.HomeActivity;
import com.example.teamup.databinding.FragmentLogInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment {
    FragmentLogInBinding binding ;
    private FirebaseAuth auth ;

    public LogInFragment() {
        // Required empty public constructor
    }

    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater , container , false) ;
        View view = binding.getRoot() ;
        binding.logInFragmentLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectData();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    public void collectData(){
        String Email = binding.logInFragmentUserNameEditT.getText().toString();
        String password = binding.logInFragmentPasswordEditT.getText().toString();
        logInUser(Email,password);
    }
    public void logInUser(String Email , String password){
        auth = FirebaseAuth.getInstance();
      auth.signInWithEmailAndPassword(Email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
          @Override
          public void onSuccess(AuthResult authResult) {
              Toast.makeText(getContext(), "success login", Toast.LENGTH_SHORT).show();
              startActivity(new Intent( getContext() , HomeActivity.class));

          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
    }
}