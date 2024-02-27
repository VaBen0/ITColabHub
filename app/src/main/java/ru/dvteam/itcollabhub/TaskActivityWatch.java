package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityTaskWatchBinding;

public class TaskActivityWatch extends AppCompatActivity {

    ActivityTaskWatchBinding binding;
    String prId, projectTitle, mail, photoProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaskWatchBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        prId = arguments.getString("projectId1");
        projectTitle = arguments.getString("projectTitle");
        photoProject = arguments.getString("projectUrlPhoto");
        String problemName = arguments.getString("problemName");
        String problemDescription = arguments.getString("problemDescription");


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