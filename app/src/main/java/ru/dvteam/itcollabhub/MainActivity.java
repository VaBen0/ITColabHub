package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.dvteam.itcollabhub.databinding.ActivityMainBinding;
import ru.dvteam.itcollabhub.view.LogIn;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString("UserReg", "");

        if(savedText.equals("true")){
            Intent intent;
            intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
        }
        else {
            setContentView(binding.getRoot());

            Typeface face=Typeface.createFromAsset(getAssets(),"font/ArchitectsDaughter-Regular.ttf");
            binding.collaborotory.setTypeface(face);
            binding.it.setTypeface(face);
            binding.hub.setTypeface(face);

            binding.nextBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    intent = new Intent(MainActivity.this, LogIn.class);
                    startActivity(intent);
                }
            });

        }

    }
}
