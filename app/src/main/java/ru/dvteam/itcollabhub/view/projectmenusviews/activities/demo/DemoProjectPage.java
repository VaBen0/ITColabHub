package ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.databinding.ActivityDemoProjectPageBinding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;

public class DemoProjectPage extends AppCompatActivity {

    ActivityDemoProjectPageBinding binding;
    String demoTitle, demoDescription;
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDemoProjectPageBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);

        demoTitle = sharedPreferences.getString("DemoProjectTitle", "");
        demoDescription = sharedPreferences.getString("DemoProjectDescription", "");

        binding.projectName.setText(demoTitle);
        binding.description.setText(demoDescription);
        binding.webLink.setText("Ссылки нет (Demo)");


    }
}