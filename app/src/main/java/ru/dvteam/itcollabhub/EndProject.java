package ru.dvteam.itcollabhub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.databinding.ActivityEndProjectBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class EndProject extends AppCompatActivity {

    ActivityEndProjectBinding binding;

    private String id, title, description, prPhoto, mail;
    private ArrayList<String>ins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityEndProjectBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(EndProject.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        description = arguments.getString("projectDescription");

        ins = new ArrayList<>();

        binding.nameProject.setText(title);
        Glide
                .with(EndProject.this)
                .load(prPhoto)
                .into(binding.prLogo);

        PostDatas post = new PostDatas();
        post.postDataGetProjectParticipant("GetPeoplesFromProjects", id, mail, new CallBackInt5() {
            @Override
            public void invoke(String ids, String names, String photos) {

                binding.main.removeAllViews();
                String[] idsArr = ids.split("\uD83D\uDD70");
                String[] namesArr = names.split("\uD83D\uDD70");
                String[] photosArr = photos.split("\uD83D\uDD70");

                for (int i = 0; i < idsArr.length; i++) {
                    View custom = getLayoutInflater().inflate(R.layout.friend_window2, null);
                    TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                    ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                    System.out.println(photosArr[i]);
                    SeekBar points = custom.findViewById(R.id.slider);
                    ProgressBar progressBar = custom.findViewById(R.id.progress_of_tasks);
                    TextView numsPercentGetChell = custom.findViewById(R.id.percents);
                    ins.add("50");
                    final int index = i;

                    points.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            String nums = progress + "";
                            numsPercentGetChell.setText(nums);
                            progressBar.setProgress(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            ins.set(index, points.getProgress() + "");
                        }
                    });

                    Glide
                            .with(EndProject.this)
                            .load(photosArr[i])
                            .into(loadImage);
                    nameu.setText(namesArr[i]);

                    binding.main.addView(custom);
                }
                View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                binding.main.addView(empty);
            }
        });

        binding.endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insert = String.join(",", ins);
                System.out.println(insert);
                PostDatas post = new PostDatas();
                post.postDataSetProjectIsEnd("SetProjectIsEnd", mail, id, insert, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        finish();
                    }
                });
            }
        });

    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(EndProject.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(EndProject.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(EndProject.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(EndProject.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(EndProject.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(EndProject.this,R.color.red));
        }
        else if(score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(EndProject.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(EndProject.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(EndProject.this,R.color.main_green));
        }
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