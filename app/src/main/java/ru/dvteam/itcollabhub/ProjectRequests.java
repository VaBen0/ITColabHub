package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
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
        setThemeActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_requests);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ProjectRequests.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        ImageView bguser = findViewById(R.id.bguser);


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
                        custom.setOnClickListener(new View.OnClickListener() {
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