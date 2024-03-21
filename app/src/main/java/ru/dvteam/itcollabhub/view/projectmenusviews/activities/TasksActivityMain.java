package ru.dvteam.itcollabhub.view.projectmenusviews.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.ActivityTaskMenuForLeader;
import ru.dvteam.itcollabhub.CreateDeadline;
import ru.dvteam.itcollabhub.CreateTask;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.UsersChosenTheme;
import ru.dvteam.itcollabhub.adapter.DeadlinesAdapter;
import ru.dvteam.itcollabhub.adapter.TasksAdapter;
import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.callbackclasses.CallBackDeadlineInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackTasksInfo;
import ru.dvteam.itcollabhub.classmodels.TasksClass;
import ru.dvteam.itcollabhub.databinding.ActivityTasksMainBinding;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.TasksMainViewModel;

public class TasksActivityMain extends AppCompatActivity {

    ActivityTasksMainBinding binding;
    TasksMainViewModel tasksMainViewModel;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityTasksMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        tasksMainViewModel = new ViewModelProvider(this).get(TasksMainViewModel.class);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(TasksActivityMain.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        if(!tasksMainViewModel.getIsl()){
            binding.add1.setVisibility(View.GONE);
            binding.add2.setVisibility(View.GONE);
        }

        binding.nameProject.setText(tasksMainViewModel.getProjectTitle());

        Glide
                .with(TasksActivityMain.this)
                .load(tasksMainViewModel.getProjectLog())
                .into(binding.prLogo);

        tasksMainViewModel.getDeadlinesNum().observe(this, s -> {
            binding.count1.setText(s);
        });

        binding.deadlinesPanel.
                setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        tasksMainViewModel.getDeadlinesData().observe(this, tasksClasses -> {
            DeadlinesAdapter adapter = new DeadlinesAdapter(tasksClasses, this, tasksMainViewModel.getProjectLog(),
                    new CallBackInt() {
                        @Override
                        public void invoke(String position) {
                            TasksClass tasksClass = tasksMainViewModel.getOneDeadline(position);
                            Intent intent;
                            if(tasksMainViewModel.getIsl()){
                                intent = new Intent(TasksActivityMain.this, ActivityTaskMenuForLeader.class);
                            }else{
                                intent = new Intent(TasksActivityMain.this, TaskMenuActivity.class);
                            }
                            intent.putExtra("projectId", tasksMainViewModel.getId());
                            intent.putExtra("projectTitle", tasksMainViewModel.getProjectTitle());
                            intent.putExtra("projectUrlPhoto", tasksMainViewModel.getProjectLog());
                            intent.putExtra("taskTitle", tasksClass.getTaskName());
                            intent.putExtra("taskId", tasksClass.getTaskId());
                            intent.putExtra("isComplete", tasksClass.getTaskComplete());
                            intent.putExtra("completedWorks", tasksClass.getTaskWorks());
                            startActivity(intent);
                        }
                    });
            binding.deadlinesPanel.setAdapter(adapter);
        });

        binding.tasksPanel
                        .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        tasksMainViewModel.getTasksData().observe(this, tasksClasses -> {
            TasksAdapter adapter = new TasksAdapter(tasksClasses, this, tasksMainViewModel.getProjectLog(), new CallBackInt() {
                @Override
                public void invoke(String position) {
                    TasksClass tasksClass = tasksMainViewModel.getOneTask(position);
                    Intent intent;
                    if(tasksMainViewModel.getIsl()){
                        intent = new Intent(TasksActivityMain.this, ActivityTaskMenuForLeader.class);
                    }else{
                        intent = new Intent(TasksActivityMain.this, TaskMenuActivity.class);
                    }
                    intent.putExtra("projectId", tasksMainViewModel.getId());
                    intent.putExtra("projectTitle", tasksMainViewModel.getProjectTitle());
                    intent.putExtra("projectUrlPhoto", tasksMainViewModel.getProjectLog());
                    intent.putExtra("taskTitle", tasksClass.getTaskName());
                    intent.putExtra("taskId", tasksClass.getTaskId());
                    intent.putExtra("isComplete", tasksClass.getTaskComplete());
                    intent.putExtra("completedWorks", tasksClass.getTaskWorks());
                    startActivity(intent);
                }
            });
            binding.tasksPanel.setAdapter(adapter);
        });

        tasksMainViewModel.setTasks();
        tasksMainViewModel.setDeadlines();

        binding.add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksActivityMain.this, CreateTask.class);
                intent.putExtra("projectId", tasksMainViewModel.getId());
                intent.putExtra("projectTitle", tasksMainViewModel.getProjectTitle());
                intent.putExtra("projectUrlPhoto", tasksMainViewModel.getProjectLog());
                startActivity(intent);
            }
        });

        binding.add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksActivityMain.this, CreateDeadline.class);
                intent.putExtra("projectId", tasksMainViewModel.getId());
                intent.putExtra("projectTitle", tasksMainViewModel.getProjectTitle());
                intent.putExtra("projectUrlPhoto", tasksMainViewModel.getProjectLog());
                startActivity(intent);
            }
        });


    }

