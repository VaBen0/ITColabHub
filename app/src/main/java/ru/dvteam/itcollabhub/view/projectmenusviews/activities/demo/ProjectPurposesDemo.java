package ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.ActivityProjectPurposesDemoBinding;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;

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
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityProjectPurposesDemoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ProjectPurposesDemo.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);
        purposes = sPref.getString("DemoProjectPurposes", "");

        binding.blockMenu.setVisibility(View.GONE);

        binding.blockMenu.setVisibility(View.VISIBLE);
        final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add2);
        binding.blockMenu.startAnimation(show);

        setPurposes();

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