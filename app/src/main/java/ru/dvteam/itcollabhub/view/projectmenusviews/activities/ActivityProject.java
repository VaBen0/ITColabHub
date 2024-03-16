package ru.dvteam.itcollabhub.view.projectmenusviews.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import ru.dvteam.itcollabhub.CreateProject;
import ru.dvteam.itcollabhub.EndProjects;
import ru.dvteam.itcollabhub.Forum;
import ru.dvteam.itcollabhub.view.projectmenusviews.fragments.MyProjects;
import ru.dvteam.itcollabhub.ProjectRequests;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.UsersChosenTheme;
import ru.dvteam.itcollabhub.UsersProjectDemo;
import ru.dvteam.itcollabhub.databinding.ActivityProjectBinding;

import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ActivityProjectViewModel;

public class ActivityProject extends AppCompatActivity {

    ActivityProjectBinding binding;
    int selectedColor, score;
    //private NavController navController;
    String mail, titleDemo, descriptionDemo, problemsDemo, purposesDemo, uriDemo;
    Boolean fragmentMan = true, projectEnd;
    private String[] wow = {"Хренос 2", "Кина не будет - электричество кончилось", "Ой, сломалось", "Караул!"};
    View back;
    ImageView dontWork;
    ActivityProjectViewModel activityProjectViewModel;
    Fragment fragmentPrMy, fragmentPrEnd;
    Boolean demoProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        /*SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);
        demoProjects = sPref.getBoolean("DemoProject", false);
        titleDemo = sPref.getString("DemoProjectTitle", "");
        uriDemo = sPref.getString("UriPath", "");
        projectEnd = sPref.getBoolean("IsEnd", false);*/

        super.onCreate(savedInstanceState);

        binding = ActivityProjectBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        activityProjectViewModel = new ViewModelProvider(this).get(ActivityProjectViewModel.class);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ActivityProject.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        /*LinearLayout profileMenu = findViewById(R.id.profile_menu);
        LinearLayout forumMenu = findViewById(R.id.forum_menu);
        ImageView plus = findViewById(R.id.plus);

        ImageView bguser = findViewById(R.id.bguser);
        TextView myProjects = findViewById(R.id.my_projects);
        TextView endProjects = findViewById(R.id.end_projects);
        LinearLayout projectMenu = findViewById(R.id.project_menu);
        ImageView notif = findViewById(R.id.notifications);
        View my_projects_lin = findViewById(R.id.linear_my_projects);
        View end_projects_lin = findViewById(R.id.linear_end_projects);
        back = findViewById(R.id.view3);
        dontWork = findViewById(R.id.imageView12);*/

        binding.linearEndProjects.setVisibility(View.INVISIBLE);

        fragmentPrMy = Fragment.instantiate(this, MyProjects.class.getName());
        fragmentPrEnd = Fragment.instantiate(this, EndProjects.class.getName());

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainerView3, fragmentPrMy, "mainFragment")
                .add(R.id.fragmentContainerView3, fragmentPrEnd, "secondFragment")
                .commit();

        getSupportFragmentManager().beginTransaction()
                .show(fragmentPrMy)
                .hide(fragmentPrEnd)
                .commit();

        /*PostDatas post = new PostDatas();
        post.postDataGetProjectReq("GRProjects", mail, new CallBackInt() {
            @Override
            public void invoke(String res) {
                if(res.equals("1")){
                    notif.setBackgroundResource(R.drawable.red_notify);
                }
                else{
                    notif.setBackgroundResource(R.drawable.white_notification);
                }
            }
        });*/
        binding.endProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMan = false;

                binding.linearMyProjects.setVisibility(View.INVISIBLE);
                binding.linearEndProjects.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .hide(fragmentPrMy)
                        .show(fragmentPrEnd)
                        .commit();
            }
        });
        binding.myProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMan = true;

                binding.linearEndProjects.setVisibility(View.INVISIBLE);
                binding.linearMyProjects.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .hide(fragmentPrEnd)
                        .show(fragmentPrMy)
                        .commit();
            }
        });
        binding.profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProject.this, Profile.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });
        binding.forumMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProject.this, Forum.class);
                startActivity(intent);
            }
        });
        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProject.this, CreateProject.class);
                startActivity(intent);
            }
        });
        binding.notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProject.this, ProjectRequests.class);
                startActivity(intent);
            }
        });
    }
    public String getMail(){
        return mail;
    }
    public int getScore(){
        return score;
    }
    public boolean getDemoProject(){
        return demoProjects;
    }
    public boolean getDemoEndProject(){
        return projectEnd;
    }
    public String getDemoTitle(){
        return titleDemo;
    }
    public String getDemoUri(){
        return uriDemo;
    }
    public void changeActivity(String id){
        Intent intent = new Intent(ActivityProject.this, UsersProject.class);
        intent.putExtra("projectId", id);
        startActivity(intent);
    }

    public void changeActivityDemo(){
        Intent intent = new Intent(ActivityProject.this, UsersProjectDemo.class);
        startActivity(intent);
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void error(){
        back.setVisibility(View.VISIBLE);
        dontWork.setVisibility(View.VISIBLE);
        Toast.makeText(ActivityProject.this, wow[(int) (Math.random() * 4)], Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onRestart() {
        super.onRestart();

        if(fragmentMan){
            MyProjects frag;
            frag = (MyProjects) getSupportFragmentManager().findFragmentByTag("mainFragment");
            if (frag != null) {
                //frag.createProjects();
            }else{
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }else{
            EndProjects frag;
            frag = (EndProjects) getSupportFragmentManager().findFragmentByTag("secondFragment");
            if (frag != null) {
                frag.createProjects();
            }else{
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void changeLine(View firstLine, View line){
        firstLine.setBackgroundResource(0);
        if(score < 100){
            line.setBackgroundResource(R.drawable.blue_line);
        }
        else if(score < 300){
            line.setBackgroundResource(R.drawable.green_line);
        }
        else if(score < 1000){
            line.setBackgroundResource(R.drawable.brown_line);
        }
        else if(score < 2500){
            line.setBackgroundResource(R.drawable.light_gray_line);
        }
        else if(score < 7000){
            line.setBackgroundResource(R.drawable.ohra_line);
        }
        else if(score < 17000){
            line.setBackgroundResource(R.drawable.red_line);
        }
        else if(score < 30000){
            line.setBackgroundResource(R.drawable.orange_line);
        }
        else if(score < 50000){
            line.setBackgroundResource(R.drawable.violete_line);
        }
        else{
            line.setBackgroundResource(R.drawable.blue_green_line);
        }
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