package ru.dvteam.itcollabhub.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.classmodels.NotificationsInfo;
import ru.dvteam.itcollabhub.databinding.NotificationsWindowBinding;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{

    private List<NotificationsInfo> info;
    private Context context;

    public NotificationsAdapter(List<NotificationsInfo> info, Context context) {
        this.info = info;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_window, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.update(info.get(position));
    }

    @Override
    public int getItemCount() {
        return info.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        NotificationsWindowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NotificationsWindowBinding.bind(itemView);

            binding.nextText.setOnClickListener(v -> {
                goToLink(info.get(getAdapterPosition()).getNotificationUrlPhoto());
            });
            binding.nextButtun.setOnClickListener(v -> {
                goToLink(info.get(getAdapterPosition()).getNotificationUrlPhoto());
            });
        }

        public void update(NotificationsInfo info){
            binding.notificationName.setText(info.getNotificationName());
            binding.notificationsText.setText(info.getNotificationText());
            Glide
                    .with(context)
                    .load(info.getNotificationLink())
                    .into(binding.photoLog);
            if(info.getNotificationLink().isEmpty()){
                binding.nextButtun.setVisibility(View.GONE);
                binding.nextText.setVisibility(View.GONE);
            }
        }

        private void goToLink(String url){
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            context.startActivity(launchBrowser);
        }
    }
}
