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
import ru.dvteam.itcollabhub.callbackclasses.CallBackDelOrChangeAd;
import ru.dvteam.itcollabhub.classmodels.DataOfWatcher;
import ru.dvteam.itcollabhub.databinding.AdvertismentPanelBinding;

public class AdvertsAdapter extends RecyclerView.Adapter<AdvertsAdapter.AdvertsAdapterViewHolder> {

    List<DataOfWatcher> data = new ArrayList<>();
    Context context;
    CallBackDelOrChangeAd callback;

    public AdvertsAdapter(Context context, List<DataOfWatcher> dataA, CallBackDelOrChangeAd callback){
        data = dataA;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public AdvertsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertisment_panel, parent, false);
        return new AdvertsAdapterViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertsAdapterViewHolder holder, int position) {
        holder.update(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AdvertsAdapterViewHolder extends RecyclerView.ViewHolder{
        AdvertismentPanelBinding binding;
        public AdvertsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AdvertismentPanelBinding.bind(itemView);
            binding.deleteBut.setOnClickListener(v -> {
                System.out.println(data.get(getAdapterPosition()).getId() + " Adapter");
                callback.delete(data.get(getAdapterPosition()).getId());
                data.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            });
            binding.editBut.setOnClickListener(v -> {
                callback.change(getAdapterPosition());
            });
        }
        public void update(DataOfWatcher data1){
            binding.fileName.setText(data1.getObjTitle());
            Glide
                    .with(context)
                    .load(data1.getObjImg())
                    .into(binding.advertismentImage);
        }
    }
}
