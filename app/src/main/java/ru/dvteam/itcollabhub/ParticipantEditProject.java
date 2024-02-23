package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ParticipantEditProject extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_participant_edit_project, container, false);

        Button add = v.findViewById(R.id.textView2);
        LinearLayout main = v.findViewById(R.id.lin_main);

        EditProject editProject = (EditProject) getActivity();
        assert editProject != null;
        int score = editProject.getScore();
        String mail = editProject.getMail();

        if(score < 100){
            add.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.blue));
        }
        else if(score < 300){
            add.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.green));
        }
        else if(score < 1000){
            add.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.brown));
        }
        else if(score < 2500){
            add.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.light_gray));
        }
        else if(score < 7000){
            add.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.ohra));
        }
        else if(score < 17000){
            add.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.red));
        }
        else if(score < 30000){
            add.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.orange));
        }
        else if(score < 50000){
            add.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.violete));
        }
        else{
            add.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.main_green));
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddParticipant.class);
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
        return v;
    }
}