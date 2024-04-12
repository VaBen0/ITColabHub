package ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.ActivityEditProjectDemoBinding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;

public class EditProjectDemo extends AppCompatActivity {

    ActivityEditProjectDemoBinding binding;
    String demoTitle, demoDescription;

    Fragment fragmentDescriptionDemo, fragmentOtherDemo, fragmentLinkDemo;

    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityEditProjectDemoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);


        demoTitle = sharedPreferences.getString("DemoProjectTitle", "");
        demoDescription = sharedPreferences.getString("DemoProjectDescription", "");
        binding.projectName.setText(demoTitle);

        fragmentDescriptionDemo = Fragment.instantiate(this, EditProjectDescriptionDemo.class.getName());
        fragmentLinkDemo = Fragment.instantiate(this, EditProjectLinkDemo.class.getName());
        fragmentOtherDemo = Fragment.instantiate(this, EditProjectOtherDemo.class.getName());

        getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerViewDemo, fragmentDescriptionDemo)
                    .commit();
        binding.linearDescription.setVisibility(View.VISIBLE);
        binding.linkLine.setVisibility(View.INVISIBLE);
        binding.otherLine.setVisibility(View.INVISIBLE);

        binding.descriptionFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linearDescription.setVisibility(View.VISIBLE);
                binding.linkLine.setVisibility(View.INVISIBLE);
                binding.otherLine.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerViewDemo, fragmentDescriptionDemo)
                            .commit();
            }
        });

        binding.linksFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linkLine.setVisibility(View.VISIBLE);
                binding.linearDescription.setVisibility(View.INVISIBLE);
                binding.otherLine.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerViewDemo, fragmentLinkDemo)
                            .commit();
                if (binding.blockMenu.getVisibility() == View.VISIBLE) {
                    binding.blockMenu.setVisibility(View.GONE);
                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete2);
                    binding.blockMenu.startAnimation(hide);
                }
            }
        });

        binding.otherFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.otherLine.setVisibility(View.VISIBLE);
                binding.linearDescription.setVisibility(View.INVISIBLE);
                binding.linkLine.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerViewDemo, fragmentOtherDemo)
                            .commit();
                if (binding.blockMenu.getVisibility() == View.VISIBLE) {
                    binding.blockMenu.setVisibility(View.GONE);
                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete2);
                    binding.blockMenu.startAnimation(hide);
                }
            }
        });
    }

    public String getDemoDescription(){return demoDescription;}
    public void editDescription(String description){
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("UserReg", "true");
        ed.putString("DemoProjectDescription", description);
        ed.putString("DemoProjectTitle", binding.projectName.getText().toString());
        ed.apply();
    }

    public void endProject(){
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("UserReg", "true");
        ed.putBoolean("DemoProjectIsEnd", true);
        ed.apply();
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