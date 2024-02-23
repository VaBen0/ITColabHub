package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityUsersProjectBinding;

public class UsersProject2 extends AppCompatActivity {

    ActivityUsersProjectBinding binding;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding = ActivityUsersProjectBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        if(score < 100){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_bgreen);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_bgreen);
            binding.projectProgress.setProgressDrawable(progressDrawable);
        }
        else if(score < 300){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_gbrown);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_gbrown);
            binding.projectProgress.setProgressDrawable(progressDrawable);
        }
        else if(score < 1000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_brlg);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_brlg);
            binding.projectProgress.setProgressDrawable(progressDrawable);
        }
        else if(score < 2500){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_lgoh);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_lgoh);
            binding.projectProgress.setProgressDrawable(progressDrawable);
        }
        else if(score < 7000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_ohred);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_ohred);
            binding.projectProgress.setProgressDrawable(progressDrawable);
        }
        else if(score < 17000) {
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_redora);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_redora);
            binding.projectProgress.setProgressDrawable(progressDrawable);
        }
        else if(score < 30000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_vo);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_vo);
            binding.projectProgress.setProgressDrawable(progressDrawable);
        }
        else if(score < 50000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setProgressDrawable(progressDrawable);
        }
        else{
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setProgressDrawable(progressDrawable);
        }

        binding.projectProgress.setMax(100);
        binding.projectProgress.setProgress(75);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        String id = arguments.getString("projectId");

        PostDatas postDatas = new PostDatas();
        postDatas.postDataGetProjectInformation("GetProjectMainInformation", id, mail, new CallBackInt4() {
            @Override
            public void invoke(String name, String photoUrl, String descript, double isend, String purposes,
                               String problems, String peoples, String time, String time1, String tg, String vk, String webs,
                               String purposesids, String problemsids, String isl) {
                binding.projectName.setText(name);
                Glide
                        .with(UsersProject2.this)
                        .load(photoUrl)
                        .into(binding.prLogo);
                binding.description.setText(descript);
            }
        });

        binding.controlPanelMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersProject2.this, ControlPanel.class);
                intent.putExtra("projectId", id);
                startActivity(intent);
            }
        });
    }

}