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
import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.callbackclasses.CallBackFriendInfromation;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.classmodels.FriendInformation;
import ru.dvteam.itcollabhub.databinding.FriendWindowBinding;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    List<FriendInformation> friendsData = new ArrayList<>();
    CallBackInt callback;
    CallBackFriendInfromation callback2;
    Context context;

    public FriendsAdapter(List<FriendInformation> friendsData, CallBackInt callback, CallBackFriendInfromation callback2, Context context) {
        this.friendsData = friendsData;
        this.callback = callback;
        this.context = context;
        this.callback2 = callback2;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_window, parent, false);
        return new FriendsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        holder.update(friendsData.get(position));
    }

    @Override
    public int getItemCount() {
        return friendsData.size();
    }


    public class FriendsViewHolder extends RecyclerView.ViewHolder{
        FriendWindowBinding binding;
        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FriendWindowBinding.bind(itemView);
            binding.getRoot().setOnClickListener(v -> {
                callback2.invoke(friendsData.get(getAdapterPosition()));
            });
            binding.notban.setOnClickListener(v -> {
                callback.invoke(friendsData.get(getAdapterPosition()).getFriendId());
                friendsData.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            });
        }
        public void update(FriendInformation friend){
            binding.textView3.setText(friend.getFriendName());
            setCircle(friend.getScore(), binding.userCircle);
            binding.projects1.setVisibility(View.GONE);
            if(friend.isFind()) binding.notban.setImageResource(R.drawable.ad);
            else binding.notban.setVisibility(View.GONE);
            Glide
                    .with(context)
                    .load(friend.getFriendPhoto())
                    .into(binding.log);
        }

    }

    public void setCircle(int score, View v){
        if (score < 100) {
            v.setBackgroundResource(R.drawable.circle_blue2);
        } else if (score < 300) {
            v.setBackgroundResource(R.drawable.circle_green2);
        } else if (score < 1000) {
            v.setBackgroundResource(R.drawable.circle_brown2);
        } else if (score < 2500) {
            v.setBackgroundResource(R.drawable.circle_light_gray2);
        } else if (score < 7000) {
            v.setBackgroundResource(R.drawable.circle_ohra2);
        } else if (score < 17000) {
            v.setBackgroundResource(R.drawable.circle_red2);
        } else if (score < 30000) {
            v.setBackgroundResource(R.drawable.circle_orange2);
        } else if (score < 50000) {
            v.setBackgroundResource(R.drawable.circle_violete2);
        } else {
            v.setBackgroundResource(R.drawable.circle_blue_green2);
        }
    }
}
