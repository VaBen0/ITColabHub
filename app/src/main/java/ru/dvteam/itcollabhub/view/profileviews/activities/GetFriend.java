package ru.dvteam.itcollabhub.view.profileviews.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.databinding.ActivityGetFriendBinding;
import ru.dvteam.itcollabhub.viewmodel.profileviewmodels.GetFriendViewModel;

public class GetFriend extends AppCompatActivity {

    ActivityGetFriendBinding binding;
    GetFriendViewModel getFriendViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        binding = ActivityGetFriendBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getFriendViewModel = new ViewModelProvider(this).get(GetFriendViewModel.class);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(GetFriend.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        getFriendViewModel.getInfo().observe(this, informationForChooseThemeForApp -> {
            int score = informationForChooseThemeForApp.getScore();
            String s = "Ваши очки: " + score;

            TextView nameu = findViewById(R.id.nameu);
            ImageView loadedImage = findViewById(R.id.loadImg);
            TextView UserScore = findViewById(R.id.score);
            LinearLayout main = findViewById(R.id.lin_lay);
            UserScore.setText(s);

            Glide
                    .with(GetFriend.this)
                    .load(informationForChooseThemeForApp.getUrlPhoto())
                    .into(loadedImage);
            nameu.setText(informationForChooseThemeForApp.getName());

            PostDatas post = new PostDatas();
            post.postDataGetFriends("GetUserFriendsR", getFriendViewModel.getMail(sPref), new CallBackInt() {
                @Override
                public void invoke(String info) {
                    String[] inf = info.split(";");
                    System.out.println(info);

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
                            ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
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
                                    post.postDataAddFriend("AddFriend", getFriendViewModel.getMail(sPref), id[finalI], new CallBackInt() {
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
        });
        getFriendViewModel.getAllInfo();

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