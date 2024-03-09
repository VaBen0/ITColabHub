package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

public class Profile extends AppCompatActivity {
    View projects_lin;
    View rating_lin;
    View friends_lin;
    int selectedColor;
    NavController navController;
    private int max;
    private int score;
    private int min;
    private String status, mail;
    private boolean rFr;
    private int archivPr, ativePr;
    private final String[] wow = {"Хренос 2", "Кина не будет - электричество кончилось", "Ой, сломалось", "Караул!"};
    View back;
    ImageView dontWork;
    String urlPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String name = sPref.getString("UserName", "");
        mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);
        int themeNum = sPref.getInt("ThemeNum", 1);
        UsersChosenTheme.setThemeNum(themeNum);
        setThemeActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String s = "Ваши очки: " + score;
        ImageView userCircle = findViewById(R.id.userCircle);
        ImageView bguser = findViewById(R.id.bguser);
        TextView projects = findViewById(R.id.projects);
        TextView friends = findViewById(R.id.friends);
        TextView rating = findViewById(R.id.rating);
        TextView UserName = findViewById(R.id.nameu);
        TextView UserScore = findViewById(R.id.score);
        ImageView loadedImg = findViewById(R.id.log);
        ImageView restartLine = findViewById(R.id.restart);
        projects_lin = findViewById(R.id.linear_projects);
        rating_lin = findViewById(R.id.linear_rating);
        friends_lin = findViewById(R.id.linear_friends);
        View fragment = findViewById(R.id.nav_host_fragment);
        LinearLayout projectMenu = findViewById(R.id.project_menu);
        LinearLayout profileMenu = findViewById(R.id.profile_menu);
        LinearLayout forumMenu = findViewById(R.id.forum_menu);
        ImageView editProfile = findViewById(R.id.notifications);
        Button theme = findViewById(R.id.themes);
        back = findViewById(R.id.view3);
        dontWork = findViewById(R.id.imageView12);

        UserName.setText(name);
        UserScore.setText(s);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(Profile.this, typedValue.resourceId);

        getWindow().setStatusBarColor(color);
        projects_lin.setVisibility(View.INVISIBLE);
        friends_lin.setVisibility(View.INVISIBLE);
        rating_lin.setVisibility(View.VISIBLE);

        /*if(score < 100){
            bguser.setBackgroundResource(R.drawable.gradient_blue);
            userCircle.setBackgroundResource(R.drawable.circle_blue);
            UserScore.setTextColor(Color.parseColor("#B20000FF"));
            selectedColor = Color.parseColor("#B20000FF");
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.blue));
        }
        else if(score < 300){
            bguser.setBackgroundResource(R.drawable.gradient_green);
            userCircle.setBackgroundResource(R.drawable.circle_green);
            UserScore.setTextColor(Color.parseColor("#B21AFF00"));
            selectedColor = Color.parseColor("#B21AFF00");
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.green));
        }
        else if(score < 1000){
            bguser.setBackgroundResource(R.drawable.gradient_brown);
            userCircle.setBackgroundResource(R.drawable.circle_brown);
            UserScore.setTextColor(Color.parseColor("#FFCC7722"));
            selectedColor = Color.parseColor("#FFCC7722");
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.brown));
        }
        else if(score < 2500){
            bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            userCircle.setBackgroundResource(R.drawable.circle_light_gray);
            UserScore.setTextColor(Color.parseColor("#B2B5B5B5"));
            selectedColor = Color.parseColor("#B2B5B5B5");
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.light_gray));
        }
        else if(score < 7000){
            bguser.setBackgroundResource(R.drawable.gradient_ohra);
            userCircle.setBackgroundResource(R.drawable.circle_ohra);
            UserScore.setTextColor(Color.parseColor("#FFE8AA0E"));
            selectedColor = Color.parseColor("#FFE8AA0E");
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.ohra));
        }
        else if(score < 17000){
            bguser.setBackgroundResource(R.drawable.gradient_red);
            userCircle.setBackgroundResource(R.drawable.circle_red);
            UserScore.setTextColor(Color.parseColor("#FF0000"));
            selectedColor = Color.parseColor("#FF0000");
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.red));
        }
        else if(score < 30000){
            bguser.setBackgroundResource(R.drawable.gradient_orange);
            userCircle.setBackgroundResource(R.drawable.circle_orange);
            UserScore.setTextColor(Color.parseColor("#FFCC7722"));
            selectedColor = Color.parseColor("#FFCC7722");
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.orange));
        }
        else if(score < 50000){
            bguser.setBackgroundResource(R.drawable.gradient_violete);
            userCircle.setBackgroundResource(R.drawable.circle_violete);
            UserScore.setTextColor(Color.parseColor("#4F0070"));
            selectedColor = Color.parseColor("#4F0070");
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.violete));
        }
        else{
            bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            userCircle.setBackgroundResource(R.drawable.circle_blue_green);
            UserScore.setTextColor(Color.parseColor("#FF00C6A2"));
            selectedColor = Color.parseColor("#FF00C6A2");
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.main_green));
        }*/

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        PostDatas post = new PostDatas();
        post.postDataGetUserData(mail, new CallBackInt2() {
            @Override
            public void invoke(String name, String urlImage, int topScore, String topStatus, String rfr, int activityProjects, int archiveProjects) {
                String s = "Ваши очки: " + topScore;
                score = topScore;
                status = topStatus;
                ativePr = activityProjects;
                archivPr = archiveProjects;
                urlPhoto = urlImage;
                UserName.setText(name);
                UserScore.setText(s);
                Glide
                        .with(Profile.this)
                        .load(urlImage)
                        .into(loadedImg);


                navController.navigate(R.id.rating);

                if(rfr.equals("0")){rFr = false;}
                else{rFr = true;}

                /*if(score < 100){
                    bguser.setBackgroundResource(R.drawable.gradient_blue);
                    userCircle.setBackgroundResource(R.drawable.circle_blue);
                    UserScore.setTextColor(Color.parseColor("#B20000FF"));
                    selectedColor = Color.parseColor("#B20000FF");
                    rating_lin.setBackgroundResource(R.drawable.blue_line);
                    getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.blue));
                }
                else if(score < 300){
                    bguser.setBackgroundResource(R.drawable.gradient_green);
                    userCircle.setBackgroundResource(R.drawable.circle_green);
                    UserScore.setTextColor(Color.parseColor("#B21AFF00"));
                    selectedColor = Color.parseColor("#B21AFF00");
                    rating_lin.setBackgroundResource(R.drawable.green_line);
                    getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.green));
                }
                else if(score < 1000){
                    bguser.setBackgroundResource(R.drawable.gradient_brown);
                    userCircle.setBackgroundResource(R.drawable.circle_brown);
                    UserScore.setTextColor(Color.parseColor("#FFCC7722"));
                    selectedColor = Color.parseColor("#FFCC7722");
                    rating_lin.setBackgroundResource(R.drawable.brown_line);
                    getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.brown));
                }
                else if(score < 2500){
                    bguser.setBackgroundResource(R.drawable.gradient_light_gray);
                    userCircle.setBackgroundResource(R.drawable.circle_light_gray);
                    UserScore.setTextColor(Color.parseColor("#B2B5B5B5"));
                    selectedColor = Color.parseColor("#B2B5B5B5");
                    rating_lin.setBackgroundResource(R.drawable.light_gray_line);
                    getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.light_gray));
                }
                else if(score < 7000){
                    bguser.setBackgroundResource(R.drawable.gradient_ohra);
                    userCircle.setBackgroundResource(R.drawable.circle_ohra);
                    UserScore.setTextColor(Color.parseColor("#FFE8AA0E"));
                    selectedColor = Color.parseColor("#FFE8AA0E");
                    rating_lin.setBackgroundResource(R.drawable.ohra_line);
                    getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.ohra));
                }
                else if(score < 17000){
                    bguser.setBackgroundResource(R.drawable.gradient_red);
                    userCircle.setBackgroundResource(R.drawable.circle_red);
                    UserScore.setTextColor(Color.parseColor("#FF0000"));
                    selectedColor = Color.parseColor("#FF0000");
                    rating_lin.setBackgroundResource(R.drawable.red_line);
                    getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.red));
                }
                else if(score < 30000){
                    bguser.setBackgroundResource(R.drawable.gradient_orange);
                    userCircle.setBackgroundResource(R.drawable.circle_orange);
                    UserScore.setTextColor(Color.parseColor("#FFCC7722"));
                    selectedColor = Color.parseColor("#FFCC7722");
                    rating_lin.setBackgroundResource(R.drawable.orange_line);
                    getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.orange));
                }
                else if(score < 50000){
                    bguser.setBackgroundResource(R.drawable.gradient_violete);
                    userCircle.setBackgroundResource(R.drawable.circle_violete);
                    UserScore.setTextColor(Color.parseColor("#9000CC"));
                    selectedColor = Color.parseColor("#4F0070");
                    rating_lin.setBackgroundResource(R.drawable.violete_line);
                    getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.violete));
                }
                else{
                    bguser.setBackgroundResource(R.drawable.gradient_blue_green);
                    userCircle.setBackgroundResource(R.drawable.circle_blue_green);
                    UserScore.setTextColor(Color.parseColor("#FF00C6A2"));
                    selectedColor = Color.parseColor("#FF00C6A2");
                    rating_lin.setBackgroundResource(R.drawable.blue_green_line);
                    getWindow().setStatusBarColor(ContextCompat.getColor(Profile.this,R.color.main_green));
                }*/

                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("UserName", name);
                ed.putInt("UserScore", topScore);
                ed.putString("UrlImg", urlImage);
                ed.apply();
            }
        });



        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //error();
