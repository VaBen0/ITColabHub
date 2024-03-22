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
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.classmodels.TasksClass;
import ru.dvteam.itcollabhub.databinding.TasksPanelBinding;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {
    List<TasksClass> tasks = new ArrayList<>();
    Context context;
    CallBackInt callBack;
    String prPhoto;

    public TasksAdapter(List<TasksClass> tasks, Context context, String prPhoto, CallBackInt callBack) {
        this.tasks = tasks;
        this.context = context;
        this.callBack = callBack;
        this.prPhoto = prPhoto;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_panel, parent, false);
        return new TasksViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        holder.update(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public class TasksViewHolder extends RecyclerView.ViewHolder{
        TasksPanelBinding binding;
        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TasksPanelBinding.bind(itemView);
            binding.getRoot().setOnClickListener(v -> {
                callBack.invoke(getAdapterPosition() + "");
            });
        }
        public void update(TasksClass tasksClass){
            binding.taskTitle.setText(tasksClass.getTaskName());
            binding.taskText.setText(tasksClass.getTaskDescription());
            Glide
                    .with(context)
                    .load(prPhoto)
                    .into(binding.loadImg);
            if(tasksClass.getTaskComplete().equals("1")) binding.view8.setImageResource(R.drawable.green_transperent);
        }
    }
}
