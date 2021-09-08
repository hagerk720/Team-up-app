package com.example.teamup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Objects.Post;
import com.example.teamup.OnItemClickListener;
import com.example.teamup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyTeamPostAdapter extends RecyclerView.Adapter<MyTeamPostAdapter.ViewHolder> {
    Context context ;
    ArrayList<Post> posts ;
    DatabaseReference reference ;
    private final OnItemClickListener listener;



    public MyTeamPostAdapter(Context context, ArrayList<Post> posts ,OnItemClickListener listener) {
        this.context = context;
        this.posts = posts;
        this.listener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(context).inflate(R.layout.my_team_fragment_card_view_post, parent, false);
        return new ViewHolder(v , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.teamName.setText(post.getTeamTitle());
        holder.numOfTeam.setText("Need " +post.getNumOfTeam() + " people");
        holder.description.setText(post.getPostDesc());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView  teamName, description, numOfTeam ,Edit , Delete ;
        OnItemClickListener listener ;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            teamName = itemView.findViewById(R.id.team_name_myTeamFragment_tv);
            description = itemView.findViewById(R.id.team_description_myTeamFragment_tv);
            numOfTeam = itemView.findViewById(R.id.num_team_myTeamFragment_tv);
            Edit = itemView.findViewById(R.id.edit_myTeamFragment_tv);
            Delete = itemView.findViewById(R.id.delete_myTeamFragment_tv);
            this.listener = listener ;
            Delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), deletePost(), Toast.LENGTH_SHORT).show();
        }
    }
    public String  deletePost(){
        reference = FirebaseDatabase.getInstance().getReference("Posts")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String str =  reference.getRef().getKey();
        //  reference.child(str).removeValue();
        return str ;
    }

}