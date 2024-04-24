package ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.databinding.ActivityUsersProjectBinding;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.UsersProjectViewModel;

public class UsersProject extends AppCompatActivity {

    ActivityUsersProjectBinding binding;
    UsersProjectViewModel usersProjectViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding = ActivityUsersProjectBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        usersProjectViewModel = new ViewModelProvider(this).get(UsersProjectViewModel.class);

        usersProjectViewModel.getProjectInformationData().observe(this, projectInformation -> {
            binding.projectName.setText(projectInformation.getProjectTitle());
            Glide
                    .with(UsersProject.this)
                    .load(projectInformation.getProjectLogo())
                    .into(binding.prLogo);
            String percents = (int) projectInformation.getIsEnd() + ".0%";
            binding.projectPercents.setText(percents);
            binding.tgIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UsersProject.this, projectInformation.getProjectTgLink(), Toast.LENGTH_SHORT).show();
                }
            });
            binding.vkIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UsersProject.this, projectInformation.getProjectVkLink(), Toast.LENGTH_SHORT).show();

                }
            });
            binding.webIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UsersProject.this, projectInformation.getProjectWebLink(), Toast.LENGTH_SHORT).show();
                }
            });

            ObjectAnimator animation = ObjectAnimator.ofInt(binding.projectProgress, "progress", 0, (int) projectInformation.getIsEnd());
            animation.setStartDelay(300);
            animation.setDuration(1000);
            animation.setAutoCancel(true);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();

            final ValueAnimator anim = ValueAnimator.ofFloat(0, (int) projectInformation.getIsEnd());
            anim.setStartDelay(300);
            anim.setDuration(1000);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                    String res = anim.getAnimatedValue().toString();
                    if(res.length() >= 4){
                        res = res.substring(0, 4) + "%";
                    }
                    else if(res.length() == 3){
                        res = res.substring(0, 3) + "%";
                    }
                    else{
                        res = res.substring(0, 2) + "0%";
                    }

                    binding.projectPercents.setText(res);
                }
            });

            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();

            if(projectInformation.getProjectIsLeader().equals("1")){
                binding.controlPanelMove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UsersProject.this, ControlPanel.class);
                        startActivity(intent);
                    }
                });
            }

            else{
                binding.controlPanelMove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UsersProject.this, ParticipControlPanel.class);
                        intent.putExtra("projectId", ProjectId.getInstance().getProjectId());
                        System.out.println("lox");
                        startActivity(intent);
                    }
                });
            }

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

        usersProjectViewModel.getProjectInformation();

        binding.projectProgress.setMax(100);
    }

    @Override
    protected void onRestart() {
        usersProjectViewModel.getProjectInformation();
        super.onRestart();
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