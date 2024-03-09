package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
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
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityUsersProjectDemoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(UsersProjectDemo.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        titleDemo = sPref.getString("DemoProjectTitle", "");
        descriptionDemo = sPref.getString("DemoProjectDescription", "");
        int score = sPref.getInt("UserScore", 0);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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