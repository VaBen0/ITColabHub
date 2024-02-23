package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ActivityProject extends AppCompatActivity {

    int selectedColor, score;
    private NavController navController;
    String mail;

    private String[] wow = {"Хренос 2", "Кина не будет - электричество кончилось", "Ой, сломалось", "Караул!"};
    View back;
    ImageView dontWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        deleteCache(ActivityProject.this);

        LinearLayout profileMenu = findViewById(R.id.profile_menu);
        LinearLayout forumMenu = findViewById(R.id.forum_menu);
        ImageButton plus = findViewById(R.id.plus);

        ImageView bguser = findViewById(R.id.bguser);
        TextView myProjects = findViewById(R.id.my_projects);
        TextView endProjects = findViewById(R.id.end_projects);
        View fragment = findViewById(R.id.nav_host_fragment);
        LinearLayout projectMenu = findViewById(R.id.project_menu);
        ImageView notif = findViewById(R.id.notifications);
        View my_projects_lin = findViewById(R.id.linear_my_projects);
        View end_projects_lin = findViewById(R.id.linear_end_projects);
        back = findViewById(R.id.view3);
        dontWork = findViewById(R.id.imageView12);

        PostDatas post = new PostDatas();
        post.postDataGetProjectReq("GRProjects", mail, new CallBackInt() {
            @Override
            public void invoke(String res) {
                if(res.equals("1")){
                    notif.setBackgroundResource(R.drawable.notification_true);
                }
                else{
                    notif.setBackgroundResource(R.drawable.white_notification);
                }
            }
        });

        if(score < 100){
            bguser.setBackgroundResource(R.drawable.gradient_blue);
            selectedColor = Color.parseColor("#B20000FF");
            my_projects_lin.setBackgroundResource(R.drawable.blue_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityProject.this,R.color.blue));
            plus.setBackgroundResource(R.drawable.ad);
        }
        else if(score < 300){
            bguser.setBackgroundResource(R.drawable.gradient_green);
            selectedColor = Color.parseColor("#B21AFF00");
            my_projects_lin.setBackgroundResource(R.drawable.green_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityProject.this,R.color.green));
            plus.setBackgroundResource(R.drawable.green_add);
        }
        else if(score < 1000){
            bguser.setBackgroundResource(R.drawable.gradient_brown);
            selectedColor = Color.parseColor("#FFCC7722");
            my_projects_lin.setBackgroundResource(R.drawable.brown_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityProject.this,R.color.brown));
            plus.setBackgroundResource(R.drawable.brown_add);
        }
        else if(score < 2500){
            bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            selectedColor = Color.parseColor("#B2B5B5B5");
            my_projects_lin.setBackgroundResource(R.drawable.light_gray_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityProject.this,R.color.light_gray));
            plus.setBackgroundResource(R.drawable.light_gray_add);
        }
        else if(score < 7000){
            bguser.setBackgroundResource(R.drawable.gradient_ohra);
            selectedColor = Color.parseColor("#FFE8AA0E");
            my_projects_lin.setBackgroundResource(R.drawable.ohra_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityProject.this,R.color.ohra));
            plus.setBackgroundResource(R.drawable.ohra_add);        }
        else if(score < 17000){
            bguser.setBackgroundResource(R.drawable.gradient_red);
            selectedColor = Color.parseColor("#FF0000");
            my_projects_lin.setBackgroundResource(R.drawable.red_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityProject.this,R.color.red));
            plus.setBackgroundResource(R.drawable.red_add);
        }
        else if(score < 30000){
            bguser.setBackgroundResource(R.drawable.gradient_orange);
            selectedColor = Color.parseColor("#FFCC7722");
            my_projects_lin.setBackgroundResource(R.drawable.orange_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityProject.this,R.color.orange));
            plus.setBackgroundResource(R.drawable.brown_add);
        }
        else if(score < 50000){
            bguser.setBackgroundResource(R.drawable.gradient_violete);
            selectedColor = Color.parseColor("#4F0070");
            my_projects_lin.setBackgroundResource(R.drawable.violete_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityProject.this,R.color.violete));
            plus.setBackgroundResource(R.drawable.violete_add);
        }
        else{
            bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            selectedColor = Color.parseColor("#FF00C6A2");
            my_projects_lin.setBackgroundResource(R.drawable.blue_green_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityProject.this,R.color.main_green));
            plus.setBackgroundResource(R.drawable.blue_green_add);
        }

        endProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error();
            }
        });

        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProject.this, Profile.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });
        forumMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error();
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProject.this, CreateProject.class);
                startActivity(intent);
            }
        });
        notif.setOnClickListener(new View.OnClickListener() {
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
    public void changeActivity(String id){
        Intent intent = new Intent(ActivityProject.this, UsersProject.class);
        intent.putExtra("projectId", id);
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
}