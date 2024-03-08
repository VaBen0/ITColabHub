package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.dvteam.itcollabhub.databinding.ActivityUsersProjectDemoBinding;

public class UsersProjectDemo extends AppCompatActivity {

    ActivityUsersProjectDemoBinding binding;
    String descriptionDemo, titleDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUsersProjectDemoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        titleDemo = sPref.getString("DemoProjectTitle", "");
        descriptionDemo = sPref.getString("DemoProjectDescription", "");
        int score = sPref.getInt("UserScore", 0);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if(score < 100){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_bgreen);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_bgreen);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProjectDemo.this, R.color.blue));
        }
        else if(score < 300){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_gbrown);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_gbrown);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProjectDemo.this, R.color.green));
        }
        else if(score < 1000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_brlg);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_brlg);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProjectDemo.this, R.color.brown));
        }
        else if(score < 2500){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_lgoh);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_lgoh);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProjectDemo.this, R.color.light_gray));
        }
        else if(score < 7000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_ohred);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_ohred);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProjectDemo.this, R.color.ohra));
        }
        else if(score < 17000) {
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_redora);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_redora);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProjectDemo.this, R.color.red));
        }
        else if(score < 30000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_vo);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_vo);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProjectDemo.this, R.color.orange));
        }
        else if(score < 50000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProjectDemo.this, R.color.violete));
        }
        else{
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProjectDemo.this, R.color.main_green));
        }

        binding.description.setText(descriptionDemo);
        binding.projectName.setText(titleDemo);

        binding.tgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UsersProjectDemo.this, "Demo", Toast.LENGTH_SHORT).show();
            }
        });

        binding.vkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UsersProjectDemo.this, "Demo", Toast.LENGTH_SHORT).show();
            }
        });

        binding.webIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UsersProjectDemo.this, "Demo", Toast.LENGTH_SHORT).show();
            }
        });

        binding.controlPanelMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersProjectDemo.this, ControlPanelDemo.class);
                startActivity(intent);
            }
        });
    }
}