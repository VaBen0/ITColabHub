package ru.dvteam.itcollabhub.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.classmodels.UserInformation;
import ru.dvteam.itcollabhub.databinding.FriendWindowBinding;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantsViewHolder> {

    List<UserInformation> users = new ArrayList<>();
    Context context;

    public ParticipantsAdapter(List<UserInformation> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ParticipantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_window, parent, false);
        return new ParticipantsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantsViewHolder holder, int position) {
        holder.update(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ParticipantsViewHolder extends RecyclerView.ViewHolder{
        FriendWindowBinding binding;
        public ParticipantsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FriendWindowBinding.bind(itemView);
        }
        public void update(UserInformation userInformation){
            binding.projects1.setVisibility(View.GONE);
            binding.userCircle.setVisibility(View.GONE);
            binding.notban.setVisibility(View.GONE);
            binding.notban.setImageResource(R.drawable.delete_black);
            binding.textView3.setText(userInformation.getUserName());
            Glide
                    .with(context)
                    .load(userInformation.getUserLog())
                    .into(binding.log);
        }
    }
}
