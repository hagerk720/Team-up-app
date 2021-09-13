package com.example.teamup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamup.OnItemClickListener;
import com.example.teamup.Objects.Post;
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
        TextView userName, teamName, description, numOfTeam, userPhoto;
        OnItemClickListener listener ;
        public PostViewHolder(@NonNull View itemView , OnItemClickListener listener) {
            super(itemView);
            userName = itemView.findViewById(R.id.post_userName_tv);
            teamName = itemView.findViewById(R.id.team_name_postHomeFragment_tv);
            description = itemView.findViewById(R.id.team_description_postHomeFragment_tv);
            numOfTeam = itemView.findViewById(R.id.num_team_postHomeFragment_tv);
            userPhoto = itemView.findViewById(R.id.profile_photo_in_postHomeFragment);
            this.listener = listener ;
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
        listener.onItemClick(getAdapterPosition() , v );
        if (!clicked){
            rearrangeCard(v);
            clicked = true ;
        }
        else{
           closedCard(v);
           clicked =false;
        }
        }
    }
    public static void rearrangeCard(View v){
        TextView numOfTeam = v.findViewById(R.id.num_team_postHomeFragment_tv);
        TextView description = v.findViewById(R.id.team_description_postHomeFragment_tv);
        ViewGroup.LayoutParams params =v.getLayoutParams();
        params.height = 1000 ;
        v.findViewById(R.id.cardView).setLayoutParams(params);
        numOfTeam.setVisibility(View.VISIBLE) ;
        ConstraintLayout constraintLayout = v.findViewById(R.id.constraint);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.team_name_postHomeFragment_tv, ConstraintSet.START,R.id.post_userName_tv ,ConstraintSet.START ,-850 );
        constraintSet.connect(R.id.team_name_postHomeFragment_tv, ConstraintSet.TOP, R.id.post_userName_tv ,ConstraintSet.BOTTOM ,0 );
        constraintSet.connect(R.id.num_team_postHomeFragment_tv, ConstraintSet.BOTTOM, R.id.constraint ,ConstraintSet.BOTTOM ,-400 );
        constraintSet.connect(R.id.join_team_postHomeFragment_tv, ConstraintSet.BOTTOM, R.id.constraint ,ConstraintSet.BOTTOM ,-400 );
        constraintSet.applyTo(constraintLayout);

    }
    public static void closedCard(View v){
        TextView numOfTeam = v.findViewById(R.id.num_team_postHomeFragment_tv);
        TextView description = v.findViewById(R.id.team_description_postHomeFragment_tv);

        ViewGroup.LayoutParams params =v.getLayoutParams();
        params.height = 500 ;
        v.findViewById(R.id.cardView).setLayoutParams(params);
        numOfTeam.setVisibility(View.GONE) ;
        ConstraintLayout constraintLayout = v.findViewById(R.id.constraint);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.team_name_postHomeFragment_tv, ConstraintSet.START,R.id.post_userName_tv ,ConstraintSet.END ,280 );
        constraintSet.connect(R.id.team_name_postHomeFragment_tv, ConstraintSet.TOP, R.id.constraint,ConstraintSet.TOP ,35 );
        constraintSet.connect(R.id.join_team_postHomeFragment_tv, ConstraintSet.BOTTOM, R.id.constraint ,ConstraintSet.BOTTOM ,0 );
        constraintSet.applyTo(constraintLayout);

    }
}
