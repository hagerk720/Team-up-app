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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment {
    FragmentLogInBinding binding ;
    private FirebaseAuth auth ;
    public DatabaseReference reference ;


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
              RetrievingUserDataFromFirebase();
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
    }
    public void RetrievingUserDataFromFirebase() {

        reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    String userName = snapshot.child("userName").getValue().toString();
                    Toast.makeText(getContext(), userName + " Sign up fragment ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    intent.putExtra("userName" , userName) ;
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "onCancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}