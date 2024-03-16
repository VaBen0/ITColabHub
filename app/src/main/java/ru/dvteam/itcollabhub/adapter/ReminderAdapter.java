package ru.dvteam.itcollabhub.adapter;

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
import ru.dvteam.itcollabhub.callbackclasses.CallBackActivityProject;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectsArray;
import ru.dvteam.itcollabhub.classmodels.DataOfWatcher;
import ru.dvteam.itcollabhub.classmodels.ProjectClass;
import ru.dvteam.itcollabhub.databinding.ReminderBinding;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderAdapterViewHolder> {

    List<DataOfWatcher> data = new ArrayList<>();
    private final Context context;
    private final CallBackActivityProject callback;

    public ReminderAdapter(ArrayList<DataOfWatcher> data, Context context, CallBackActivityProject callback){
        this.data = data;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ReminderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder, parent, false);
        return new ReminderAdapterViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapterViewHolder holder, int position) {
        holder.update(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReminderAdapterViewHolder extends RecyclerView.ViewHolder{
        private final ReminderBinding binding;
        public ReminderAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReminderBinding.bind(itemView);

            binding.getRoot().setOnClickListener(v -> {

            });
        }

        public void update(DataOfWatcher data){
            binding.textView33.setText(data.getObjTitle());
            if(data.isType()) binding.textView32.setText("Задание");
            else binding.textView32.setText("Объявление");
            Glide
                    .with(context)
                    .load(data.getObjImg())
                    .into(binding.loadImg);
        }
    }
}
