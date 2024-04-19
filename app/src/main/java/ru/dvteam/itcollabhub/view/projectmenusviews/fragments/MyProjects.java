package ru.dvteam.itcollabhub.view.projectmenusviews.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import ru.dvteam.itcollabhub.databinding.FragmentMyProjectsBinding;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.view.adapter.ActivityProjectAdapter;
import ru.dvteam.itcollabhub.callbackclasses.CallBackActivityProject;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo.UsersProjectDemo;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.UsersProject;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ActivityProjectViewModel;

public class MyProjects extends Fragment {

    FragmentMyProjectsBinding binding;
    ActivityProjectAdapter activityProjectAdapter;
    ActivityProjectViewModel activityProjectViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyProjectsBinding.inflate(inflater, container, false);
        activityProjectViewModel = new ViewModelProvider(requireActivity()).get(ActivityProjectViewModel.class);

        binding.activityProjects.setLayoutManager(new LinearLayoutManager(getContext()));

        activityProjectViewModel.getActivityProjects().observe(requireActivity(), arrayList -> {
            activityProjectAdapter = new ActivityProjectAdapter(getContext(), arrayList, new CallBackActivityProject() {
                @Override
                public void setActivity(String id) {
                    if(id.equals("Demo")){
                        Intent intent = new Intent(getContext(), UsersProjectDemo.class);
                        startActivity(intent);
                    }else {
                        activityProjectViewModel.setProjectId(id);
                        GlobalProjectInformation.getInstance().setEnd(false);
                        Intent intent = new Intent(getContext(), UsersProject.class);
                        startActivity(intent);
                    }
                }
            });

            binding.activityProjects.setAdapter(activityProjectAdapter);
        });

        return binding.getRoot();
    }
//    public void createProjects(){
//        System.out.println("my");
//        binding.mainLayout.removeAllViews();
//        PostDatas post = new PostDatas();
//        post.postDataGetUserProjects("GetUserProject", "catvano4551@gmail.com", new CallBackInt() {
//            @Override
//            public void invoke(String info) {
//                //Toast.makeText(activityProject, info, Toast.LENGTH_SHORT).show();
//                System.out.println(info);
//                String[] inf = info.split(";");
//                ActivityProject activityProject = (ActivityProject) getActivity();
//
//                if(!inf[0].equals("Нет1проектов564")) {
//                    binding.mainLayout.removeAllViews();
//                    String[] names = inf[0].split(",");
//                    String[] photo = inf[1].split(",");
//                    String[] id = inf[2].split(",");
//                    String[] userName = inf[4].split(",");
//                    String[] userImg = inf[5].split(",");
//                    String[] userScore = inf[6].split(",");
//                    String[] percents = inf[7].split(",");
//
//                    for (int i = 0; i < names.length; i++) {
//                        View custom = getLayoutInflater().inflate(R.layout.project_window, null);
//                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
//                        ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
//                        ImageView user = (ImageView) custom.findViewById(R.id.logo);
//                        ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
//                        TextView nameOfUser = (TextView) custom.findViewById(R.id.textView13);
//                        TextView percent = custom.findViewById(R.id.textView16);
//                        ProgressBar lvl = custom.findViewById(R.id.lvl);
//
//                        lvl.setProgress(Integer.parseInt(percents[i]));
//                        percent.setText(percents[i] + ".0%");
//
//                        nameOfUser.setText(userName[i]);
//
//                        Glide
//                                .with(MyProjects.this)
//                                .load(userImg[i])
//                                .into(user);
//
//
//                        if(Integer.parseInt(userScore[i]) < 100){
//                            userCircle.setBackgroundResource(R.drawable.circle_blue2);
//                        }
//                        else if(Integer.parseInt(userScore[i]) < 300){
//                            userCircle.setBackgroundResource(R.drawable.circle_green2);
//                        }
//                        else if(Integer.parseInt(userScore[i]) < 1000){
//                            userCircle.setBackgroundResource(R.drawable.circle_brown2);
//                        }
//                        else if(Integer.parseInt(userScore[i]) < 2500){
//                            userCircle.setBackgroundResource(R.drawable.circle_light_gray2);
//                        }
//                        else if(Integer.parseInt(userScore[i]) < 7000){
//                            userCircle.setBackgroundResource(R.drawable.circle_ohra2);
//                        }
//                        else if(Integer.parseInt(userScore[i]) < 17000){
//                            userCircle.setBackgroundResource(R.drawable.circle_red2);
//                        }
//                        else if(Integer.parseInt(userScore[i]) < 30000){
//                            userCircle.setBackgroundResource(R.drawable.circle_orange2);
//                        }
//                        else if(Integer.parseInt(userScore[i]) < 50000){
//                            userCircle.setBackgroundResource(R.drawable.circle_violete2);
//                        }
//                        else{
//                            userCircle.setBackgroundResource(R.drawable.circle_blue_green2);
//                        }
//
//                        Glide
//                                .with(MyProjects.this)
//                                .load(photo[i])
//                                .into(loadImage);
//                        nameu.setText(names[i]);
//
//                        int finalI = i;
//                        custom.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                activityProject.changeActivity(id[finalI]);
//                            }
//                        });
//                        loadImage.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                activityProject.changeActivity(id[finalI]);
//                            }
//                        });
//                        binding.mainLayout.addView(custom, 0);
//                    }
//
//                    /*if(activityProject.getDemoProject() && !activityProject.getDemoEndProject()){
//                        String titleDemo = activityProject.getDemoTitle();
//                        String uri = activityProject.getDemoUri();
//                        Uri uriImage = Uri.parse(uri);
//                        View custom = getLayoutInflater().inflate(R.layout.project_window, null);
//                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
//                        ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
//                        ImageView user = (ImageView) custom.findViewById(R.id.logo);
//                        ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
//                        TextView nameOfUser = (TextView) custom.findViewById(R.id.textView13);
//                        TextView percent = custom.findViewById(R.id.textView16);
//                        ProgressBar lvl = custom.findViewById(R.id.lvl);
//
//                        nameOfUser.setText("Demo");
//
//                        nameu.setText(titleDemo);
//
//                        custom.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                activityProject.changeActivityDemo();
//                            }
//                        });
//                        loadImage.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                activityProject.changeActivityDemo();
//                            }
//                        });
//                        main.addView(custom, 0);
//                    }*/
//
//                    View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
//                    binding.mainLayout.addView(empty);
//                }
//                else{
//                    Toast.makeText(getActivity(), "Результаты не найдены", Toast.LENGTH_SHORT).show();;
//                }
//            }
//        });
//    }

}