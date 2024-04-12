package ru.dvteam.itcollabhub.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.databinding.ActivityMainBinding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.view.authorizeviews.LogIn;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);

        String savedText = sharedPreferences.getString("UserReg", "");

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
