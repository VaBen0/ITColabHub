package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityProjectPageBinding;

public class ProjectPage extends AppCompatActivity {

    ActivityProjectPageBinding binding;

    private String id, title, description, prPhoto, mail, tgLink, vkLink, webLink, isOpen, isVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProjectPageBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        description = arguments.getString("projectDescription");
        webLink = arguments.getString("webLink");

        binding.projectName.setText(title);
        binding.description.setText(description);
        binding.webLink.setText(webLink);
        Glide
                .with(ProjectPage.this)
                .load(prPhoto)
                .into(binding.prLogo);
        Glide
                .with(ProjectPage.this)
                .load(prPhoto)
                .into(binding.log);

        binding.webLink.setOnClickListener(view -> goToLink(webLink));
    }

    private void goToLink(String url){
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}