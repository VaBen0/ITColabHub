package ru.dvteam.itcollabhub.view.adapter;

import android.content.Context;
import android.os.CountDownTimer;
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
import ru.dvteam.itcollabhub.databinding.DeadlinePanelBinding;

public class DeadlinesAdapter extends RecyclerView.Adapter<DeadlinesAdapter.DeadlinesViewHolder> {
    List<TasksClass> deadlines = new ArrayList<>();
    Context context;
    CallBackInt callBack;
    String prPhoto;

    public DeadlinesAdapter(List<TasksClass> deadlines, Context context, String prPhoto, CallBackInt callBack) {
        this.deadlines = deadlines;
        this.context = context;
        this.callBack = callBack;
        this.prPhoto = prPhoto;
    }

    @NonNull
    @Override
    public DeadlinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.deadline_panel, parent, false);
        return new DeadlinesViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull DeadlinesViewHolder holder, int position) {
        holder.update(deadlines.get(position));
    }

    @Override
    public int getItemCount() {
        return deadlines.size();
    }

    public class DeadlinesViewHolder extends RecyclerView.ViewHolder{
        DeadlinePanelBinding binding;
        public DeadlinesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DeadlinePanelBinding.bind(itemView);
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
            if(tasksClass.getTaskComplete().equals("1") || Long.parseLong(tasksClass.getDate()) <= 0) binding.view8.setImageResource(R.drawable.green_transperent);
            new CountDownTimer(Long.parseLong(tasksClass.getDate()), 1000) {

                public void onTick(long millisUntilFinished) {
                    long allSeconds = millisUntilFinished / 1000;
                    long seconds = allSeconds % 60;
                    long minutes = (allSeconds / 60) % 60;
                    long hours = (allSeconds / 3600) % 24;
                    long days = (allSeconds / 3600) / 24;
                    String s;
                    if(days < 10) {
                        switch ((int) days % 10) {
                            case 1:
                                s = String.format("%2d день %02d:%02d:%02d", days, hours, minutes, seconds);
                                break;
                            case 2:
                            case 3:
                            case 4:
                                s = String.format("%2d дня %02d:%02d:%02d", days, hours, minutes, seconds);
                                break;
                            default:
                                s = String.format("%2d дней %02d:%02d:%02d", days, hours, minutes, seconds);
                                break;
                        }
                    } else{
                        switch ((int) days % 10) {
                            case 1:
                                s = String.format("%02d день %02d:%02d:%02d", days, hours, minutes, seconds);
                                break;
                            case 2:
                            case 3:
                            case 4:
                                s = String.format("%02d дня %02d:%02d:%02d", days, hours, minutes, seconds);
                                break;
                            default:
                                s = String.format("%02d дней %02d:%02d:%02d", days, hours, minutes, seconds);
                                break;
                        }
                    }
                    binding.timer.setText(s);
                }

                public void onFinish() {binding.timer.setText("Время истекло");}
            }.start();
        }
    }
}
