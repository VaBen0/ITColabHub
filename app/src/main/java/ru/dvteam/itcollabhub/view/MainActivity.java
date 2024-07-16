package ru.dvteam.itcollabhub.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.MainActivitySecondMenu;
import ru.dvteam.itcollabhub.databinding.ActivityMain2Binding;
import ru.dvteam.itcollabhub.databinding.ActivityMainBinding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.view.authorizeviews.LogIn;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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

            float width = binding.ITCollabHub.getPaint().measureText("ITCollabHub");

            Shader textShader1 = new LinearGradient(0, 0, width, binding.ITCollabHub.getTextSize(),
                    new int[]{Color.rgb(199, 8, 225), Color.rgb(236, 54, 75)},
                    null, Shader.TileMode.CLAMP);

            binding.ITCollabHub.getPaint().setShader(textShader1);

            float width2 = binding.togetherText.getPaint().measureText("Вместе в IT");

            Shader textShader2 = new LinearGradient(0, 0, width2, binding.togetherText.getTextSize(),
                    new int[]{Color.rgb(246, 168, 253), Color.rgb(207, 177, 241)},
                    null, Shader.TileMode.CLAMP);

            binding.togetherText.getPaint().setShader(textShader2);

            float width3 = binding.version.getPaint().measureText("1.0.0");

            Shader textShader3 = new LinearGradient(0, 0, width3, binding.version.getTextSize(),
                    new int[]{Color.rgb(119, 96, 212), Color.rgb(37, 201, 159)},
                    null, Shader.TileMode.CLAMP);

            binding.version.getPaint().setShader(textShader3);

            float width4 = binding.stadium.getPaint().measureText("Beta");

            Shader textShader4 = new LinearGradient(0, 0, width4, binding.stadium.getTextSize(),
                    new int[]{Color.rgb(119, 96, 212), Color.rgb(37, 201, 159)},
                    null, Shader.TileMode.CLAMP);

            binding.stadium.getPaint().setShader(textShader4);

            binding.nextBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    intent = new Intent(MainActivity.this, MainActivitySecondMenu.class);
                    startActivity(intent);
                }
            });

        }

    }
}
