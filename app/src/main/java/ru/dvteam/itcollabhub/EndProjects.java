package ru.dvteam.itcollabhub;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class EndProjects extends Fragment {

    String mail;
    int score;
    LinearLayout main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_end_projects, container, false);

        main = v.findViewById(R.id.main_layout);
        ActivityProject activityProject = (ActivityProject) getActivity();
        assert activityProject != null;
        mail = activityProject.getMail();
        score = activityProject.getScore();
        createProjects();

        return v;
    }

    public void createProjects(){
        main.removeAllViews();
        PostDatas post = new PostDatas();
        post.postDataGetUserProjects("GetUserEndProject", mail, new CallBackInt() {
            @Override
            public void invoke(String info) {
                //Toast.makeText(activityProject, info, Toast.LENGTH_SHORT).show();
                System.out.println(info);
                String[] inf = info.split(";");
                ActivityProject activityProject = (ActivityProject) getActivity();

                if(!inf[0].equals("Нет1проектов564")) {
                    main.removeAllViews();
                    String[] names = inf[0].split(",");
                    String[] photo = inf[1].split(",");
                    String[] id = inf[2].split(",");
                    String[] userName = inf[4].split(",");
                    String[] userImg = inf[5].split(",");
                    String[] userScore = inf[6].split(",");
                    String[] percents = inf[7].split(",");

                    for (int i = 0; i < names.length; i++) {
                        View custom = getLayoutInflater().inflate(R.layout.project_window, null);
                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                        ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                        ImageView user = (ImageView) custom.findViewById(R.id.logo);
                        ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                        TextView nameOfUser = (TextView) custom.findViewById(R.id.textView13);
                        TextView percent = custom.findViewById(R.id.textView16);
                        ProgressBar lvl = custom.findViewById(R.id.lvl);

                        if(score < 100){
                            Drawable progressDrawable = getResources().getDrawable(R.drawable.circular_progress_bar_green2);
                            lvl.setBackgroundResource(R.drawable.progress_circle_blue2);
                            lvl.setProgressDrawable(progressDrawable);
                        }
                        else if(score < 300){
                            Drawable progressDrawable = getResources().getDrawable(R.drawable.circular_progress_bar_brown2);
                            lvl.setBackgroundResource(R.drawable.progress_circle_green2);
                            lvl.setProgressDrawable(progressDrawable);
                        }
                        else if(score < 1000){
                            Drawable progressDrawable = getResources().getDrawable(R.drawable.circular_progress_bar_light_gray2);
                            lvl.setBackgroundResource(R.drawable.progress_circle_brown2);
                            lvl.setProgressDrawable(progressDrawable);
                        }
                        else if(score < 2500){
                            Drawable progressDrawable = getResources().getDrawable(R.drawable.circular_progress_bar_ohra2);
                            lvl.setBackgroundResource(R.drawable.progress_circle_light_gray2);
                            lvl.setProgressDrawable(progressDrawable);
                        }
                        else if(score < 7000){
                            Drawable progressDrawable = getResources().getDrawable(R.drawable.circular_progress_bar_red2);
                            lvl.setBackgroundResource(R.drawable.progress_circle_ohra2);
                            lvl.setProgressDrawable(progressDrawable);
                        }
                        else if(score < 17000){
                            Drawable progressDrawable = getResources().getDrawable(R.drawable.circular_progress_bar_orange2);
                            lvl.setBackgroundResource(R.drawable.progress_circle_red2);
                            lvl.setProgressDrawable(progressDrawable);
                        }
                        else if(score < 30000){
                            Drawable progressDrawable = getResources().getDrawable(R.drawable.circular_progress_bar_violete2);
                            lvl.setBackgroundResource(R.drawable.progress_circle_orange2);
                            lvl.setProgressDrawable(progressDrawable);
                        }
                        else if(score < 50000){
                            Drawable progressDrawable = getResources().getDrawable(R.drawable.circular_progress_bar_blue_green2);
                            lvl.setBackgroundResource(R.drawable.progress_circle_violete2);
                            lvl.setProgressDrawable(progressDrawable);
                        }
                        else{
                            Drawable progressDrawable = getResources().getDrawable(R.drawable.circular_progress_bar_blue_green2);
                            lvl.setBackgroundResource(R.drawable.progress_circle_blue_green2);
                            lvl.setProgressDrawable(progressDrawable);
                        }

                        lvl.setProgress(Integer.parseInt(percents[i]));
                        percent.setText(percents[i] + ".0%");

                        nameOfUser.setText(userName[i]);

                        Glide
                                .with(EndProjects.this)
                                .load(userImg[i])
                                .into(user);


                        if(Integer.parseInt(userScore[i]) < 100){
                            userCircle.setBackgroundResource(R.drawable.circle_blue2);
                        }
                        else if(Integer.parseInt(userScore[i]) < 300){
                            userCircle.setBackgroundResource(R.drawable.circle_green2);
                        }
                        else if(Integer.parseInt(userScore[i]) < 1000){
                            userCircle.setBackgroundResource(R.drawable.circle_brown2);
                        }
                        else if(Integer.parseInt(userScore[i]) < 2500){
                            userCircle.setBackgroundResource(R.drawable.circle_light_gray2);
                        }
                        else if(Integer.parseInt(userScore[i]) < 7000){
                            userCircle.setBackgroundResource(R.drawable.circle_ohra2);
                        }
                        else if(Integer.parseInt(userScore[i]) < 17000){
                            userCircle.setBackgroundResource(R.drawable.circle_red2);
                        }
                        else if(Integer.parseInt(userScore[i]) < 30000){
                            userCircle.setBackgroundResource(R.drawable.circle_orange2);
                        }
                        else if(Integer.parseInt(userScore[i]) < 50000){
                            userCircle.setBackgroundResource(R.drawable.circle_violete2);
                        }
                        else{
                            userCircle.setBackgroundResource(R.drawable.circle_blue_green2);
                        }

                        Glide
                                .with(EndProjects.this)
                                .load(photo[i])
                                .into(loadImage);
                        nameu.setText(names[i]);

                        int finalI = i;
                        custom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activityProject.changeActivity(id[finalI]);
                            }
                        });
                        loadImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activityProject.changeActivity(id[finalI]);
                            }
                        });
                        main.addView(custom);
                    }
                    View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                    main.addView(empty);
                }
                else{
                    Toast.makeText(getActivity(), "Результаты не найдены", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }
}