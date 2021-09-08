package com.example.teamup.homeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.teamup.Objects.Post;
import com.example.teamup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddPostFragment extends Fragment {
    DatabaseReference reference;
    public String userName;
    public String userNickname ;
    Post post;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPostFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddPostFragment newInstance(String param1, String param2) {
        AddPostFragment fragment = new AddPostFragment();
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
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        Button add_post_fragment_post_btn = view.findViewById(R.id.add_fragment_post_btn);
        add_post_fragment_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrievingUserName(view);
                navController.navigate(R.id.action_addPostFragment_to_homeFragment);
            }
        });
    }

    public void createPost(Post post) {
        FirebaseDatabase.getInstance().getReference("Posts")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push().setValue(post).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void RetrievingUserName(View view) {
        reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {

                    userName = snapshot.child("userName").getValue().toString();
                    userNickname = snapshot.child("userNickname").getValue().toString();
                    EditText team_Title = view.findViewById(R.id.add_fragment_team_title_et);
                    EditText num_Of_Team = view.findViewById(R.id.add_fragment_num_need_et);
                    EditText post_Desc = view.findViewById(R.id.add_fragment_team_description_et);
                    String teamTitle = team_Title.getText().toString();
                    String numOfTeam = num_Of_Team.getText().toString();
                    String postDesc = post_Desc.getText().toString();
                    post = new Post(teamTitle, numOfTeam, postDesc ,userName , userNickname);
                    createPost(post);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "onCancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

}