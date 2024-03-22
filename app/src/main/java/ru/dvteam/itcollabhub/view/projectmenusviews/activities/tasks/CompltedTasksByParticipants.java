package ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.databinding.ActivityCompltedTasksByParticipantsBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;

public class CompltedTasksByParticipants extends AppCompatActivity {

    ActivityCompltedTasksByParticipantsBinding binding;
    private String mail, idPr, title, prPhoto, taskTitle, taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityCompltedTasksByParticipantsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        idPr = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        taskTitle = arguments.getString("taskTitle");
        taskId = arguments.getString("taskId");
        System.out.println(idPr + " " + title + " " + prPhoto + " " + taskTitle + " " + taskId);

        binding.taskTitle.setText(taskTitle);
        binding.nameProject.setText(title);
        binding.nameProject.setText(title);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.prLogo);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.prLogo);


        PostDatas post = new PostDatas();
        post.postDataGetPeoplesComplitedWork("GetWorksFromTask", idPr, mail, taskId, new CallBackInt5() {
            @Override
            public void invoke(String id, String name, String photo) {
                System.out.println(photo);
                String[] idsArr = id.split("\uD83D\uDD70");
                String[] namesArr = name.split("\uD83D\uDD70");
                String[] photosArr = photo.split("\uD83D\uDD70");
                if (!idsArr[0].equals("Ошибка")) {
                    binding.noCompleted.setVisibility(View.GONE);
                    for (int i = 0; i < idsArr.length; i++) {
                        View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                        ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                        ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                        TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                        ImageView messege = (ImageView) custom.findViewById(R.id.notban);

                        userCircle.setVisibility(View.GONE);
                        project1.setVisibility(View.GONE);
                        messege.setImageResource(R.drawable.upload_button_black);

                        Glide
                                .with(CompltedTasksByParticipants.this)
                                .load(photosArr[i])
                                .into(loadImage);
                        nameu.setText(namesArr[i]);

                        int finalI1 = i;
                        custom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println(id);
                                Intent intent = new Intent(CompltedTasksByParticipants.this, ActivityWork.class);
                                intent.putExtra("projectId", idPr);
                                intent.putExtra("projectTitle", title);
                                intent.putExtra("projectUrlPhoto", prPhoto);
                                intent.putExtra("taskTitle", taskTitle);
                                intent.putExtra("workId", idsArr[finalI1]);
                                intent.putExtra("userName", namesArr[finalI1]);
                                intent.putExtra("userPhoto", photosArr[finalI1]);
                                startActivity(intent);
                            }
                        });
                        binding.main.addView(custom);
                    }
                    View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                    binding.main.addView(empty);
                }
            }
        });

    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(CompltedTasksByParticipants.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CompltedTasksByParticipants.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(CompltedTasksByParticipants.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(CompltedTasksByParticipants.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(CompltedTasksByParticipants.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(CompltedTasksByParticipants.this,R.color.red));
        }
        else if(score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(CompltedTasksByParticipants.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(CompltedTasksByParticipants.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CompltedTasksByParticipants.this,R.color.main_green));
        }
    }

    public void setThemeActivity(){
        int themeType = UsersChosenTheme.getThemeNum();

        switch (themeType){
            case(1):
                setTheme(R.style.Theme_ITCollabHub_Blue);
                break;
            case(2):
                setTheme(R.style.Theme_ITCollabHub_Green);
                break;
            case(3):
                setTheme(R.style.Theme_ITCollabHub_Brown);
                break;
            case(4):
                setTheme(R.style.Theme_ITCollabHub_PinkGold);
                break;
            case(5):
                setTheme(R.style.Theme_ITCollabHub_Ohra);
                break;
            case(6):
                setTheme(R.style.Theme_ITCollabHub_Red);
                break;
            case(7):
                setTheme(R.style.Theme_ITCollabHub_Orange);
                break;
            case(8):
                setTheme(R.style.Theme_ITCollabHub_Violete);
                break;
            case(9):
                setTheme(R.style.Theme_ITCollabHub_BlueGreen);
                break;
        }

    }
}