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
import androidx.navigation.NavController;

import com.example.teamup.HomeActivity;
import com.example.teamup.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
FragmentSignUpBinding binding ;
private FirebaseAuth auth ;
NavController navController ;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        binding = FragmentSignUpBinding.inflate(inflater ,container , false);
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
        binding = null ; 
    }
    public void collectData(){

        String Email = binding.SignUpFragmentEmailEditT.getText().toString();
        String userName = binding.SignUpFragmentUserNameEditT.getText().toString();
        String password = binding.SignUpFragmentPasswordEditT.getText().toString();
        String confirmPassword = binding.SignUpFragmentConfirmPasswordEditT.getText().toString();
        if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(userName) ||TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPassword) ){
            Toast.makeText(getContext(), "complete info", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirmPassword)){
            Toast.makeText(getContext(), "password not identical", Toast.LENGTH_SHORT).show();
        }

        else{
            createUser(Email ,password);
        }
    }
    public void createUser(String email , String password){
        auth = FirebaseAuth.getInstance() ;
        auth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
         @Override
         public void onSuccess(AuthResult authResult) {
         Toast.makeText(getContext(), "user created", Toast.LENGTH_SHORT).show();
         if (auth.getCurrentUser() != null){
             auth.signOut();
         }
             Intent intent = new Intent(getContext() , HomeActivity.class);
             startActivity(intent);

         }
     }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
          Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

         }
     });
    }
}