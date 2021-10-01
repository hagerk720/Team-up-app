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
    NavController navController ;


    public AddPostFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddPostFragment newInstance(String param1, String param2) {
        AddPostFragment fragment = new AddPostFragment();
        Bundle args = new Bundle();

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
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        return view ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = view.findViewById(R.id.add_fragment_post_btn);
        Button add_post_fragment_post_btn = view.findViewById(R.id.add_fragment_post_btn);

        navController = Navigation.findNavController(view);

        if (AddPostFragmentArgs.fromBundle(getArguments()).getEditPost()!= "null"){
            btn.setText("Edit");

            String key = AddPostFragmentArgs.fromBundle(getArguments()).getEditPost();
            reference = FirebaseDatabase.getInstance().getReference("Posts")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Post post = snapshot.getValue(Post.class);
                    fillFields(view , post);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                         updatePost(key , collectFields(view , post ));
                         navController.navigate(R.id.action_addPostFragment_to_homeFragment);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            btn.setText("Post");
        }

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
                    post = new Post(teamTitle, numOfTeam, postDesc ,userName , userNickname, FirebaseAuth.getInstance().getCurrentUser().getUid());
                    createPost(post);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "onCancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updatePost(String key , Post post){
        FirebaseDatabase.getInstance().getReference("Posts")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(key).setValue(post).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "updated", Toast.LENGTH_SHORT).show();
            }
        }) ;
    }
    public Post collectFields(View v , Post post){
        EditText team_Title = v.findViewById(R.id.add_fragment_team_title_et);
        EditText num_Of_Team = v.findViewById(R.id.add_fragment_num_need_et);
        EditText post_Desc = v.findViewById(R.id.add_fragment_team_description_et);
        String teamTitle = team_Title.getText().toString();
        String numOfTeam = num_Of_Team.getText().toString();
        String postDesc = post_Desc.getText().toString();
        post = new Post(teamTitle, numOfTeam, postDesc , post.getUserName() , post.getUserNickname());
       return post ;
    }
    public void fillFields(View v , Post post){
        EditText team_Title = v.findViewById(R.id.add_fragment_team_title_et);
        EditText num_Of_Team = v.findViewById(R.id.add_fragment_num_need_et);
        EditText post_Desc = v.findViewById(R.id.add_fragment_team_description_et);
        team_Title.setText(post.getTeamTitle());
        num_Of_Team.setText(post.getNumOfTeam());
        post_Desc.setText(post.getPostDesc());
    }

}