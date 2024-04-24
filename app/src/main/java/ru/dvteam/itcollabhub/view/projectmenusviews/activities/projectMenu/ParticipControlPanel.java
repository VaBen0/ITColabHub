package ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu;

import static ru.dvteam.itcollabhub.view.UsersChosenTheme.setThemeActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackActivityProject;
import ru.dvteam.itcollabhub.databinding.ActivityControlPanelBinding;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.view.adapter.ReminderAdapter;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.advertisments.Advertisment;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.advertisments.ProjectAdvertisments;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.files.ProjectFiles;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.problems.Problems;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.problems.ProblemsParticip;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.purposes.Purpose;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.purposes.PurposeParticipiant;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.TaskActivityWatch;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.TasksActivityMain;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ControlPanelViewModel;

import ru.dvteam.itcollabhub.databinding.ActivityParticipControlPanelBinding;

public class ParticipControlPanel extends AppCompatActivity {

    ActivityParticipControlPanelBinding binding;
    ControlPanelViewModel controlPanelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity(this);
        super.onCreate(savedInstanceState);

        binding = ActivityParticipControlPanelBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        controlPanelViewModel = new ViewModelProvider(this).get(ControlPanelViewModel.class);
        GlobalProjectInformation.getInstance().setEnd(false);

        controlPanelViewModel.getProjectInformation().observe(this, projectInformation -> {

            binding.nameProject.setText(projectInformation.getProjectTitle());
            Glide
                    .with(ParticipControlPanel.this)
                    .load(projectInformation.getProjectLogo())
                    .into(binding.prLogo);

            if(((int)(parse(projectInformation.getProjectPurposes()) * 100) == 100)){
                Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_greeen);
                binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_greeen);
                binding.purpProgress.setProgressDrawable(progressDrawable);
            }
            if(((int)(parse(projectInformation.getProjectProblems()) * 100) == 100)){
                Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_greeen);
                binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_greeen);
                binding.problemsProgress.setProgressDrawable(progressDrawable);
            }
            if(((int)(parse(projectInformation.getProjectTasks()) * 100) == 100)){
                Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_greeen);
                binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_greeen);
                binding.progressOfTasks.setProgressDrawable(progressDrawable);
            }

            ProgressBar purposesProgress = findViewById(R.id.purp_progress);
            ProgressBar problemsProgress = findViewById(R.id.problems_progress);
            ProgressBar tasksProgress = findViewById(R.id.progress_of_tasks);
            purposesProgress.setMax(100);
            problemsProgress.setMax(100);
            tasksProgress.setMax(100);

            ObjectAnimator animation = ObjectAnimator.ofInt(purposesProgress, "progress", 0,
                    (int)(parse(projectInformation.getProjectPurposes()) * 100));
            animation.setStartDelay(300);
            animation.setDuration(1000);
            animation.setAutoCancel(true);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();

            ObjectAnimator anim = ObjectAnimator.ofInt(problemsProgress, "progress", 0,
                    (int)(parse(projectInformation.getProjectProblems()) * 100));
            anim.setStartDelay(300);
            anim.setDuration(1000);
            anim.setAutoCancel(true);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();

            ObjectAnimator anim3 = ObjectAnimator.ofInt(tasksProgress, "progress", 0,
                    (int)(parse(projectInformation.getProjectTasks()) * 100));
            anim3.setStartDelay(300);
            anim3.setDuration(1000);
            anim3.setAutoCancel(true);
            anim3.setInterpolator(new DecelerateInterpolator());
            anim3.start();
        });

        controlPanelViewModel.setProjectInformation();

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ParticipControlPanel.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        fillReminderPlace();

        Date date = new Date();
        LocalTime current = null;
        long millis = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            current = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
            millis = ChronoUnit.MILLIS.between(current, LocalTime.MAX);
        }
        else {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            millis = (c.getTimeInMillis() - System.currentTimeMillis());
        }
        new CountDownTimer(millis, 1000) {

            public void onTick(long millisUntilFinished) {
                long allSeconds = millisUntilFinished / 1000;
                long seconds = allSeconds % 60;
                long minutes = (allSeconds / 60) % 60;
                long hours = (allSeconds / 3600) % 24;
                binding.timer.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }

            public void onFinish() {
                binding.timer.setText("А всё)");
            }

        }.start();

        binding.purpProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(controlPanelViewModel.getIsl().equals("0")){
                    intent = new Intent(ParticipControlPanel.this, PurposeParticipiant.class);
                }
                else {
                    intent = new Intent(ParticipControlPanel.this, Purpose.class);
                }
                startActivity(intent);
            }
        });

        binding.problemsProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(controlPanelViewModel.getIsl().equals("0")){
                    intent = new Intent(ParticipControlPanel.this, ProblemsParticip.class);
                }
                else {
                    intent = new Intent(ParticipControlPanel.this, Problems.class);
                }
                startActivity(intent);
            }
        });

        binding.advertisments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipControlPanel.this, ProjectAdvertisments.class);
                startActivity(intent);
            }
        });

        binding.projectFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipControlPanel.this, ProjectFiles.class);
                startActivity(intent);
            }
        });

        binding.progressOfTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipControlPanel.this, TasksActivityMain.class);
                startActivity(intent);
            }
        });
        binding.projectPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipControlPanel.this, ProjectPage.class);
                startActivity(intent);
            }
        });
    }
    double parse(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }

    @Override
    protected void onRestart() {
        controlPanelViewModel.setProjectInformation();
        controlPanelViewModel.setAdverts();
        super.onRestart();
    }

    public void fillReminderPlace(){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.reminderPlace.setLayoutManager(layoutManager);

        controlPanelViewModel.getAdds1().observe(this, dataOfWatchers -> {
            if(dataOfWatchers.isEmpty()){
                binding.noReminder.setVisibility(View.VISIBLE);
            }else{
                binding.noReminder.setVisibility(View.GONE);
            }
            ReminderAdapter adapter;
            adapter = new ReminderAdapter(dataOfWatchers, this, new CallBackActivityProject() {
                @Override
                public void setActivity(String id) {
                    Intent intent;
                    if(id.equals("1")) {
                        intent = new Intent(ParticipControlPanel.this, Advertisment.class);
                    }else{
                        intent = new Intent(ParticipControlPanel.this, TaskActivityWatch.class);
                    }
                    startActivity(intent);
                }
            });
            binding.reminderPlace.setAdapter(adapter);
        });

        controlPanelViewModel.setAdverts();
    }
}