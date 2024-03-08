package ru.dvteam.itcollabhub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.FragmentFriendAsPartisipantBinding;


public class FriendAsPartisipant extends Fragment {
    FragmentFriendAsPartisipantBinding binding;
    String mail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendAsPartisipantBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreateProject2 createProject2 = (CreateProject2) getActivity();
        mail = createProject2.getMail();
        int userScore = createProject2.getScore();
        String addedIdArr = createProject2.getPeoplesIds();

        PostDatas post = new PostDatas();
        post.postDataGetFriends("GetUserFriends", mail, new CallBackInt() {
            @Override
            public void invoke(String info) {
                String[] inf = info.split(";");

                if(!inf[0].equals("Нет1друзей564")) {
                    binding.linMain.removeAllViews();
                    String[] names = inf[0].split(",");
                    String[] photo = inf[1].split(",");
                    String[] id = inf[2].split(",");
                    String[] score = inf[3].split(",");
                    String[] project = inf[4].split(",");

                    for (int i = 0; i < names.length; i++) {
                        if (!addedIdArr.contains(id[i])) {
                            View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
                            TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                            ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                            ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                            TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                            ImageView messege = (ImageView) custom.findViewById(R.id.notban);
                            setButtonColor(userScore, messege);

                            Glide
                                    .with(FriendAsPartisipant.this)
                                    .load(photo[i])
                                    .into(loadImage);
                            nameu.setText(names[i]);
                            project1.setVisibility(View.GONE);

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
                            messege.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CreateProject2 createProject = (CreateProject2) getActivity();
                                    createProject.setId(id[finalI]);
                                    binding.linMain.removeView(custom);
                                }
                            });
                            binding.linMain.addView(custom);
                        }
                    }
                    View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                    binding.linMain.addView(empty);
                }
                else{
                    Toast.makeText(getContext(), "У вас нет друзей", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setButtonColor(int score, ImageView but){
        if(score < 100){
            but.setImageResource(R.drawable.ad);
        }
        else if(score < 300){
            but.setImageResource(R.drawable.green_add);
        }
        else if(score < 1000){
            but.setImageResource(R.drawable.brown_add);
        }
        else if(score < 2500){
            but.setImageResource(R.drawable.light_gray_add);
        }
        else if(score < 7000){
            but.setImageResource(R.drawable.ohra_add);
        }
        else if(score < 17000){
            but.setImageResource(R.drawable.red_add);
        }
        else if(score < 30000) {
            but.setImageResource(R.drawable.brown_add);
        }
        else if(score < 50000){
            but.setImageResource(R.drawable.violete_add);
        }
        else{
            but.setImageResource(R.drawable.blue_green_add);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}