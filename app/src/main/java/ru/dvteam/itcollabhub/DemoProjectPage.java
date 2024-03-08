package ru.dvteam.itcollabhub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.dvteam.itcollabhub.databinding.ActivityDemoProjectPageBinding;

public class DemoProjectPage extends AppCompatActivity {

    ActivityDemoProjectPageBinding binding;
    String demoTitle, demoDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDemoProjectPageBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        demoTitle = sPref.getString("DemoProjectTitle", "");
        demoDescription = sPref.getString("DemoProjectDescription", "");
        int score = sPref.getInt("UserScore", 0);

        binding.projectName.setText(demoTitle);
        binding.description.setText(demoDescription);
        binding.webLink.setText("Ссылки нет (Demo)");


    }
}