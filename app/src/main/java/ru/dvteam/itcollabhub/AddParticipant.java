package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.databinding.ActivityAddParticipantBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class AddParticipant extends AppCompatActivity {

    ActivityAddParticipantBinding binding;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityAddParticipantBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(AddParticipant.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        binding.find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.nameFriend.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Результаты не найдены", Toast.LENGTH_SHORT).show();
                }
                else {
                    String UserName = binding.nameFriend.getText().toString();

                    PostDatas post = new PostDatas();
                    post.postDataGetFindFriend("GetUsers", UserName, mail, new CallBackInt() {
                        @Override
                        public void invoke(String info) {
                            String[] inf = info.split(";");

                            if (!inf[0].equals("Нет1друзей564")) {
                                binding.linMain.removeAllViews();
                                String[] names = inf[0].split(",");
                                String[] photo = inf[1].split(",");
                                String[] id = inf[2].split(",");
                                String[] score = inf[3].split(",");
                                String[] project = inf[4].split(",");

                                for (int i = 0; i < names.length; i++) {
                                    View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
                                    TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                                    ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                                    ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                                    TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                                    ImageView messege = (ImageView) custom.findViewById(R.id.notban);
                                    messege.setBackgroundResource(R.drawable.ad);

                                    Glide
                                            .with(AddParticipant.this)
                                            .load(photo[i])
                                            .into(loadImage);
                                    nameu.setText(names[i]);
                                    project1.setText(project[i]);

                                    if (Integer.parseInt(score[i]) < 100) {
                                        userCircle.setBackgroundResource(R.drawable.circle_blue2);
                                    } else if (Integer.parseInt(score[i]) < 300) {
                                        userCircle.setBackgroundResource(R.drawable.circle_green2);
                                    } else if (Integer.parseInt(score[i]) < 1000) {
                                        userCircle.setBackgroundResource(R.drawable.circle_brown2);
                                    } else if (Integer.parseInt(score[i]) < 2500) {
                                        userCircle.setBackgroundResource(R.drawable.circle_light_gray2);
                                    } else if (Integer.parseInt(score[i]) < 7000) {
                                        userCircle.setBackgroundResource(R.drawable.circle_ohra2);
                                    } else if (Integer.parseInt(score[i]) < 17000) {
                                        userCircle.setBackgroundResource(R.drawable.circle_red2);
                                    } else if (Integer.parseInt(score[i]) < 30000) {
                                        userCircle.setBackgroundResource(R.drawable.circle_orange2);
                                    } else if (Integer.parseInt(score[i]) < 50000) {
                                        userCircle.setBackgroundResource(R.drawable.circle_violete2);
                                    } else {
                                        userCircle.setBackgroundResource(R.drawable.circle_blue_green2);
                                    }


                                    int finalI = i;
                                    loadImage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(v.getContext(), FriendProfile.class);
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
                                            Intent intent = new Intent(v.getContext(), FriendProfile.class);
                                            intent.putExtra("id", id[finalI]);
                                            intent.putExtra("name", names[finalI]);
                                            intent.putExtra("score", score[finalI]);
                                            intent.putExtra("image_url", photo[finalI]);
                                            intent.putExtra("project", project[finalI]);
                                            startActivity(intent);
                                        }
                                    });
                                    messege.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            post.postDataAddFriend("SendRequestToAddFriend", mail, id[finalI], new CallBackInt() {
                                                @Override
                                                public void invoke(String res) {
                                                    Toast.makeText(v.getContext(), res, Toast.LENGTH_SHORT).show();
                                                    binding.linMain.removeView(custom);
                                                }
                                            });
                                        }
                                    });
                                    binding.linMain.addView(custom);
                                }
                                View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                                binding.linMain.addView(empty);
                            } else {
                                Toast.makeText(v.getContext(), "Результаты не найдены", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(AddParticipant.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(AddParticipant.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(AddParticipant.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(AddParticipant.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(AddParticipant.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(AddParticipant.this,R.color.red));
        }
        else if(score < 30000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(AddParticipant.this,R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(AddParticipant.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(AddParticipant.this,R.color.main_green));
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