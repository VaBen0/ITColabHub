package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityAdvertismentBinding;

public class Advertisment extends AppCompatActivity {

    ActivityAdvertismentBinding binding;
    String prId, projectTitle, mail, photoProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        binding = ActivityAdvertismentBinding.inflate(getLayoutInflater());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(binding.getRoot());

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        String id = arguments.getString("problemId");
        prId = arguments.getString("projectId1");
        projectTitle = arguments.getString("projectTitle");
        photoProject = arguments.getString("projectUrlPhoto");
        String problemPhoto = arguments.getString("problemPhoto");
        String problemName = arguments.getString("problemName");
        String problemDescription = arguments.getString("problemDescription");

        long millis = 259200000;

        new CountDownTimer(millis, 1000) {

            public void onTick(long millisUntilFinished) {
                long allSeconds = millisUntilFinished / 1000;
                long seconds = allSeconds % 60;
                long minutes = (allSeconds / 60) % 60;
                long hours = (allSeconds / 3600) % 24;
                long days = (allSeconds / 3600) / 24;
                binding.timer.setText(String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds));
            }

            public void onFinish() {
                binding.timer.setText("А всё)");
            }

        }.start();

        binding.nameProject.setText(projectTitle);
        binding.problemTitle.setText(problemName);
        binding.problemDescription.setText(problemDescription);
        Glide
                .with(Advertisment.this)
                .load(photoProject)
                .into(binding.prLogo);
        Glide
                .with(Advertisment.this)
                .load(problemPhoto)
                .into(binding.advertPhoto);

        binding.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}