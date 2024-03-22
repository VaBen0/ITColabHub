package ru.dvteam.itcollabhub.view.projectmenusviews.activities.advertisments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityAdvertismentBinding;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.Information;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;

public class Advertisment extends AppCompatActivity {

    ActivityAdvertismentBinding binding;
    String prId, projectTitle, photoProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UsersChosenTheme.setThemeActivity(this);
        super.onCreate(savedInstanceState);

        binding = ActivityAdvertismentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        prId = ProjectId.getInstance().getProjectId();
        projectTitle = GlobalProjectInformation.getInstance().getProjectTitle();
        photoProject = GlobalProjectInformation.getInstance().getProjectLog();
        String problemPhoto = Information.getInstance().getObjImage();
        String problemName = Information.getInstance().getObjTitle();
        String problemDescription = Information.getInstance().getObjText();

//        long millis = 259200000;
//
//        new CountDownTimer(millis, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                long allSeconds = millisUntilFinished / 1000;
//                long seconds = allSeconds % 60;
//                long minutes = (allSeconds / 60) % 60;
//                long hours = (allSeconds / 3600) % 24;
//                long days = (allSeconds / 3600) / 24;
//                binding.timer.setText(String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds));
//            }
//
//            public void onFinish() {
//                binding.timer.setText("А всё)");
//            }
//
//        }.start();

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