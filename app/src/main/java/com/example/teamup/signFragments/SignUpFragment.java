package com.example.teamup.signFragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teamup.HomeActivity;
import com.example.teamup.User;
import com.example.teamup.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    FragmentSignUpBinding binding;
    private FirebaseAuth auth;
    public DatabaseReference reference ;
    public FirebaseDatabase firebaseDatabase ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }


    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.signUpFragmentSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectData();

            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void collectData() {

        String Email = binding.SignUpFragmentEmailEditT.getText().toString();
        String userName = binding.SignUpFragmentUserNameEditT.getText().toString();
        String password = binding.SignUpFragmentPasswordEditT.getText().toString();
        String confirmPassword = binding.SignUpFragmentConfirmPasswordEditT.getText().toString();
        if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(getContext(), "complete info", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "password not identical", Toast.LENGTH_SHORT).show();
        } else {
            createUser(Email, password, userName);
        }
    }

    public void createUser(String email, String password, String userName) {
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity() ,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "user created", Toast.LENGTH_SHORT).show();
                    User user = new User(email, userName);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "user info added", Toast.LENGTH_SHORT).show();
                                RetrievingUserDataFromFirebase();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void RetrievingUserDataFromFirebase() {
    //    Toast.makeText(getContext(), "i am here ", Toast.LENGTH_SHORT).show();

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
