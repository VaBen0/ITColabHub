package ru.dvteam.itcollabhub.view.projectmenusviews.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.ProblemsParticip;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.UsersChosenTheme;
import ru.dvteam.itcollabhub.databinding.ActivityControlPanel3Binding;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ControlPanelViewModel;

public class ControlPanel3 extends AppCompatActivity {

    ActivityControlPanel3Binding binding;
    ControlPanelViewModel controlPanelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityControlPanel3Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        controlPanelViewModel = new ViewModelProvider(this).get(ControlPanelViewModel.class);

        controlPanelViewModel.getProjectInformation().observe(this, projectInformation -> {

            binding.nameProject.setText(projectInformation.getProjectTitle());
            Glide
                    .with(ControlPanel3.this)
                    .load(projectInformation.getProjectLogo())
                    .into(binding.prLogo);

            if(((int)(parse(projectInformation.getProjectPurposes()) * 100) == 100)){
                Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_greeen);
                binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_greeen);
                binding.purpProgress.setProgressDrawable(progressDrawable);
            }
            if(((int)(parse(projectInformation.getProjectProblems()) * 100) == 100)){
                Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_greeen);
                binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_greeen);
                binding.problemsProgress.setProgressDrawable(progressDrawable);
            }
            if(((int)(parse(projectInformation.getProjectTasks()) * 100) == 100)){
                Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_greeen);
                binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_greeen);
                binding.progressOfTasks.setProgressDrawable(progressDrawable);
            }

            if(projectInformation.getProjectIsLeader().equals("0")){
                binding.main.removeView(binding.editProjectConst);
            }

            ProgressBar purposesProgress = findViewById(R.id.purp_progress);
            ProgressBar problemsProgress = findViewById(R.id.problems_progress);
            ProgressBar tasksProgress = findViewById(R.id.progress_of_tasks);
            purposesProgress.setMax(100);
            problemsProgress.setMax(100);
            tasksProgress.setMax(100);

            ObjectAnimator animation = ObjectAnimator.ofInt(purposesProgress, "progress", 0,
                    (int)(parse(projectInformation.getProjectPurposes()) * 100));
            animation.setStartDelay(300);
            animation.setDuration(1000);
            animation.setAutoCancel(true);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();

            ObjectAnimator anim = ObjectAnimator.ofInt(problemsProgress, "progress", 0,
                    (int)(parse(projectInformation.getProjectProblems()) * 100));
            anim.setStartDelay(300);
            anim.setDuration(1000);
            anim.setAutoCancel(true);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();

            ObjectAnimator anim3 = ObjectAnimator.ofInt(tasksProgress, "progress", 0,
                    (int)(parse(projectInformation.getProjectTasks()) * 100));
            anim3.setStartDelay(300);
            anim3.setDuration(1000);
            anim3.setAutoCancel(true);
            anim3.setInterpolator(new DecelerateInterpolator());
            anim3.start();
        });

        controlPanelViewModel.setProjectInformation();

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ControlPanel3.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        binding.purpProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanel3.this, PurposeParticipiant.class);
                startActivity(intent);
            }
        });

        binding.problemsProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanel3.this, ProblemsParticip.class);
                startActivity(intent);
            }
        });

        binding.projectFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanel3.this, ProjectFilesEndProject.class);
                startActivity(intent);
            }
        });

        binding.editProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanel3.this, EditProject.class);
                startActivity(intent);
            }
        });

        binding.projectParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanel3.this, ProjectParticipants.class);
                startActivity(intent);
                GlobalProjectInformation.getInstance().setLead(false);
            }
        });

        binding.progressOfTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanel3.this, TasksActivityMain.class);
                startActivity(intent);
                GlobalProjectInformation.getInstance().setLead(false);
            }
        });
    }
    double parse(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }

    @Override
    protected void onRestart() {
        controlPanelViewModel.setProjectInformation();
        super.onRestart();
    }