//                friends_lin.setBackgroundColor(0);
//                rating_lin.setBackgroundColor(0);
//                projects_lin.setBackgroundResource(R.drawable.blue_line);
                //navController.navigate(R.id.projects);
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projects_lin.setVisibility(View.INVISIBLE);
                rating_lin.setVisibility(View.INVISIBLE);
                friends_lin.setVisibility(View.VISIBLE);

                Bundle bundle = new Bundle();
                bundle.putString("mail", mail);
                bundle.putBoolean("rFr", rFr);

                navController.navigate(R.id.friends, bundle);
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projects_lin.setVisibility(View.INVISIBLE);
                friends_lin.setVisibility(View.INVISIBLE);
                rating_lin.setVisibility(View.VISIBLE);

                Bundle bundle = new Bundle();
                bundle.putInt("score", score);
                bundle.putString("status", status);

                navController.navigate(R.id.rating, bundle);

            }
        });

        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ChooseThemeForApp.class);
                intent.putExtra("UserPhoto", urlPhoto);
                startActivity(intent);
            }
        });

        restartLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });
        projectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ActivityProject.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });
        forumMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Forum.class);
                startActivity(intent);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });
    }

    public String getMail(){return mail;}
    public void changeActivity(){
        Intent intent = new Intent(Profile.this, GetFriend.class);
        startActivity(intent);
    }
    public int getScore(){return score;}
    public int getActiveProjects(){return ativePr;}
    public int getArchiveProjects(){return archivPr;}

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
        startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        finish();
    }
}