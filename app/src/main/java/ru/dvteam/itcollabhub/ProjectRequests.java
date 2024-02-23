package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ProjectRequests extends AppCompatActivity {

    int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_requests);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        ImageView bguser = findViewById(R.id.bguser);

        if(score < 100){
            bguser.setBackgroundResource(R.drawable.gradient_blue);
            selectedColor = Color.parseColor("#B20000FF");
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectRequests.this,R.color.blue));
        }
        else if(score < 300){
            bguser.setBackgroundResource(R.drawable.gradient_green);
            selectedColor = Color.parseColor("#B21AFF00");
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectRequests.this,R.color.green));
        }
        else if(score < 1000){
            bguser.setBackgroundResource(R.drawable.gradient_brown);
            selectedColor = Color.parseColor("#FFCC7722");
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectRequests.this,R.color.brown));
        }
        else if(score < 2500){
            bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            selectedColor = Color.parseColor("#B2B5B5B5");
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectRequests.this,R.color.light_gray));
        }
        else if(score < 7000){
            bguser.setBackgroundResource(R.drawable.gradient_ohra);
            selectedColor = Color.parseColor("#FFE8AA0E");
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectRequests.this,R.color.ohra));
        }
        else if(score < 17000){
            bguser.setBackgroundResource(R.drawable.gradient_red);
            selectedColor = Color.parseColor("#FF0000");
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectRequests.this,R.color.red));
        }
        else if(score < 30000){
            bguser.setBackgroundResource(R.drawable.gradient_orange);
            selectedColor = Color.parseColor("#FFCC7722");
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectRequests.this,R.color.orange));
        }
        else if(score < 50000){
            bguser.setBackgroundResource(R.drawable.gradient_violete);
            selectedColor = Color.parseColor("#4F0070");
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectRequests.this,R.color.violete));
        }
        else{
            bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            selectedColor = Color.parseColor("#FF00C6A2");
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectRequests.this,R.color.main_green));
        }

        LinearLayout main = findViewById(R.id.main);

        PostDatas post = new PostDatas();
        post.postDataGetProjectReq("GetRProjects", mail, new CallBackInt() {
            @Override
            public void invoke(String info) {
                String[] inf = info.split(";");

                //Toast.makeText(ProjectRequests.this, info, Toast.LENGTH_SHORT).show();

                if(!inf[0].equals("Нет1проектов564")) {
                    main.removeAllViews();
                    String[] names = inf[0].split(",");
                    String[] photo = inf[1].split(",");
                    String[] id = inf[2].split(",");

                    for (int i = 0; i < names.length; i++) {
                        View custom = getLayoutInflater().inflate(R.layout.project_request_window, null);
                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                        ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                        ImageView notban = (ImageView) custom.findViewById(R.id.notban);
                        ImageView ban = (ImageView) custom.findViewById(R.id.ban);

                        Glide
                                .with(ProjectRequests.this)
                                .load(photo[i])
                                .into(loadImage);
                        nameu.setText(names[i]);

                        int finalI = i;
                        loadImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ProjectRequests.this, UsersProject2.class);
                                intent.putExtra("projectId", id[finalI]);
                                startActivity(intent);
                            }
                        });
                        nameu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ProjectRequests.this, UsersProject2.class);
                                intent.putExtra("projectId", id[finalI]);
                                startActivity(intent);
                            }
                        });
                        notban.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                post.postDataAnswerProject("AddUserToProject", mail, id[finalI], new CallBackInt() {
                                    @Override
                                    public void invoke(String res) {
                                        main.removeView(custom);
                                    }
                                });
                            }
                        });
                        ban.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                post.postDataAnswerProject("NotAddUserToProject", mail, id[finalI], new CallBackInt() {
                                    @Override
                                    public void invoke(String res) {
                                        main.removeView(custom);
                                    }
                                });
                            }
                        });
                        main.addView(custom);
                    }
                    View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                    main.addView(empty);
                }
                else{
                    Toast.makeText(ProjectRequests.this, "Нет запросов", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}