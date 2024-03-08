package ru.dvteam.itcollabhub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import ru.dvteam.itcollabhub.databinding.ActivityEditProjectDemoBinding;

public class EditProjectDemo extends AppCompatActivity {

    ActivityEditProjectDemoBinding binding;
    String demoTitle, demoDescription;

    Fragment fragmentDescriptionDemo, fragmentOtherDemo, fragmentLinkDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProjectDemoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        demoTitle = sPref.getString("DemoProjectTitle", "");
        demoDescription = sPref.getString("DemoProjectDescription", "");
        int score = sPref.getInt("UserScore", 0);

        binding.projectName.setText(demoTitle);

        fragmentDescriptionDemo = Fragment.instantiate(this, EditProjectDescriptionDemo.class.getName());
        fragmentLinkDemo = Fragment.instantiate(this, EditProjectLinkDemo.class.getName());
        fragmentOtherDemo = Fragment.instantiate(this, EditProjectOtherDemo.class.getName());

        changeLine(score, binding.linearDescription, binding.linkLine, binding.otherLine);
        getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerViewDemo, fragmentDescriptionDemo)
                    .commit();

        binding.descriptionFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLine(score, binding.linearDescription, binding.linkLine, binding.otherLine);
                getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerViewDemo, fragmentDescriptionDemo)
                            .commit();
            }
        });

        binding.linksFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLine(score, binding.linkLine, binding.linearDescription, binding.otherLine);
                getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerViewDemo, fragmentLinkDemo)
                            .commit();
            }
        });

        binding.otherFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLine(score, binding.otherLine, binding.linearDescription, binding.linkLine);
                getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerViewDemo, fragmentOtherDemo)
                            .commit();
            }
        });
    }

    private void changeLine(int score, View firstLine, View secondLine, View thirdLine){
        secondLine.setBackgroundResource(0);
        thirdLine.setBackgroundResource(0);
        if(score < 100){
            firstLine.setBackgroundResource(R.drawable.blue_line);
        }
        else if(score < 300){
            firstLine.setBackgroundResource(R.drawable.green_line);
        }
        else if(score < 1000){
            firstLine.setBackgroundResource(R.drawable.brown_line);
        }
        else if(score < 2500){
            firstLine.setBackgroundResource(R.drawable.light_gray_line);
        }
        else if(score < 7000){
            firstLine.setBackgroundResource(R.drawable.ohra_line);
        }
        else if(score < 17000){
            firstLine.setBackgroundResource(R.drawable.red_line);
        }
        else if(score < 30000){
            firstLine.setBackgroundResource(R.drawable.orange_line);
        }
        else if(score < 50000){
            firstLine.setBackgroundResource(R.drawable.violete_line);
        }
        else{
            firstLine.setBackgroundResource(R.drawable.blue_green_line);
        }
        if (binding.blockMenu.getVisibility() == View.VISIBLE) {
            binding.blockMenu.setVisibility(View.GONE);
            final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete2);
            binding.blockMenu.startAnimation(hide);
        }
    }

    public String getDemoDescription(){return demoDescription;}
    public void editDescription(String description){
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("UserReg", "true");
        ed.putString("DemoProjectDescription", description);
        ed.putString("DemoProjectTitle", binding.projectName.getText().toString());
        ed.apply();
    }

    public void endProject(){
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("UserReg", "true");
        ed.putBoolean("DemoProjectIsEnd", true);
        ed.apply();
    }
}