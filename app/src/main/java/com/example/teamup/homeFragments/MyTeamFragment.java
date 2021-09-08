package com.example.teamup.homeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Adapters.MyTeamPostAdapter;
import com.example.teamup.Objects.Post;
import com.example.teamup.OnItemClickListener;
import com.example.teamup.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTeamFragment extends Fragment implements OnItemClickListener {
    RecyclerView recyclerView ;
    DatabaseReference reference ;
    MyTeamPostAdapter adapter ;
    ArrayList<Post> postList ;



    public MyTeamFragment() {
        // Required empty public constructor
    }


    public static MyTeamFragment newInstance(String param1, String param2) {
        MyTeamFragment fragment = new MyTeamFragment();

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
        View v = inflater.inflate(R.layout.fragment_my_team, container, false);
        recyclerView = v.findViewById(R.id.myTeamFragment_recyclerView_posts);
        reference = FirebaseDatabase.getInstance().getReference("Posts")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postList = new ArrayList<>();
        adapter = new MyTeamPostAdapter(getContext(), postList ,this);

        recyclerView.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Post post = dataSnapshot.getValue(Post.class);
                        postList.add(post);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        FloatingActionButton my_Team_fragment_add_post_btn = view.findViewById(R.id.myTeamFragment_add_post_btn);
        my_Team_fragment_add_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_menu_myTeam_to_addPostFragment);
            }
        });
        

    }

    @Override
    public void onItemClick(int Position) {
        Toast.makeText(getContext(), deletePost(Position), Toast.LENGTH_SHORT).show();
    }
    public String  deletePost(int position){
        String str =  reference.getRef().getKey();
      //  reference.child(str).removeValue();
        return str ;
    }
}