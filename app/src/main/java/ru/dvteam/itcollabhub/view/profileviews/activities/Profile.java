package ru.dvteam.itcollabhub.view.profileviews.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.view.projectmenusviews.activities.ActivityProject;
import ru.dvteam.itcollabhub.Forum;
import ru.dvteam.itcollabhub.view.profileviews.fragments.Friends;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.view.profileviews.fragments.Rating;
import ru.dvteam.itcollabhub.UsersChosenTheme;

import ru.dvteam.itcollabhub.databinding.ActivityProfileBinding;
import ru.dvteam.itcollabhub.viewmodel.profileviewmodels.ProfileViewModel;

public class Profile extends AppCompatActivity {
    private final String[] wow = {"Хренос 2", "Кина не будет - электричество кончилось", "Ой, сломалось", "Караул!"};
    View back;
    ImageView dontWork;
    ActivityProfileBinding binding;
    Fragment fragmentRating, fragmentAwards, fragmentFriends;
    ProfileViewModel profileViewModel;
    public static Activity ma;
    private Boolean access = true;
    private int themeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        themeNum = sPref.getInt("ThemeNum", 1);

        UsersChosenTheme.setThemeNum(themeNum);
        UsersChosenTheme.setThemeActivity(this);

        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        fragmentRating = Fragment.instantiate(this, Rating.class.getName());
        fragmentFriends = Fragment.instantiate(this, Friends.class.getName());

        ma = this;

        profileViewModel.getProfileInformation(sPref);

        profileViewModel.getUserAllInfo().observe(this, profileInformation -> {
            String s = "Ваши очки: " + profileInformation.getUserScore();

            binding.nameu.setText(profileInformation.getUserName());
            binding.score.setText(s);
            Glide
                    .with(this)
                    .load(profileInformation.getUserImageUrl())
                    .into(binding.log);
            profileViewModel.isbanned();
        });

        profileViewModel.getBanned().observe(this, aBoolean -> {
            access = !aBoolean;
        });


        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(Profile.this, typedValue.resourceId);

        getWindow().setStatusBarColor(color);
        binding.linearProjects.setVisibility(View.INVISIBLE);
        binding.linearFriends.setVisibility(View.INVISIBLE);
        binding.linearRating.setVisibility(View.VISIBLE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profileMenus, fragmentRating)
                .commit();



        binding.projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //error();
//                friends_lin.setBackgroundColor(0);
//                rating_lin.setBackgroundColor(0);
//                projects_lin.setBackgroundResource(R.drawable.blue_line);
                //navController.navigate(R.id.projects);
            }
        });

        binding.friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access) {
                    binding.linearRating.setVisibility(View.INVISIBLE);
                    binding.linearProjects.setVisibility(View.INVISIBLE);
                    binding.linearFriends.setVisibility(View.VISIBLE);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.profileMenus, fragmentFriends)
                            .commit();
                }else{
                    Toast.makeText(Profile.this, "Вы заблокированы", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access) {
                    binding.linearRating.setVisibility(View.VISIBLE);
                    binding.linearProjects.setVisibility(View.INVISIBLE);
                    binding.linearFriends.setVisibility(View.INVISIBLE);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.profileMenus, fragmentRating)
                            .commit();
                }else{
                    Toast.makeText(Profile.this, "Вы заблокированы", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.themes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access) {
                    Intent intent = new Intent(Profile.this, ChooseThemeForApp.class);
                    profileViewModel.setDataForChooseTheme(themeNum);
                    startActivity(intent);
                }else{
                    Toast.makeText(Profile.this, "Вы заблокированы", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });
        binding.projectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access) {
                    Intent intent = new Intent(Profile.this, ActivityProject.class);
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    finish();
                }else{
                    Toast.makeText(Profile.this, "Вы заблокированы", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.forumMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access) {
                    Intent intent = new Intent(Profile.this, Forum.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Profile.this, "Вы заблокированы", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });
    }

    public String getMail(){return "mail";}
    public void changeActivity(){
        Intent intent = new Intent(Profile.this, GetFriend.class);
        profileViewModel.setDataForChooseTheme(themeNum);
        startActivity(intent);
    }
    public int getScore(){return 0;}
    public int getActiveProjects(){return 0;}
    public int getArchiveProjects(){return 0;}

    public void error(){
        back.setVisibility(View.VISIBLE);
        dontWork.setVisibility(View.VISIBLE);
        Toast.makeText(Profile.this, wow[(int) (Math.random() * 4)], Toast.LENGTH_SHORT).show();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        back.setVisibility(View.GONE);
                        dontWork.setVisibility(View.GONE);
                    }
                });
            }
        };
        thread.start();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        profileViewModel.getProfileInformation(sPref);
    }
}