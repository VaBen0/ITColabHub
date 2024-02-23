package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityProblemsParticipBinding;

public class ProblemsParticip extends AppCompatActivity {

    ActivityProblemsParticipBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        binding = ActivityProblemsParticipBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProblemsParticip.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProblemsParticip.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProblemsParticip.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProblemsParticip.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProblemsParticip.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProblemsParticip.this,R.color.red));
        }
        else if(score < 30000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProblemsParticip.this,R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProblemsParticip.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProblemsParticip.this,R.color.main_green));
        }

        /*Glide.with(this)
                .load("https://serveritcollabhub.development-team.ru/project_image/moaiitcollabhub.png")
                .into(binding.textView8);*/

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        String id = arguments.getString("projectId");
        String prId = arguments.getString("projectId1");
        String projectTitle = arguments.getString("projectTitle");
        String photoProject = arguments.getString("projectUrlPhoto");

        binding.nameProject.setText(projectTitle);
        Glide
                .with(ProblemsParticip.this)
                .load(photoProject)
                .into(binding.prLogo);

        PostDatas post = new PostDatas();
        post.postDataGetProblems("GetProblems", id, new CallBackInt() {
            @Override
            public void invoke(String res) {
                String[] inf = res.split("\uD83d\uDD70");
                //Toast.makeText(ProblemsParticip.this, inf[3], Toast.LENGTH_SHORT).show();
                for(int i = 0; i < inf.length; i += 4){
                    View custom = getLayoutInflater().inflate(R.layout.problem_panel2, null);
                    ImageView loadImg = custom.findViewById(R.id.problemImage);
                    TextView name = custom.findViewById(R.id.problemName);
                    TextView descr = custom.findViewById(R.id.problemDescription);
                    View back = custom.findViewById(R.id.view8);

                    if(inf[i + 2].equals("1")){
                        back.setBackgroundResource(R.drawable.green_transperent);
                    }

                    name.setText(inf[i]);
                    descr.setText(inf[i+1]);
                    Glide
                            .with(ProblemsParticip.this)
                            .load(inf[i+3])
                            .into(loadImg);
                    binding.reminderPlace.addView(custom);
                }
            }
        });
    }
}