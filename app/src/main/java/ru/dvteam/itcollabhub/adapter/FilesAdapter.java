package ru.dvteam.itcollabhub.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt1;
import ru.dvteam.itcollabhub.classmodels.FileInformation;
import ru.dvteam.itcollabhub.databinding.GfilePanelBinding;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FilesViewHolder> {

    List<FileInformation> files = new ArrayList<>();
    Context context;
    CallBackInt1 callback;

    public FilesAdapter(ArrayList<FileInformation> files, Context context, CallBackInt1 callback){
        this.files = files;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public FilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.gfile_panel, parent, false);
        return new FilesViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull FilesViewHolder holder, int position) {
        holder.update(files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class FilesViewHolder extends RecyclerView.ViewHolder{
        GfilePanelBinding binding;
        public FilesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = GfilePanelBinding.bind(itemView);
            binding.zakrepBut.setOnClickListener(v -> {
                binding.backGround.setBackgroundResource(R.drawable.green_transperent);
                callback.invoke("1", files.get(getAdapterPosition()).getFileId());
                files.add(0, files.remove(getAdapterPosition()));
                notifyItemMoved(getAdapterPosition(), 0);
                binding.zakrepBut.setVisibility(View.GONE);
                binding.zakrepBut1.setVisibility(View.VISIBLE);
            });
            binding.zakrepBut1.setOnClickListener(v -> {
                binding.backGround.setBackgroundResource(R.drawable.progress_panel_background);
                callback.invoke("2", files.get(getAdapterPosition()).getFileId());
                files.add(FileInformation.getCntFixed() - 1, files.remove(getAdapterPosition()));
                notifyItemMoved(getAdapterPosition(), FileInformation.getCntFixed() - 1);
                binding.zakrepBut.setVisibility(View.VISIBLE);
                binding.zakrepBut1.setVisibility(View.GONE);
            });
            binding.deleteBut.setOnClickListener(v -> {
                callback.invoke("3", files.get(getAdapterPosition()).getFileId());
                files.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            });
            binding.editBut.setOnClickListener(v -> {
                callback.invoke("4", getAdapterPosition() + "");
            });
            binding.getRoot().setOnClickListener(v -> {
                goToLink(files.get(getAdapterPosition()).getLink());
            });
        }
        public void update(FileInformation fileInformation){
            binding.fileName.setText(fileInformation.getName());
            Glide
                    .with(context)
                    .load(fileInformation.getImg())
                    .into(binding.fileImage);
            if(fileInformation.getFixed().equals("1")) {
                binding.backGround.setBackgroundResource(R.drawable.green_transperent);
                binding.zakrepBut.setVisibility(View.GONE);
                binding.zakrepBut1.setVisibility(View.VISIBLE);
            }
        }

        private void goToLink(String url){
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            context.startActivity(launchBrowser);
        }
    }
}