//    private void setTasks(){
//        PostDatas post = new PostDatas();
//        post.postDataGetProjectTasks("GetTasksFromProject", id, mail, new CallBackTasksInfo()  {
//            @Override
//            public void invoke(String s1, String s2, String s3, String s4) {
//                if(!s1.equals("Ошибка")) {
//                    binding.textView15.setVisibility(View.GONE);
//                    String[] idsArr = s1.split("\uD83D\uDD70");
//                    String[] namesArr = s2.split("\uD83D\uDD70");
//                    String[] textsArr = s3.split("\uD83D\uDD70");
//                    String[] completeArr = s4.split("\uD83D\uDD70");
//                    for (int i = 0; i < namesArr.length; i++) {
//                        int finalI = i;
//                        int finalI1 = i;
//                        int finalI2 = i;
//                        if (completeArr[finalI2].equals("0")) {
//
//                            View custom = getLayoutInflater().inflate(R.layout.reminder, null);
//                            ImageView loadImg = custom.findViewById(R.id.loadImg);
//                            TextView name = custom.findViewById(R.id.textView33);
//                            TextView descr = custom.findViewById(R.id.textView32);
//                            name.setText(namesArr[finalI]);
//                            descr.setText("Задание");
//                            custom.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = new Intent(ControlPanel.this, TaskActivityWatch.class);
//                                    intent.putExtra("projectTitle", title);
//                                    intent.putExtra("projectUrlPhoto", urlPhoto);
//                                    intent.putExtra("projectId1", id);
//                                    intent.putExtra("problemName", namesArr[finalI]);
//                                    intent.putExtra("problemDescription", textsArr[finalI]);
//                                    startActivity(intent);
//                                }
//                            });
//
//                            Glide
//                                    .with(ControlPanel.this)
//                                    .load(urlPhoto)
//                                    .listener(new RequestListener<Drawable>() {
//                                        @Override
//                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                            return false;
//                                        }
//
//                                        @Override
//                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                            Transition t = null;
//                                            t = new Slide(Gravity.END);
//                                            t.setDuration(1000);
//
//                                            TransitionManager.beginDelayedTransition(binding.reminderPlace, t);
//
//                                            binding.reminderPlace.addView(custom);
//                                            return false;
//                                        }
//                                    })
//                                    .into(loadImg);
//
//                        }
//                    }
//                }
//            }
//        });
//    }
//    public void getAdverts(String id1, String id2){
//        if(!id1.isEmpty() || !id2.isEmpty()){
//            binding.textView15.setVisibility(View.GONE);
//            PostDatas post = new PostDatas();
//
//            post.postDataGetProjectAds("GetProjectAds", id1, new CallBackInt() {
//                @Override
//                public void invoke(String res) {
//                    String[] inf = res.split("\uD83D\uDD70");
//                    String[] idm = id1.split(",");
//                    for(int i = 0; i < inf.length; i += 3){
//                        View custom = getLayoutInflater().inflate(R.layout.reminder, null);
//                        ImageView loadImg = custom.findViewById(R.id.loadImg);
//                        TextView name = custom.findViewById(R.id.textView33);
//                        TextView descr = custom.findViewById(R.id.textView32);
//
//                        int finalI = i;
//                        descr.setText("Объявление");
//
//                        String[] nameArr = inf[i].split(" ");
//                        int len = 0;
//                        String itog = "";
//                        for(int j = 0; j < nameArr.length; j++){
//                            len += nameArr[j].length();
//                            if(len + 3 < 22){
//                                if(j == 0){
//                                    itog += nameArr[j];
//                                }
//                                else{
//                                    itog += " " + nameArr[j];
//                                }
//                            }
//                            else{
//                                itog += "...";
//                                break;
//                            }
//                        }
//
//                        custom.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                              Intent intent = new Intent(ControlPanel.this, Advertisment.class);
//                                intent.putExtra("problemPhoto", inf[finalI+2]);
//                                intent.putExtra("projectTitle", title);
//                                intent.putExtra("projectUrlPhoto", urlPhoto);
//                                intent.putExtra("projectId1", id);
//                                intent.putExtra("problemName", inf[finalI]);
//                                intent.putExtra("problemDescription", inf[finalI + 1]);
//                                intent.putExtra("problemId", idm[finalI / 3]);
//                                //Toast.makeText(ProjectAdvertisments.this, idm[finalI / 3] + " " + prId, Toast.LENGTH_SHORT).show();
//                                startActivity(intent);
//                            }
//                        });
//
//                        name.setText(itog);
//                        Glide
//                                .with(ControlPanel.this)
//                                .load(inf[i+2])
//                                .listener(new RequestListener<Drawable>() {
//                                    @Override
//                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                        Transition t = null;
//                                        t = new Slide(Gravity.END);
//                                        t.setDuration(1000);
//
//                                        TransitionManager.beginDelayedTransition(binding.reminderPlace, t);
//
//                                        binding.reminderPlace.addView(custom);
//                                        return false;
//                                    }
//                                })
//                                .into(loadImg);
//
//
//                    }
//
//                }
//            });
//            post.postDataGetProjectAds("GetProjectAds", id2, new CallBackInt() {
//                @Override
//                public void invoke(String res) {
//                    String[] inf = res.split("\uD83D\uDD70");
//                    String[] idm = id2.split(",");
//                    for(int i = 0; i < inf.length; i += 3){
//                        View custom = getLayoutInflater().inflate(R.layout.reminder, null);
//                        ImageView loadImg = custom.findViewById(R.id.loadImg);
//                        TextView name = custom.findViewById(R.id.textView33);
//                        TextView descr = custom.findViewById(R.id.textView32);
//
//                        int finalI = i;
//                        descr.setText("Объявление");
//
//                        String[] nameArr = inf[i].split(" ");
//                        int len = 0;
//                        String itog = "";
//                        for(int j = 0; j < nameArr.length; j++){
//                            len += nameArr[j].length();
//                            if(len + 3 < 22){
//                                if(j == 0){
//                                    itog += nameArr[j];
//                                }
//                                else{
//                                    itog += " " + nameArr[j];
//                                }
//                            }
//                            else{
//                                itog += "...";
//                                break;
//                            }
//                        }
//
//                        custom.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(ControlPanel.this, Advertisment.class);
//                                intent.putExtra("problemPhoto", inf[finalI+2]);
//                                intent.putExtra("projectTitle", title);
//                                intent.putExtra("projectUrlPhoto", urlPhoto);
//                                intent.putExtra("projectId1", id);
//                                intent.putExtra("problemName", inf[finalI]);
//                                intent.putExtra("problemDescription", inf[finalI + 1]);
//                                intent.putExtra("problemId", idm[finalI / 3]);
//                                //Toast.makeText(ProjectAdvertisments.this, idm[finalI / 3] + " " + prId, Toast.LENGTH_SHORT).show();
//                                startActivity(intent);
//                            }
//                        });
//
//                        name.setText(itog);
//                        Glide
//                                .with(ControlPanel.this)
//                                .load(inf[i+2])
//                                .listener(new RequestListener<Drawable>() {
//                                    @Override
//                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                        Transition t = null;
//                                        t = new Slide(Gravity.END);
//                                        t.setDuration(1000);
//
//                                        TransitionManager.beginDelayedTransition(binding.reminderPlace, t);
//
//                                        binding.reminderPlace.addView(custom);
//                                        return false;
//                                    }
//                                })
//                                .into(loadImg);
//                        setTasks();
//                    }
//                }
//            });
//        }
//        else{
//            binding.textView15.setVisibility(View.VISIBLE);
//            setTasks();
//        }
//
//    }
//
//    private void getAdvertIds(){
//        PostDatas post = new PostDatas();
//        post.postDataGetProjectAdsIds("GetProjectAdsIds", id, new CallBackInt() {
//            @Override
//            public void invoke(String res) {
//                post.postDataGetProjectAdsIds("GetProjectAdsIds2", id, new CallBackInt() {
//                    @Override
//                    public void invoke(String res2) {
//                        getAdverts(res, res2);
//                    }
//                });
//            }
//        });
//    }

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