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
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt1;
import ru.dvteam.itcollabhub.classmodels.FileInformation;
import ru.dvteam.itcollabhub.databinding.GfilePanelBinding;

public class FilesEndProjectAdapter extends RecyclerView.Adapter<FilesEndProjectAdapter.FilesEndProjectViewHolder> {
    List<FileInformation> files = new ArrayList<>();
    Context context;

    public FilesEndProjectAdapter(ArrayList<FileInformation> files, Context context){
        this.files = files;
        this.context = context;
    }

    @NonNull
    @Override
    public FilesEndProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.gfile_panel, parent, false);
        return new FilesEndProjectViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull FilesEndProjectViewHolder holder, int position) {
        holder.update(files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }


    public class FilesEndProjectViewHolder extends RecyclerView.ViewHolder{
        GfilePanelBinding binding;
        public FilesEndProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = GfilePanelBinding.bind(itemView);
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
            binding.zakrepBut.setVisibility(View.GONE);
            binding.editBut.setVisibility(View.GONE);
            binding.deleteBut.setVisibility(View.GONE);
        }

        private void goToLink(String url){
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            context.startActivity(launchBrowser);
        }
    }
}
