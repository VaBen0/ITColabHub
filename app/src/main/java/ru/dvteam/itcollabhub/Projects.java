package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Projects extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_projects, container, false);

        /*LinearLayout layout = vw.findViewById(R.id.lin_lay);
        Profile profile = (Profile) getActivity();
        assert profile != null;
        String mail = profile.getMail();
        //Toast.makeText(v.getContext(), mail, Toast.LENGTH_SHORT).show();
        PostDatas post = new PostDatas();
        post.postDataGetUserProjects1("GetUserProject2", mail, new CallBackInt() {
            @Override
            public void invoke(String info) {
                String[] inf = info.split(";");

                if(!inf[0].equals("Нет1проектов564")) {
                    layout.removeAllViews();
                    String[] names = inf[0].split(",");
                    String[] photo = inf[1].split(",");
                    String userName = inf[4];
                    String userImg = inf[5];
                    String userScore = inf[6];

                    for (int i = 0; i < names.length; i++) {
                        View custom1 = inflater.inflate(R.layout.project_window2, null);
                        TextView nameu1 = (TextView) custom1.findViewById(R.id.textView3);
                        ImageView loadImage1 = (ImageView) custom1.findViewById(R.id.loadImg);
                        ImageView user1 = (ImageView) custom1.findViewById(R.id.userImage);
                        ImageView userCircle1 = (ImageView) custom1.findViewById(R.id.user_circle);
                        TextView nameOfUser1 = (TextView) custom1.findViewById(R.id.textView13);

                        nameOfUser1.setText(userName);


                        Glide
                                .with(Projects.this)
                                .load(userImg)
                                .into(user1);


                        if(Integer.parseInt(userScore) < 100){
                            userCircle1.setBackgroundResource(R.drawable.circle_blue2);
                        }
                        else if(Integer.parseInt(userScore) < 300){
                            userCircle1.setBackgroundResource(R.drawable.circle_green2);
                        }
                        else if(Integer.parseInt(userScore) < 1000){
                            userCircle1.setBackgroundResource(R.drawable.circle_brown2);
                        }
                        else if(Integer.parseInt(userScore) < 2500){
                            userCircle1.setBackgroundResource(R.drawable.circle_light_gray2);
                        }
                        else if(Integer.parseInt(userScore) < 7000){
                            userCircle1.setBackgroundResource(R.drawable.circle_ohra2);
                        }
                        else if(Integer.parseInt(userScore) < 17000){
                            userCircle1.setBackgroundResource(R.drawable.circle_red2);
                        }
                        else if(Integer.parseInt(userScore) < 30000){
                            userCircle1.setBackgroundResource(R.drawable.circle_orange2);
                        }
                        else if(Integer.parseInt(userScore) < 50000){
                            userCircle1.setBackgroundResource(R.drawable.circle_violete2);
                        }
                        else{
                            userCircle1.setBackgroundResource(R.drawable.circle_blue_green2);
                        }

                        Glide
                                .with(Projects.this)
                                .load(photo[i])
                                .into(loadImage1);

                        nameu1.setText(names[i]);
                        layout.addView(custom1);
                    }
                    View empty = inflater.inflate(R.layout.emty_obj, null);
                    layout.addView(empty);
                }
                else{
                    Toast.makeText(vw.getContext(), "Результаты не найдены", Toast.LENGTH_SHORT).show();;
                }
            }
        });*/

        return vw;
    }
}