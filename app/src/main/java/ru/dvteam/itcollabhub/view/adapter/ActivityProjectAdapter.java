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

import ru.dvteam.itcollabhub.classmodels.ProjectClass;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackActivityProject;
import ru.dvteam.itcollabhub.databinding.ProjectWindowBinding;

public class ActivityProjectAdapter extends RecyclerView.Adapter<ActivityProjectAdapter.ActivityProjectViewHolder> {

    private List<ProjectClass> activityProjects = new ArrayList<>();
    private final Context context;
    private final CallBackActivityProject callback;

    public ActivityProjectAdapter(Context context, List<ProjectClass> projects, CallBackActivityProject callback){
        this.context = context;
        activityProjects = projects;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ActivityProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_window, parent, false);
        return new ActivityProjectViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityProjectViewHolder holder, int position) {
        holder.update(activityProjects.get(position));
    }

    @Override
    public int getItemCount() {
        return activityProjects.size();
    }

    public class ActivityProjectViewHolder extends RecyclerView.ViewHolder {
        private final ProjectWindowBinding binding;

        public ActivityProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProjectWindowBinding.bind(itemView);

            binding.getRoot().setOnClickListener(v -> {
                ProjectClass projectClass = activityProjects.get(getAdapterPosition());

                callback.setActivity(projectClass.getProjectId());
            });
        }
        public void update(ProjectClass projectClass) {
            binding.textView3.setText(projectClass.getProjectTitle());
            binding.textView13.setText(projectClass.getProjectCreatorName());
            binding.textView16.setText(projectClass.getProjectProgress() + "%");

            binding.lvl.setMax(100);
            binding.lvl.setProgress(projectClass.getProjectProgress());

            if(projectClass.getProjectCreatorScore() < 100){
                binding.userCircle.setBackgroundResource(R.drawable.circle_blue2);
            }
            else if(projectClass.getProjectCreatorScore() < 300){
                binding.userCircle.setBackgroundResource(R.drawable.circle_green2);
            }
            else if(projectClass.getProjectCreatorScore() < 1000){
                binding.userCircle.setBackgroundResource(R.drawable.circle_brown2);
            }
            else if(projectClass.getProjectCreatorScore() < 2500){
                binding.userCircle.setBackgroundResource(R.drawable.circle_light_gray2);
            }
            else if(projectClass.getProjectCreatorScore() < 7000){
                binding.userCircle.setBackgroundResource(R.drawable.circle_ohra2);
            }
            else if(projectClass.getProjectCreatorScore() < 17000){
                binding.userCircle.setBackgroundResource(R.drawable.circle_red2);
            }
            else if(projectClass.getProjectCreatorScore() < 30000){
                binding.userCircle.setBackgroundResource(R.drawable.circle_orange2);
            }
            else if(projectClass.getProjectCreatorScore() < 50000){
                binding.userCircle.setBackgroundResource(R.drawable.circle_violete2);
            }
            else{
                binding.userCircle.setBackgroundResource(R.drawable.circle_blue_green2);
            }
            if(!projectClass.getProjectId().equals("Demo")){
                Glide
                        .with(context)
                        .load(projectClass.getProjectLog())
                        .into(binding.log);
                Glide
                        .with(context)
                        .load(projectClass.getProjectCreatorLog())
                        .into(binding.logo);
            }
        }
    }
}
