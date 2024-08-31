package com.john.reference.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.john.reference.R;
import com.john.reference.model.UserModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.graphics.Color;

public class UserView extends RecyclerView.Adapter<UserView.UserViewHolder> {
    private List<UserModel> users = new ArrayList<>();
    private Set<Integer> selectedPositions = new HashSet<>();
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel currentUser = users.get(position);
        String name = currentUser.getFirst_name() + " " + currentUser.getLast_name();
        holder.nameTextView.setText(name);
        holder.emailTextView.setText(currentUser.getEmail());

        Glide.with(holder.avatarImageView.getContext())
                .load(currentUser.getAvatar())
                .into(holder.avatarImageView);

        holder.itemView.setBackgroundColor(selectedPositions.contains(position) ? Color.LTGRAY : Color.TRANSPARENT);
        holder.itemView.setOnClickListener(v -> {
            if (selectedPositions.contains(holder.getAdapterPosition())) {
                selectedPositions.remove(holder.getAdapterPosition());
            } else {
                selectedPositions.add(holder.getAdapterPosition());
            }
            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public List<UserModel> getSelectedUsers() {
        List<UserModel> selectedUsers = new ArrayList<>();
        for (int position : selectedPositions) {
            selectedUsers.add(users.get(position));
        }
        return selectedUsers;
    }

    public void clearSelections() {
        selectedPositions.clear();
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView emailTextView;
        private ImageView avatarImageView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_view_name);
            emailTextView = itemView.findViewById(R.id.text_view_email);
            avatarImageView = itemView.findViewById(R.id.image_view_avatar);

        }
    }
}
