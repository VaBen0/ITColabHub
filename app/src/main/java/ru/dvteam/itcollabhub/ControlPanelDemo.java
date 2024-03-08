package ru.dvteam.itcollabhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.service.controls.Control;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import ru.dvteam.itcollabhub.databinding.ActivityControlPanelDemoBinding;

public class ControlPanelDemo extends AppCompatActivity {

    ActivityControlPanelDemoBinding binding;
    String demoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityControlPanelDemoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        demoTitle = sPref.getString("DemoProjectTitle", "");
        int score = sPref.getInt("UserScore", 0);

        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanelDemo.this,R.color.blue));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.blue));
            binding.advertisments.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.blue));
            binding.editProject.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.blue));
            binding.projectPage.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.blue));
            binding.projectParticipants.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.blue));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_bg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_bg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_bg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_bg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_bg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_bg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanelDemo.this,R.color.green));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.green));
            binding.advertisments.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.green));
            binding.editProject.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.green));
            binding.projectPage.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.green));
            binding.projectParticipants.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.green));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_gg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_gg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_gg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_gg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_gg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_gg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanelDemo.this,R.color.brown));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.brown));
            binding.advertisments.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.brown));
            binding.editProject.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.brown));
            binding.projectPage.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.brown));
            binding.projectParticipants.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.brown));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_brg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_brg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_brg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_brg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_brg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_brg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanelDemo.this,R.color.light_gray));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.light_gray));
            binding.advertisments.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.light_gray));
            binding.editProject.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.light_gray));
            binding.projectPage.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.light_gray));
            binding.projectParticipants.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.light_gray));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_lgg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_lgg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_lgg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_lgg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_lgg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_lgg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanelDemo.this,R.color.ohra));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.ohra));
            binding.advertisments.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.ohra));
            binding.editProject.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.ohra));
            binding.projectPage.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.ohra));
            binding.projectParticipants.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.ohra));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_ohg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_ohg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_ohg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_ohg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_ohg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_ohg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanelDemo.this,R.color.red));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.red));
            binding.advertisments.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.red));
            binding.editProject.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.red));
            binding.projectPage.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.red));
            binding.projectParticipants.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.red));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_rg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_rg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_rg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_rg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_rg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_rg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 30000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanelDemo.this,R.color.orange));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.orange));
            binding.advertisments.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.orange));
            binding.editProject.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.orange));
            binding.projectPage.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.orange));
            binding.projectParticipants.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.orange));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_og);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_og);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_og);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_og);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_og);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_og);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanelDemo.this,R.color.violete));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.violete));
            binding.advertisments.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.violete));
            binding.editProject.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.violete));
            binding.projectPage.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.violete));
            binding.projectParticipants.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.violete));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_vg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_vg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_vg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_vg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_vg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_vg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanelDemo.this,R.color.main_green));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.main_green));
            binding.advertisments.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.main_green));
            binding.editProject.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.main_green));
            binding.projectPage.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.main_green));
            binding.projectParticipants.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanelDemo.this, R.color.main_green));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_mgg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_mgg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_mgg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_mgg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_mgg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_mgg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }

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

        //timeInMilliseconds = 86400000 - timeInMilliseconds;
        new CountDownTimer(millis, 1000) {

            public void onTick(long millisUntilFinished) {
                long allSeconds = millisUntilFinished / 1000;
                long seconds = allSeconds % 60;
                long minutes = (allSeconds / 60) % 60;
                long hours = (allSeconds / 3600) % 24;
                String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                binding.timer.setText(time);
            }

            public void onFinish() {
                binding.timer.setText("А всё)");
            }

        }.start();

        binding.nameProject.setText(demoTitle);

        binding.editProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanelDemo.this, EditProjectDemo.class);
                startActivity(intent);
            }
        });

        binding.purpProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanelDemo.this, ProjectPurposesDemo.class);
                startActivity(intent);
            }
        });

        binding.problemsProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanelDemo.this, ProjectProblemsDemo.class);
                startActivity(intent);
            }
        });

        binding.projectPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanelDemo.this, DemoProjectPage.class);
                startActivity(intent);
            }
        });
    }
}