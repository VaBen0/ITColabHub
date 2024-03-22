package ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityTaskWatchBinding;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.Information;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;

public class TaskActivityWatch extends AppCompatActivity {

    ActivityTaskWatchBinding binding;
    String prId, projectTitle, mail, photoProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UsersChosenTheme.setThemeActivity(this);
        super.onCreate(savedInstanceState);

        binding = ActivityTaskWatchBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        prId = ProjectId.getInstance().getProjectId();
        projectTitle = GlobalProjectInformation.getInstance().getProjectTitle();
        photoProject = GlobalProjectInformation.getInstance().getProjectLog();
        String problemPhoto = Information.getInstance().getObjImage();
        String problemName = Information.getInstance().getObjTitle();
        String problemDescription = Information.getInstance().getObjText();

        binding.nameProject.setText(projectTitle);
        binding.problemTitle.setText(problemName);
        binding.problemDescription.setText(problemDescription);
        Glide
                .with(TaskActivityWatch.this)
                .load(photoProject)
                .into(binding.prLogo);
        Glide
                .with(TaskActivityWatch.this)
                .load(photoProject)
                .into(binding.advertPhoto);

        binding.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}