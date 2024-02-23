package ru.dvteam.itcollabhub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

public class GetFriend extends AppCompatActivity {

    private int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.activity_get_friend);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String name = sPref.getString("UserName", "");
        String mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);
        String urlImage = sPref.getString("UrlImg", "");


        String s = "Ваши очки: " + score;
        TextView nameu = findViewById(R.id.nameu);
        ImageView loadedImage = findViewById(R.id.loadImg);
        ImageView userCircle = findViewById(R.id.userCircle);
        ImageView bguser = findViewById(R.id.bguser);
        TextView rating = findViewById(R.id.rating);
        TextView UserScore = findViewById(R.id.score);
        LinearLayout main = findViewById(R.id.lin_lay);
        ImageView restartLine = findViewById(R.id.restart);
        View projects_lin = findViewById(R.id.linear_rating);
        UserScore.setText(s);

        Glide
                .with(GetFriend.this)
                .load(urlImage)
                .into(loadedImage);
        nameu.setText(name);

        if(score < 100){
            bguser.setBackgroundResource(R.drawable.gradient_blue);
            userCircle.setBackgroundResource(R.drawable.circle_blue);
            UserScore.setTextColor(Color.parseColor("#B20000FF"));
            selectedColor = Color.parseColor("#B20000FF");
            projects_lin.setBackgroundResource(R.drawable.blue_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        }
        else if(score < 300){
            bguser.setBackgroundResource(R.drawable.gradient_green);
            userCircle.setBackgroundResource(R.drawable.circle_green);
            UserScore.setTextColor(Color.parseColor("#B21AFF00"));
            selectedColor = Color.parseColor("#B21AFF00");
            projects_lin.setBackgroundResource(R.drawable.green_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        }
        else if(score < 1000){
            bguser.setBackgroundResource(R.drawable.gradient_brown);
            userCircle.setBackgroundResource(R.drawable.circle_brown);
            UserScore.setTextColor(Color.parseColor("#FFCC7722"));
            selectedColor = Color.parseColor("#FFCC7722");
            projects_lin.setBackgroundResource(R.drawable.brown_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.brown));
        }
        else if(score < 2500){
            bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            userCircle.setBackgroundResource(R.drawable.circle_light_gray);
            UserScore.setTextColor(Color.parseColor("#B2B5B5B5"));
            selectedColor = Color.parseColor("#B2B5B5B5");
            projects_lin.setBackgroundResource(R.drawable.light_gray_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.light_gray));
        }
        else if(score < 7000){
            bguser.setBackgroundResource(R.drawable.gradient_ohra);
            userCircle.setBackgroundResource(R.drawable.circle_ohra);
            UserScore.setTextColor(Color.parseColor("#FFE8AA0E"));
            selectedColor = Color.parseColor("#FFE8AA0E");
            projects_lin.setBackgroundResource(R.drawable.ohra_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ohra));
        }
        else if(score < 17000){
            bguser.setBackgroundResource(R.drawable.gradient_red);
            userCircle.setBackgroundResource(R.drawable.circle_red);
            UserScore.setTextColor(Color.parseColor("#FF0000"));
            selectedColor = Color.parseColor("#FF0000");
            projects_lin.setBackgroundResource(R.drawable.red_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.red));
        }
        else if(score < 30000){
            bguser.setBackgroundResource(R.drawable.gradient_orange);
            userCircle.setBackgroundResource(R.drawable.circle_orange);
            UserScore.setTextColor(Color.parseColor("#FFCC7722"));
            selectedColor = Color.parseColor("#FFCC7722");
            projects_lin.setBackgroundResource(R.drawable.orange_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.orange));
        }
        else if(score < 50000){
            bguser.setBackgroundResource(R.drawable.gradient_violete);
            userCircle.setBackgroundResource(R.drawable.circle_violete);
            UserScore.setTextColor(Color.parseColor("#4F0070"));
            selectedColor = Color.parseColor("#4F0070");
            projects_lin.setBackgroundResource(R.drawable.violete_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.violete));
        }
        else{
            bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            userCircle.setBackgroundResource(R.drawable.circle_blue_green);
            UserScore.setTextColor(Color.parseColor("#FF00C6A2"));
            selectedColor = Color.parseColor("#FF00C6A2");
            projects_lin.setBackgroundResource(R.drawable.blue_green_line);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.main_green));
        }

        PostDatas post = new PostDatas();
        post.postDataGetFriends("GetUserFriendsR", mail, new CallBackInt() {
            @Override
            public void invoke(String info) {
                String[] inf = info.split(";");

                if(!inf[0].equals("Нет1друзей564")) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    main.removeAllViews();
                    String[] names = inf[0].split(",");
                    String[] photo = inf[1].split(",");
                    String[] id = inf[2].split(",");
                    String[] score = inf[3].split(",");
                    String[] project = inf[4].split(",");

                    for (int i = 0; i < names.length; i++) {
                        View custom = inflater.inflate(R.layout.friend_window, null);
                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                        ImageView loadImage = (ImageView) custom.findViewById(R.id.loadImg);
                        ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                        TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                        ImageView plus = (ImageView) custom.findViewById(R.id.notban);
                        plus.setBackgroundResource(R.drawable.ad);

                        Glide
                                .with(GetFriend.this)
                                .load(photo[i])
                                .into(loadImage);
                        nameu.setText(names[i]);
                        project1.setText(project[i]);

                        if(Integer.parseInt(score[i]) < 100){
                            userCircle.setBackgroundResource(R.drawable.circle_blue2);
                        }
                        else if(Integer.parseInt(score[i]) < 300){
                            userCircle.setBackgroundResource(R.drawable.circle_green2);
                        }
                        else if(Integer.parseInt(score[i]) < 1000){
                            userCircle.setBackgroundResource(R.drawable.circle_brown2);
                        }
                        else if(Integer.parseInt(score[i]) < 2500){
                            userCircle.setBackgroundResource(R.drawable.circle_light_gray2);
                        }
                        else if(Integer.parseInt(score[i]) < 7000){
                            userCircle.setBackgroundResource(R.drawable.circle_ohra2);
                        }
                        else if(Integer.parseInt(score[i]) < 17000){
                            userCircle.setBackgroundResource(R.drawable.circle_red2);
                        }
                        else if(Integer.parseInt(score[i]) < 30000){
                            userCircle.setBackgroundResource(R.drawable.circle_orange2);
                        }
                        else if(Integer.parseInt(score[i]) < 50000){
                            userCircle.setBackgroundResource(R.drawable.circle_violete2);
                        }
                        else{
                            userCircle.setBackgroundResource(R.drawable.circle_blue_green2);
                        }

                        custom.setId(Integer.parseInt(id[i]));
                        int finalI = i;
                        loadImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(GetFriend.this, FriendProfile.class);
                                intent.putExtra("id", id[finalI]);
                                intent.putExtra("name", names[finalI]);
                                intent.putExtra("score", score[finalI]);
                                intent.putExtra("image_url", photo[finalI]);
                                intent.putExtra("project", project[finalI]);
                                startActivity(intent);
                            }
                        });
                        nameu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(GetFriend.this, FriendProfile.class);
                                intent.putExtra("id", id[finalI]);
                                intent.putExtra("name", names[finalI]);
                                intent.putExtra("score", score[finalI]);
                                intent.putExtra("image_url", photo[finalI]);
                                intent.putExtra("project", project[finalI]);
                                startActivity(intent);
                            }
                        });
                        plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                post.postDataAddFriend("AddFriend", mail, id[finalI], new CallBackInt() {
                                    @Override
                                    public void invoke(String res) {
                                        Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();
                                        if(res.equals("Друг добавлен")){
                                            main.removeView(custom);
                                        }
                                    }
                                });
                            }
                        });
                        main.addView(custom);
                    }
                    View empty = inflater.inflate(R.layout.emty_obj, null);
                    main.addView(empty);
                }
                else{
                    Toast.makeText(GetFriend.this, "У вас нет запросов", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}