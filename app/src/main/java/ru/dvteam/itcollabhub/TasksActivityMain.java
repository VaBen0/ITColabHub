package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import ru.dvteam.itcollabhub.databinding.ActivityTasksMainBinding;

public class TasksActivityMain extends AppCompatActivity {

    ActivityTasksMainBinding binding;
    String mail, id, title, prPhoto, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        binding = ActivityTasksMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setActivityFormat(score);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");

        binding.nameProject.setText(title);

        Glide
                .with(TasksActivityMain.this)
                .load(prPhoto)
                .into(binding.prLogo);

        binding.add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksActivityMain.this, CreateTask.class);
                intent.putExtra("projectId", id);
                intent.putExtra("projectTitle", title);
                intent.putExtra("projectUrlPhoto", prPhoto);
                startActivity(intent);
            }
        });

        PostDatas post = new PostDatas();
        post.postDataGetProjectTasks("GetTasksFromProject", id, mail, new CallBackInt5() {
            @Override
            public void invoke(String s1, String s2, String s3) {
                String[] idsArr = s1.split("\uD83D\uDD70");
                String[] namesArr = s2.split("\uD83D\uDD70");
                String[] textsArr = s3.split("\uD83D\uDD70");
                Toast.makeText(TasksActivityMain.this, s1 + " " + s2 + " " + s3, Toast.LENGTH_SHORT).show();

                for (int i = 0; i < namesArr.length; i++) {
                    View custom = getLayoutInflater().inflate(R.layout.tasks_panel, null);
                    TextView nameu = (TextView) custom.findViewById(R.id.taskTitle);
                    TextView text = (TextView) custom.findViewById(R.id.taskText);
                    ImageView prImg = (ImageView) custom.findViewById(R.id.loadImg);

                    Glide
                            .with(TasksActivityMain.this)
                            .load(prPhoto)
                            .into(prImg);

                    nameu.setText(namesArr[i]);
                    //text.setText(textsArr[i]);

                    int finalI = i;

                    binding.tasksPlace.addView(custom);
                }
            }
        });
    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.blue));
            binding.add1.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.blue));
            binding.add2.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.green));
            binding.add1.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.green));
            binding.add2.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.brown));
            binding.add1.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.brown));
            binding.add2.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.light_gray));
            binding.add1.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.light_gray));
            binding.add2.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.ohra));
            binding.add1.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.ohra));
            binding.add2.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.red));
            binding.add1.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.red));
            binding.add2.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.red));
        }
        else if(score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this, R.color.orange));
            binding.add1.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.orange));
            binding.add2.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.violete));
            binding.add1.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.violete));
            binding.add2.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.main_green));
            binding.add1.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.main_green));
            binding.add2.setBackgroundTintList(ContextCompat.getColorStateList(TasksActivityMain.this, R.color.main_green));
        }
    }
}