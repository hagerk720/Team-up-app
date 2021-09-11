package com.example.teamup.homeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.OnItemClickListener;
import com.example.teamup.Objects.Post;
import com.example.teamup.R;
import com.example.teamup.Adapters.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnItemClickListener{
    TextView textView ;
    RecyclerView recyclerView ;
    DatabaseReference reference ;
    RecyclerViewAdapter adapter ;
    ArrayList<Post> postList ;
    ArrayList<String> keys ;



    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        // animation
        textView = v.findViewById(R.id.textview);
        Animation animation = AnimationUtils.loadAnimation(getContext() , R.anim.top_header);
        textView.setAnimation(animation);
        //-------------------------------------------------------
        recyclerView = v.findViewById(R.id.homeFragment_recyclerView_posts);
        reference = FirebaseDatabase.getInstance().getReference("Posts");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postList = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new RecyclerViewAdapter(getContext(), postList ,this);

        recyclerView.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Post post = data.getValue(Post.class);
                        postList.add(post);
                        keys.add(data.getKey());
                        adapter.notifyDataSetChanged();
                    }
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
        FloatingActionButton Home_fragment_add_post_btn = view.findViewById(R.id.homeFragment_add_post_btn);
        Home_fragment_add_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeFragment_to_addPostFragment);
            }
        });
    }

    @Override
    public void onItemClick(int Position) {
        Toast.makeText(getContext(), "item clicked" + Position, Toast.LENGTH_SHORT).show();
    }
}