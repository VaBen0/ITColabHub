package ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import  android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.databinding.ActivityUsersProject3Binding;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.UsersProject3ViewModel;

public class UsersProject3 extends AppCompatActivity {

    ActivityUsersProject3Binding binding;
    UsersProject3ViewModel usersProject3ViewModel;
    String mail = MailGlobalInfo.getInstance().getUserMail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UsersChosenTheme.setThemeActivity(this);
        super.onCreate(savedInstanceState);

        binding = ActivityUsersProject3Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        usersProject3ViewModel = new ViewModelProvider(this).get(UsersProject3ViewModel.class);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        binding.projectProgress.setMax(100);
//        binding.projectProgress.setProgress(100);

        usersProject3ViewModel.getProjectInformationData().observe(this, projectInformation -> {
            binding.projectName.setText(projectInformation.getProjectTitle());
            Glide
                    .with(UsersProject3.this)
                    .load(projectInformation.getProjectLogo())
                    .into(binding.prLogo);
            String percents = "100%";
            binding.projectPercents.setText(percents);
            binding.tgIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UsersProject3.this, projectInformation.getProjectTgLink(), Toast.LENGTH_SHORT).show();
                }
            });
            binding.vkIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UsersProject3.this, projectInformation.getProjectVkLink(), Toast.LENGTH_SHORT).show();

                }
            });
            binding.webIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UsersProject3.this, projectInformation.getProjectWebLink(), Toast.LENGTH_SHORT).show();
                }
            });

            ObjectAnimator animation = ObjectAnimator.ofInt(binding.projectProgress, "progress", 0, 100);
            animation.setStartDelay(300);
            animation.setDuration(1000);
            animation.setAutoCancel(true);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();

            final ValueAnimator anim = ValueAnimator.ofFloat(0, 100);
            anim.setStartDelay(300);
            anim.setDuration(1000);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                    String res = anim.getAnimatedValue().toString();
                    if(res.length() >= 4){
                        res = res.substring(0, 3) + "%";
                    }
                    else if(res.length() == 3){
                        res = res.substring(0, 2) + "%";
                    }
                    else{
                        res = res.substring(0, 2) + "0%";
                    }

                    binding.projectPercents.setText(res);
                }
            });

            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();


            binding.controlPanelMove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UsersProject3.this, ControlPanel3.class);
                    startActivity(intent);
                }
            });


            binding.description.setText(projectInformation.getProjectDescription());
            String purpose = "Выполненных целей: " + projectInformation.getProjectPurposes();
            String problem = "Выполненных задач: " + projectInformation.getProjectProblems();
            String peopleCount = "Количество участников: " + projectInformation.getProjectPeoples();
            binding.completePurposes.setText(purpose);
            binding.completeProblems.setText(problem);
            binding.numOfPeoples.setText(peopleCount);
            binding.date.setText(projectInformation.getProjectTime());
            binding.time.setText(projectInformation.getProjectTime1());
        });

        usersProject3ViewModel.getProjectInformation();

        binding.projectProgress.setMax(100);
    }

    @Override
    protected void onRestart() {
        usersProject3ViewModel.getProjectInformation();
        super.onRestart();
    }

}