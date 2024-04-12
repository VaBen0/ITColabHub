package ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt4;
import ru.dvteam.itcollabhub.databinding.ActivityUsersProject2Binding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;

public class UsersProject2 extends AppCompatActivity {

    ActivityUsersProject2Binding binding;
    String mail;
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UsersChosenTheme.setThemeActivity(this);
        super.onCreate(savedInstanceState);

        binding = ActivityUsersProject2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);
        mail = sharedPreferences.getString("UserMail", "");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding.projectProgress.setMax(100);
        binding.projectProgress.setProgress(75);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        String id = arguments.getString("projectId");

        PostDatas postDatas = new PostDatas();
        postDatas.postDataGetProjectInformation("GetProjectMainInformation", id, mail, new CallBackInt4() {
            @Override
            public void invoke(String name, String photoUrl, String descript, double isend, String purposes,
                               String problems, String peoples, String time, String time1, String tg, String vk, String webs,
                               String purposesids, String problemsids, String isl, String tasks, String isOpen, String isVisible) {
                binding.projectName.setText(name);
                Glide
                        .with(UsersProject2.this)
                        .load(photoUrl)
                        .into(binding.prLogo);
                binding.description.setText(descript);
                binding.tgIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!tg.isEmpty()) {
                            Toast.makeText(UsersProject2.this, tg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                binding.vkIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!vk.isEmpty()) {
                            Toast.makeText(UsersProject2.this, vk, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                binding.webIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!webs.isEmpty()) {
                            Toast.makeText(UsersProject2.this, webs, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}