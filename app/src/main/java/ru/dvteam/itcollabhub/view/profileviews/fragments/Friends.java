package ru.dvteam.itcollabhub.view.profileviews.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.FriendProfile;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.FragmentFriendsBinding;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.viewmodel.profileviewmodels.ProfileViewModel;

public class Friends extends Fragment {

    FragmentFriendsBinding binding;

    ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendsBinding.inflate(inflater, container, false);

        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        profileViewModel.getUserAllInfo().observe(getViewLifecycleOwner(), profileInformation -> {
            if(profileInformation.getUserRfr().equals("0")){binding.notification.setBackgroundResource(R.drawable.notification_false);}
            else{binding.notification.setBackgroundResource(R.drawable.red_notify);}

            PostDatas post = new PostDatas();
            post.postDataGetFriends("GetUserFriends", profileInformation.getUserMail(), new CallBackInt() {
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
                            View custom = inflater.inflate(R.layout.friend_window, null);
                            TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                            ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                            ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                            TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                            ImageView messege = (ImageView) custom.findViewById(R.id.notban);
                            messege.setBackgroundResource(R.drawable.message);

                            Glide
                                    .with(Friends.this)
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
                            custom.setOnClickListener(new View.OnClickListener() {
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
                                    Profile profile = (Profile) getActivity();
                                    assert profile != null;
                                    profile.error();
                                }
                            });
                            binding.linMain.addView(custom);
                        }
                        View empty = inflater.inflate(R.layout.emty_obj, null);
                        binding.linMain.addView(empty);
                    }
                    else{
                        Toast.makeText(getContext(), "У вас нет друзей", Toast.LENGTH_SHORT).show();
                    }
                }
            });



            binding.notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Profile profile = (Profile) getActivity();
                    assert profile != null;
                    profile.changeActivity();
                }
            });

            binding.find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(binding.nameFriend.getText().toString().isEmpty()){
                        Toast.makeText(v.getContext(), "Результаты не найдены", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String UserName = binding.nameFriend.getText().toString();

                        PostDatas post = new PostDatas();
                        post.postDataGetFindFriend("GetUsers", UserName, profileInformation.getUserMail(), new CallBackInt() {
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
                                        View custom = inflater.inflate(R.layout.friend_window, null);
                                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                                        ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                                        ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                                        TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                                        ImageView messege = (ImageView) custom.findViewById(R.id.notban);
                                        messege.setBackgroundResource(R.drawable.ad);

                                        Glide
                                                .with(Friends.this)
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
                                                post.postDataAddFriend("SendRequestToAddFriend", profileInformation.getUserMail(), id[finalI], new CallBackInt() {
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
                                    View empty = inflater.inflate(R.layout.emty_obj, null);
                                    binding.linMain.addView(empty);
                                } else {
                                    Toast.makeText(v.getContext(), "Результаты не найдены", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        });

        return binding.getRoot();
    }
}