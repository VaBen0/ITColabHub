package ru.dvteam.itcollabhub.view.projectmenusviews.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityProjectPageBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ProjectPageViewModel;

public class ProjectPage extends AppCompatActivity {

    ActivityProjectPageBinding binding;
    ProjectPageViewModel projectPageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProjectPageBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        projectPageViewModel = new ViewModelProvider(this).get(ProjectPageViewModel.class);

        binding.projectName.setText(projectPageViewModel.getProjectTitle());
        binding.description.setText(projectPageViewModel.getProjectDescription());
        binding.webLink.setText(projectPageViewModel.getProjectWebLink());
        Glide
                .with(ProjectPage.this)
                .load(projectPageViewModel.getProjectLog())
                .into(binding.prLogo);
        Glide
                .with(ProjectPage.this)
                .load(projectPageViewModel.getProjectLog())
                .into(binding.log);

        binding.webLink.setOnClickListener(view -> goToLink(projectPageViewModel.getProjectWebLink()));
    }

    private void goToLink(String url){
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}