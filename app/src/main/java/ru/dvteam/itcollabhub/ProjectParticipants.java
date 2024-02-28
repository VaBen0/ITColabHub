package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityProjectParticipantsBinding;

public class ProjectParticipants extends AppCompatActivity {

    ActivityProjectParticipantsBinding binding;
    String id, title, description, prPhoto, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProjectParticipantsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        description = arguments.getString("projectDescription");

        binding.projectName.setText(title);
        Glide
                .with(ProjectParticipants.this)
                .load(prPhoto)
                .into(binding.prLogo);

        if(score < 100){
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.blue));
        }
        else if(score < 300){
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.green));
        }
        else if(score < 1000){
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.brown));
        }
        else if(score < 2500){
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.light_gray));
        }
        else if(score < 7000){
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.ohra));
        }
        else if(score < 17000){
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.red));
        }
        else if(score < 30000){
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.orange));
        }
        else if(score < 50000){
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.violete));
        }
        else{
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.main_green));
        }
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectParticipants.this, AddParticipant.class);
                startActivity(intent);
            }
        });

        /*PostDatas post = new PostDatas();
        post.postDataGetFriends("GetUserFriends", mail, new CallBackInt() {
            @Override
            public void invoke(String info) {
                String[] inf = info.split(";");

                if(!inf[0].equals("Нет1друзей564")) {
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
                        ImageView messege = (ImageView) custom.findViewById(R.id.notban);
                        messege.setBackgroundResource(R.drawable.message);

                        Glide
                                .with(ParticipantEditProject.this)
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
                                Toast.makeText(getActivity(), "НЕ ПОМЕНЯЛ ЕЩЁ!!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        main.addView(custom);
                    }
                    View empty = inflater.inflate(R.layout.emty_obj, null);
                    main.addView(empty);
                }
                else{
                    Toast.makeText(v.getContext(), "У вас нет друзей", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }
}