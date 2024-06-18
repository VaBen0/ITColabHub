package ru.dvteam.itcollabhub.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
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
import ru.dvteam.itcollabhub.databinding.FriendRoleWindowBinding;
import ru.dvteam.itcollabhub.databinding.FriendWindowBinding;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantsViewHolder> {

    List<UserInformation> users = new ArrayList<>();
    Context context;
    int color;

    public ParticipantsAdapter(List<UserInformation> users, Context context, int color) {
        this.users = users;
        this.context = context;
        this.color = color;
    }

    @NonNull
    @Override
    public ParticipantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_role_window, parent, false);
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
        FriendRoleWindowBinding binding;
        public ParticipantsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FriendRoleWindowBinding.bind(itemView);
        }
        public void update(UserInformation userInformation){
            int h = binding.view12.getHeight();
            int[] colors = {color,Color.parseColor("#00C6A2"), Color.parseColor("#00C6A2")};
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors);
            binding.view12.setBackground(gd);
            binding.roleCircle.setBackground(gd);
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
