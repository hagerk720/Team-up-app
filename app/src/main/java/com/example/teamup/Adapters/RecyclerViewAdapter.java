package com.example.teamup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.Objects.Post;
import com.example.teamup.OnItemClickListener;
import com.example.teamup.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PostViewHolder> {
    Context context;
    ArrayList<Post> postList;
    private final OnItemClickListener listener;
    public static boolean clicked = false ;

    public RecyclerViewAdapter(Context context, ArrayList<Post> postList, OnItemClickListener listener) {
        this.context = context;
        this.postList = postList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_fragment_card_view_posts, parent, false);
        return new PostViewHolder(v ,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.userName.setText(post.getUserName());
        holder.teamName.setText(post.getTeamTitle());
        holder.numOfTeam.setText("Need " +post.getNumOfTeam() + " people");
        holder.description.setText(post.getPostDesc());
        holder.userPhoto.setText(post.getUserNickname());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName, teamName, description, numOfTeam, userPhoto , join;
        OnItemClickListener listener ;
        public PostViewHolder(@NonNull View itemView , OnItemClickListener listener) {
            super(itemView);
            userName = itemView.findViewById(R.id.post_userName_tv);
            teamName = itemView.findViewById(R.id.team_name_postHomeFragment_tv);
            description = itemView.findViewById(R.id.team_description_postHomeFragment_tv);
            numOfTeam = itemView.findViewById(R.id.num_team_postHomeFragment_tv);
            userPhoto = itemView.findViewById(R.id.profile_photo_in_postHomeFragment);
            join = itemView.findViewById(R.id.join_team_postHomeFragment_tv);
            this.listener = listener ;
            itemView.setOnClickListener(this);
            join.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        listener.onItemClick(getAdapterPosition() , v );
        }
    }




}
