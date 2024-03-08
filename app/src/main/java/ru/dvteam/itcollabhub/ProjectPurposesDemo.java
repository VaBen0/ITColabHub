package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityProjectPurposesDemoBinding;

public class ProjectPurposesDemo extends AppCompatActivity {

    ActivityProjectPurposesDemoBinding binding;
    private boolean clicked = false;
    private static final int PICK_IMAGES_CODE = 0;
    private String mediaPath = "";
    private Boolean acces = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    String purposes;
    int score;
    String prId, mail;
    int countPurposes = 0, countTicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProjectPurposesDemoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);
        purposes = sPref.getString("DemoProjectPurposes", "");

        binding.blockMenu.setVisibility(View.GONE);

        binding.blockMenu.setVisibility(View.VISIBLE);
        final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add2);
        binding.blockMenu.startAnimation(show);

        setPurposes();

        if (score < 100) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectPurposesDemo.this, R.color.blue));
        } else if (score < 300) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectPurposesDemo.this, R.color.green));
        } else if (score < 1000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectPurposesDemo.this, R.color.brown));
        } else if (score < 2500) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectPurposesDemo.this, R.color.light_gray));
        } else if (score < 7000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectPurposesDemo.this, R.color.ohra));
        } else if (score < 17000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectPurposesDemo.this, R.color.red));
        } else if (score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectPurposesDemo.this, R.color.orange));
        } else if (score < 50000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectPurposesDemo.this, R.color.violete));
        } else {
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectPurposesDemo.this, R.color.main_green));
        }

        binding.addPurp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.name.getText().toString().isEmpty() && !binding.description1.getText().toString().isEmpty()){
                    purposes += "\uD83D\uDD70" + binding.name.getText().toString() + "\uD83D\uDD70" +
                            binding.description1.getText().toString();
                    binding.name.setText("");
                    binding.description1.setText("");

                    SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putBoolean("DemoProject", true);
                    ed.putString("DemoProjectPurposes", purposes);
                    ed.apply();
                    binding.reminderPlace.removeAllViews();
                    setPurposes();
                }
            }
        });

    }

    private void setPurposes(){
        String[] inf = purposes.split("\uD83d\uDD70");
        countPurposes = inf.length / 2;

        for (int i = 0; i < inf.length; i += 2) {
            View custom = getLayoutInflater().inflate(R.layout.purpose_panel, null);
            ImageView loadImg = custom.findViewById(R.id.imagePurp);
            TextView name = custom.findViewById(R.id.name);
            TextView descr = custom.findViewById(R.id.description1);
            TextView title = custom.findViewById(R.id.problemTitlePanel);
            View back = custom.findViewById(R.id.view8);
            LinearLayout yesOrNo = custom.findViewById(R.id.yes_or_no);
            LinearLayout descript = custom.findViewById(R.id.description_purpose);
            Button yes = custom.findViewById(R.id.yes);
            Button no = custom.findViewById(R.id.no);

            int finalI = i;
            custom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (countTicked >= inf.length / 2 - 1 && inf[finalI + 2].equals("0")) {
                        Toast.makeText(ProjectPurposesDemo.this, "Эту цель нельзя отметить выполненной", Toast.LENGTH_SHORT).show();
                    } else if (!clicked && inf[finalI + 2].equals("0")) {
                        back.setBackgroundResource(R.drawable.progress_panel_background2);
                        descript.setVisibility(View.GONE);
                        yesOrNo.setVisibility(View.VISIBLE);
                        clicked = true;
                    }
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProjectPurposesDemo.this, "В демо проекте нельзя отметить цель выполненной", Toast.LENGTH_SHORT).show();
                    back.setBackgroundResource(R.drawable.progress_panel_background);
                    descript.setVisibility(View.VISIBLE);
                    yesOrNo.setVisibility(View.GONE);
                    clicked = false;
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back.setBackgroundResource(R.drawable.progress_panel_background);
                    descript.setVisibility(View.VISIBLE);
                    yesOrNo.setVisibility(View.GONE);
                    clicked = false;
                }
            });

            binding.addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProjectPurposesDemo.this, "В демо проекте нельзя", Toast.LENGTH_SHORT).show();
                }
            });

            title.setText(inf[i]);
            name.setText(inf[i]);
            descr.setText(inf[i + 1]);
            binding.reminderPlace.addView(custom, 0);
        }
    }
}