////    private void setTasks(){
////        PostDatas post = new PostDatas();
////        post.postDataGetProjectTasks("GetTasksFromProject", id, mail, new CallBackTasksInfo()  {
////            @Override
////            public void invoke(String s1, String s2, String s3, String s4) {
////
////                if(!s1.equals("Ошибка")){
////                    binding.noTasks.setVisibility(View.GONE);
////                    String[] idsArr = s1.split("\uD83D\uDD70");
////                    String[] namesArr = s2.split("\uD83D\uDD70");
////                    String[] textsArr = s3.split("\uD83D\uDD70");
////                    String[] completeArr = s4.split("\uD83D\uDD70");
////
////                    if(status == 1){
////                        for (int i = 0; i < namesArr.length; i++) {
////                            int finalI = i;
////                            int finalI1 = i;
////                            int finalI2 = i;
////                            post.postDataGetCompletedWorks("CheckCountOfReadyWorksFromTask", id, mail, idsArr[i], new CallBackInt() {
////                            @Override
////                            public void invoke(String res) {
////                                View custom = getLayoutInflater().inflate(R.layout.tasks_panel, null);
////                                View view = custom.findViewById(R.id.view8);
////                                if(completeArr[finalI2].equals("1")){
////                                    view.setBackgroundResource(R.color.green_transperent);
////                                }
////                                TextView nameu = (TextView) custom.findViewById(R.id.taskTitle);
////                                TextView text = (TextView) custom.findViewById(R.id.taskText);
////                                ImageView prImg = (ImageView) custom.findViewById(R.id.loadImg);
////                                View circleStatus = custom.findViewById(R.id.circle);
////
////                                Glide
////                                        .with(TasksActivityMain.this)
////                                        .load(prPhoto)
////                                        .into(prImg);
////
////                                nameu.setText(namesArr[finalI]);
////                                if (!textsArr[finalI].isEmpty() && textsArr.length > finalI1) {
////                                    text.setText(textsArr[finalI]);
////                                } else {
////                                    text.setText("");
////                                }
////
////                                    if (Integer.parseInt(res) == 0) {
////                                        circleStatus.setBackgroundResource(R.drawable.black_line);
////                                    } else {
////                                        circleStatus.setBackgroundResource(R.drawable.red_line);
////                                    }
////                                int finalI1 = finalI;
////                                custom.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View v) {
////                                        Intent intent;
////                                        if(status == 1){
////                                            intent = new Intent(TasksActivityMain.this, ActivityTaskMenuForLeader.class);
////                                        }else{
////                                            intent = new Intent(TasksActivityMain.this, TaskMenuActivity.class);
////                                        }
////                                        intent.putExtra("projectId", id);
////                                        intent.putExtra("projectTitle", title);
////                                        intent.putExtra("projectUrlPhoto", prPhoto);
////                                        intent.putExtra("taskTitle", namesArr[finalI1]);
////                                        intent.putExtra("taskId", idsArr[finalI1]);
////                                        intent.putExtra("isComplete", completeArr[finalI2]);
////                                        intent.putExtra("completedWorks", Integer.parseInt(res));
////                                        startActivity(intent);
////                                    }
////                                });
////
////                                binding.tasksPlace.addView(custom);
////                            }
////                            });
////                        }
////                    }else{
////                    for (int i = 0; i < namesArr.length; i++) {
////                        int finalI = i;
////                        int finalI1 = i;
////                        int finalI2 = i;
////                        //post.postDataGetCompletedWorks("CheckCountOfReadyWorksFromTask", id, mail, idsArr[i], new CallBackInt() {
////                            //@Override
////                            //public void invoke(String res) {
////                                View custom = getLayoutInflater().inflate(R.layout.tasks_panel, null);
////                                View view = custom.findViewById(R.id.view8);
////                                if(completeArr[finalI2].equals("1")){
////                                    view.setBackgroundResource(R.color.green_transperent);
////                                }
////                                TextView nameu = (TextView) custom.findViewById(R.id.taskTitle);
////                                TextView text = (TextView) custom.findViewById(R.id.taskText);
////                                ImageView prImg = (ImageView) custom.findViewById(R.id.loadImg);
////                                View circleStatus = custom.findViewById(R.id.circle);
////                                circleStatus.setVisibility(View.GONE);
////
////                                Glide
////                                        .with(TasksActivityMain.this)
////                                        .load(prPhoto)
////                                        .into(prImg);
////
////                                nameu.setText(namesArr[finalI]);
////                                if (!textsArr[finalI].isEmpty() && textsArr.length > finalI1) {
////                                    text.setText(textsArr[finalI]);
////                                } else {
////                                    text.setText("");
////                                }
////
////                                /*if (Integer.parseInt(res) == 0) {
////                                    circleStatus.setBackgroundResource(R.drawable.black_line);
////                                } else {
////                                    circleStatus.setBackgroundResource(R.drawable.red_line);
////                                }*/
////
////                                //int finalI1 = finalI;
////                                custom.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View v) {
////                                        Intent intent;
////                                        if(status == 1){
////                                            intent = new Intent(TasksActivityMain.this, ActivityTaskMenuForLeader.class);
////                                        }else{
////                                            intent = new Intent(TasksActivityMain.this, TaskMenuActivity.class);
////                                        }
////                                        intent.putExtra("projectId", id);
////                                        intent.putExtra("projectTitle", title);
////                                        intent.putExtra("projectUrlPhoto", prPhoto);
////                                        intent.putExtra("taskTitle", namesArr[finalI1]);
////                                        intent.putExtra("taskId", idsArr[finalI1]);
////                                        intent.putExtra("isComplete", completeArr[finalI2]);
////                                        startActivity(intent);
////                                    }
////                                });
////
////                                binding.tasksPlace.addView(custom);
////                            }
////                        //});
////                    }
////
////                }else{
////                    binding.noTasks.setVisibility(View.VISIBLE);
////                }
////            }
////        });
////    }
//
//    private void setDeadlines(){
//        PostDatas post = new PostDatas();
//        System.out.println(id + " " + mail);
//        post.postDataGetProjectDeadlines("GetDeadlinesFromProject", id, mail, new CallBackDeadlineInfo()  {
//            @Override
//            public void invoke(String s1, String s2, String s3, String s4, String s5) {
//
//                if(!s1.equals("Ошибка")){
//                    binding.noTasks.setVisibility(View.GONE);
//                    String[] idsArr = s1.split("\uD83D\uDD70");
//                    String[] namesArr = s2.split("\uD83D\uDD70");
//                    String[] textsArr = s3.split("\uD83D\uDD70");
//                    String[] completeArr = s4.split("\uD83D\uDD70");
//
//                    if(status == 1){
//                        for (int i = 0; i < namesArr.length; i++) {
//                            int finalI = i;
//                            int finalI1 = i;
//                            int finalI2 = i;
//                            post.postDataGetCompletedWorks("CheckCountOfReadyWorksFromTask", id, mail, idsArr[i], new CallBackInt() {
//                                @Override
//                                public void invoke(String res) {
//                                    View custom = getLayoutInflater().inflate(R.layout.tasks_panel, null);
//                                    View view = custom.findViewById(R.id.view8);
//                                    if(completeArr[finalI2].equals("1")){
//                                        view.setBackgroundResource(R.color.green_transperent);
//                                    }
//                                    TextView nameu = (TextView) custom.findViewById(R.id.taskTitle);
//                                    TextView text = (TextView) custom.findViewById(R.id.taskText);
//                                    ImageView prImg = (ImageView) custom.findViewById(R.id.loadImg);
//                                    View circleStatus = custom.findViewById(R.id.circle);
//
//                                    Glide
//                                            .with(TasksActivityMain.this)
//                                            .load(prPhoto)
//                                            .into(prImg);
//
//                                    nameu.setText(namesArr[finalI]);
//                                    if (!textsArr[finalI].isEmpty() && textsArr.length > finalI1) {
//                                        text.setText(textsArr[finalI]);
//                                    } else {
//                                        text.setText("");
//                                    }
//
//                                    if (Integer.parseInt(res) == 0) {
//                                        circleStatus.setBackgroundResource(R.drawable.black_line);
//                                    } else {
//                                        circleStatus.setBackgroundResource(R.drawable.red_line);
//                                    }
//                                    int finalI1 = finalI;
//                                    custom.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
////                                            Intent intent;
////                                            if(status == 1){
////                                                intent = new Intent(TasksActivityMain.this, ActivityTaskMenuForLeader.class);
////                                            }else{
////                                                intent = new Intent(TasksActivityMain.this, TaskMenuActivity.class);
////                                            }
////                                            intent.putExtra("projectId", id);
////                                            intent.putExtra("projectTitle", title);
////                                            intent.putExtra("projectUrlPhoto", prPhoto);
////                                            intent.putExtra("taskTitle", namesArr[finalI1]);
////                                            intent.putExtra("taskId", idsArr[finalI1]);
////                                            intent.putExtra("isComplete", completeArr[finalI2]);
////                                            intent.putExtra("completedWorks", Integer.parseInt(res));
////                                            startActivity(intent);
//                                        }
//                                    });
//
//                                    binding.reminderPlace1.addView(custom);
//                                }
//                            });
//                        }
//                    }else{
//                        for (int i = 0; i < namesArr.length; i++) {
//                            int finalI = i;
//                            int finalI1 = i;
//                            int finalI2 = i;
//                            //post.postDataGetCompletedWorks("CheckCountOfReadyWorksFromTask", id, mail, idsArr[i], new CallBackInt() {
//                            //@Override
//                            //public void invoke(String res) {
//                            View custom = getLayoutInflater().inflate(R.layout.tasks_panel, null);
//                            View view = custom.findViewById(R.id.view8);
//                            if(completeArr[finalI2].equals("1")){
//                                view.setBackgroundResource(R.color.green_transperent);
//                            }
//                            TextView nameu = (TextView) custom.findViewById(R.id.taskTitle);
//                            TextView text = (TextView) custom.findViewById(R.id.taskText);
//                            ImageView prImg = (ImageView) custom.findViewById(R.id.loadImg);
//                            View circleStatus = custom.findViewById(R.id.circle);
//                            circleStatus.setVisibility(View.GONE);
//
//                            Glide
//                                    .with(TasksActivityMain.this)
//                                    .load(prPhoto)
//                                    .into(prImg);
//
//                            nameu.setText(namesArr[finalI]);
//                            if (!textsArr[finalI].isEmpty() && textsArr.length > finalI1) {
//                                text.setText(textsArr[finalI]);
//                            } else {
//                                text.setText("");
//                            }
//
//                                /*if (Integer.parseInt(res) == 0) {
//                                    circleStatus.setBackgroundResource(R.drawable.black_line);
//                                } else {
//                                    circleStatus.setBackgroundResource(R.drawable.red_line);
//                                }*/
//
//                            //int finalI1 = finalI;
////                            custom.setOnClickListener(new View.OnClickListener() {
////                                @Override
////                                public void onClick(View v) {
////                                    Intent intent;
////                                    if(status == 1){
////                                        intent = new Intent(TasksActivityMain.this, ActivityTaskMenuForLeader.class);
////                                    }else{
////                                        intent = new Intent(TasksActivityMain.this, TaskMenuActivity.class);
////                                    }
////                                    intent.putExtra("projectId", id);
////                                    intent.putExtra("projectTitle", title);
////                                    intent.putExtra("projectUrlPhoto", prPhoto);
////                                    intent.putExtra("taskTitle", namesArr[finalI1]);
////                                    intent.putExtra("taskId", idsArr[finalI1]);
////                                    intent.putExtra("isComplete", completeArr[finalI2]);
////                                    startActivity(intent);
////                                }
////                            });
//
//                            binding.reminderPlace1.addView(custom);
//                        }
//                        //});
//                    }
//
//                }else{
//                    binding.noTasks.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.blue));
            binding.add1.setImageResource(R.drawable.blue);
            binding.add2.setImageResource(R.drawable.blue);
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.green));
            binding.add1.setImageResource(R.drawable.green_add);
            binding.add2.setImageResource(R.drawable.green_add);
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.brown));
            binding.add1.setImageResource(R.drawable.brown_add);
            binding.add2.setImageResource(R.drawable.brown_add);
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.light_gray));
            binding.add1.setImageResource(R.drawable.light_gray_add);
            binding.add2.setImageResource(R.drawable.light_gray_add);
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.ohra));
            binding.add1.setImageResource(R.drawable.ohra_add);
            binding.add2.setImageResource(R.drawable.ohra_add);
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.red));
            binding.add1.setImageResource(R.drawable.red_add);
            binding.add2.setImageResource(R.drawable.red_add);
        }
        else if(score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this, R.color.orange));
            binding.add1.setImageResource(R.drawable.brown_add);
            binding.add2.setImageResource(R.drawable.brown_add);
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.violete));
            binding.add1.setImageResource(R.drawable.violete_add);
            binding.add2.setImageResource(R.drawable.violete_add);
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(TasksActivityMain.this,R.color.main_green));
            binding.add1.setImageResource(R.drawable.blue_green_add);
            binding.add2.setImageResource(R.drawable.blue_green_add);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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