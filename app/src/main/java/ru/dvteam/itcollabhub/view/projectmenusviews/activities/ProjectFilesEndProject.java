package ru.dvteam.itcollabhub.view.projectmenusviews.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.UsersChosenTheme;
import ru.dvteam.itcollabhub.adapter.FilesAdapter;
import ru.dvteam.itcollabhub.adapter.FilesEndProjectAdapter;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt1;
import ru.dvteam.itcollabhub.databinding.ActivityProjectFilesEndProjectBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ProjectFilesViewModel;

public class ProjectFilesEndProject extends AppCompatActivity {
    ActivityProjectFilesEndProjectBinding binding;
    ProjectFilesViewModel projectFilesViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityProjectFilesEndProjectBinding.inflate(getLayoutInflater());
        projectFilesViewModel = new ViewModelProvider(this).get(ProjectFilesViewModel.class);

        setContentView(binding.getRoot());

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ProjectFilesEndProject.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        binding.nameProject.setText(projectFilesViewModel.getPrName());

        Glide
                .with(ProjectFilesEndProject.this)
                .load(projectFilesViewModel.getPrLog())
                .into(binding.prLogo);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.filesPlace.setLayoutManager(layoutManager);

        projectFilesViewModel.getAllFiles().observe(this, fileInformations -> {
            FilesEndProjectAdapter adapter = new FilesEndProjectAdapter(fileInformations, this);
            binding.filesPlace.setAdapter(adapter);
        });

        projectFilesViewModel.getFilesInfo();
    }

    public void setThemeActivity(){
        int themeType = UsersChosenTheme.getThemeNum();

        switch (themeType) {
            case (1):
                setTheme(R.style.Theme_ITCollabHub_Blue);
                break;
            case (2):
                setTheme(R.style.Theme_ITCollabHub_Green);
                break;
            case (3):
                setTheme(R.style.Theme_ITCollabHub_Brown);
                break;
            case (4):
                setTheme(R.style.Theme_ITCollabHub_PinkGold);
                break;
            case (5):
                setTheme(R.style.Theme_ITCollabHub_Ohra);
                break;
            case (6):
                setTheme(R.style.Theme_ITCollabHub_Red);
                break;
            case (7):
                setTheme(R.style.Theme_ITCollabHub_Orange);
                break;
            case (8):
                setTheme(R.style.Theme_ITCollabHub_Violete);
                break;
            case (9):
                setTheme(R.style.Theme_ITCollabHub_BlueGreen);
                break;
        }

    }
}