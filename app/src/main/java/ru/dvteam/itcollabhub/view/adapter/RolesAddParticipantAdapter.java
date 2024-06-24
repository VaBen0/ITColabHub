package ru.dvteam.itcollabhub.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackParticipantWithRole;
import ru.dvteam.itcollabhub.classmodels.ParticipantWithRole;
import ru.dvteam.itcollabhub.databinding.FriendRoleWindowBinding;

public class RolesAddParticipantAdapter extends RecyclerView.Adapter<RolesAddParticipantAdapter.ViewHolder> {

    List<ParticipantWithRole> users;
    Context context;
    int color;
    CallBackParticipantWithRole callBack;

    public RolesAddParticipantAdapter(List<ParticipantWithRole> users, int color, Context context, CallBackParticipantWithRole callBack) {
        this.users = users;
        this.color = color;
        this.context = context;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_role_window, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.update(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        FriendRoleWindowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FriendRoleWindowBinding.bind(itemView);

            binding.notban.setOnClickListener(v -> {
                if(users.get(getAdapterPosition()).isChecked()){
                    binding.notban.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.view13.setVisibility(View.GONE);
                    callBack.invoke(users.get(getAdapterPosition()), 0);
                }else{
                    binding.notban.setImageResource(R.drawable.checked_radio_icon);
                    binding.view13.setVisibility(View.VISIBLE);
                    callBack.invoke(users.get(getAdapterPosition()), 1);
                }
            });
        }
        public void update(ParticipantWithRole participant){
            ArrayList<Integer> colorsArray = new ArrayList<>();

            if(participant.getRoleColor().equals("#FFFFFF")){
                colorsArray.add(color);
                colorsArray.add(Color.parseColor("#FFFFFF"));
            }else{
                colorsArray.add(color);
                colorsArray.add(Color.parseColor(participant.getRoleColor()));
                colorsArray.add(Color.parseColor(participant.getRoleColor()));
            }

            int[] colors = new int[colorsArray.size()];
            for (int i = 0; i < colorsArray.size(); i++) {
                colors[i] = colorsArray.get(i);
            }
            if(!users.get(getAdapterPosition()).isChecked()){
                binding.notban.setImageResource(R.drawable.not_checked_radio_icon);
                binding.view13.setVisibility(View.GONE);
            }else{
                binding.notban.setImageResource(R.drawable.checked_radio_icon);
                binding.view13.setVisibility(View.VISIBLE);
            }
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors);


            binding.view12.setBackground(gd);
            binding.roleCircle.setBackground(gd);
            binding.textView3.setText(participant.getName());
            binding.role.setText(participant.getRoleName());
            Glide
                    .with(context)
                    .load(participant.getUrlPhoto())
                    .into(binding.log);
        }
    }
}
