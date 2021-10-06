package com.example.teamup.homeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teamup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;


public class ProfileFragment extends Fragment {
    TextView userName ;
    EditText userEmail  ;
    TextView changePassword ;
    EditText newPassword ;
    RoundedImageView profile ;
    RoundedImageView editProfilePhoto;
    ImageView editUserName ;
    Button save_btn ;
    Button change_email_btn ;
    DatabaseReference reference ;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        inflateView(view);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user_name = snapshot.child("userName").getValue().toString();
                String email =" ";
                if (user != null) {
                    for (UserInfo profile : user.getProviderData()) {
                        email = profile.getEmail();
                    }
                }
                userName.setText(user_name);
                userEmail.setText(email);
                userEmail.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        change_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail.setEnabled(true);

            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword.setVisibility(v.VISIBLE);

            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updateEmail(userEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                userEmail.setEnabled(false);
                            }
                        });
                if (newPassword.getVisibility() == View.GONE ){
                }
                else if (newPassword.getVisibility() == View.VISIBLE && newPassword.getText().toString().equals(" ")){
                    Toast.makeText(getContext(), "write your new password", Toast.LENGTH_SHORT).show();
                }
                else{
                    user.updatePassword(newPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext(), "password updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                    newPassword.setVisibility(View.GONE);
                }

            }
        });

        return view ;
    }
    public void inflateView(View view){
       userName = view.findViewById(R.id.profileFragment_user_name_tv);
       userEmail = view.findViewById(R.id.profileFragment_email_address_et) ;
       changePassword = view.findViewById(R.id.profileFragment_change_pass);
       save_btn = view.findViewById(R.id.profileFragment_save_btn);
       change_email_btn = view.findViewById(R.id.profileFragment_change_email_btn);
       newPassword = view.findViewById(R.id.profileFragment_newPassword_et);
    }
}