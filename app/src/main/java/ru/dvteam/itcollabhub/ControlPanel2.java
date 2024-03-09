package ru.dvteam.itcollabhub;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.service.controls.Control;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.dvteam.itcollabhub.databinding.ActivityControlPanel2Binding;

public class ControlPanel2 extends AppCompatActivity {

    private static final String TAG = "MyApp";

    ActivityControlPanel2Binding binding;
    String title, urlPhoto;
    String mail, islead;
    String purposesidss, problemss, id;
    String description, tgMain, vkMain, webMain, isOpenM, isVisibleM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        binding = ActivityControlPanel2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ControlPanel2.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        id = arguments.getString("projectId");

        getMainInfo();

        Date date = new Date();
        LocalTime current = null;
        long millis = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            current = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
            millis = ChronoUnit.MILLIS.between(current, LocalTime.MAX);
        }
        else {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            millis = (c.getTimeInMillis() - System.currentTimeMillis());
        }

        //timeInMilliseconds = 86400000 - timeInMilliseconds;
        new CountDownTimer(millis, 1000) {

            public void onTick(long millisUntilFinished) {
                long allSeconds = millisUntilFinished / 1000;
                long seconds = allSeconds % 60;
                long minutes = (allSeconds / 60) % 60;
                long hours = (allSeconds / 3600) % 24;
                binding.timer.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                // logic to set the EditText could go here
            }

            public void onFinish() {
                binding.timer.setText("А всё)");
            }

        }.start();

        binding.purpProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(islead.equals("0")){
                    Intent intent = new Intent(ControlPanel2.this, PurposeParticipiant.class);
                    intent.putExtra("projectTitle", title);
                    intent.putExtra("projectUrlPhoto", urlPhoto);
                    intent.putExtra("projectId", purposesidss);
                    intent.putExtra("projectId1", id);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ControlPanel2.this, Purpose.class);
                    intent.putExtra("projectTitle", title);
                    intent.putExtra("projectUrlPhoto", urlPhoto);
                    intent.putExtra("projectId", purposesidss);
                    intent.putExtra("projectId1", id);
                    startActivity(intent);
                }
            }
        });

        binding.problemsProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(islead.equals("0")){
                    Intent intent = new Intent(ControlPanel2.this, ProblemsParticip.class);
                    intent.putExtra("projectTitle", title);
                    intent.putExtra("projectUrlPhoto", urlPhoto);
                    intent.putExtra("projectId1", id);
                    intent.putExtra("projectId", problemss);
                    intent.putExtra("leader", islead);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ControlPanel2.this, Problems.class);
                    intent.putExtra("projectTitle", title);
                    intent.putExtra("projectUrlPhoto", urlPhoto);
                    intent.putExtra("projectId1", id);
                    intent.putExtra("projectId", problemss);
                    intent.putExtra("leader", islead);
                    startActivity(intent);
                }
            }
        });

        binding.projectFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanel2.this, ProjectFiles.class);
                intent.putExtra("projectTitle", title);
                intent.putExtra("projectUrlPhoto", urlPhoto);
                intent.putExtra("projectId1", id);
                startActivity(intent);
            }
        });

        binding.progressOfTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanel2.this, TasksActivityMain.class);
                intent.putExtra("projectTitle", title);
                intent.putExtra("projectUrlPhoto", urlPhoto);
                intent.putExtra("projectId", id);
                intent.putExtra("status", 0);
                startActivity(intent);
            }
        });

        binding.projectChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlPanel2.this, ProjectPage.class);
                intent.putExtra("projectTitle", title);
                intent.putExtra("projectUrlPhoto", urlPhoto);
                intent.putExtra("projectDescription", description);
                intent.putExtra("webLink", webMain);
                startActivity(intent);
            }
        });

    }
    private void getMainInfo(){
        PostDatas postDatas = new PostDatas();
        postDatas.postDataGetProjectInformation("GetProjectMainInformation1", id, mail, new CallBackInt4() {
            @Override
            public void invoke(String name, String photoUrl, String descript, double isend, String purposes,
                               String problems, String peoples, String time, String time1, String tg, String vk, String webs, String purposesids,
                               String problemsids, String isl, String tasks, String isOpen, String isVisible) {
                title = name;
                urlPhoto = photoUrl;
                description = descript;
                webMain = webs;

                binding.nameProject.setText(name);
                Glide
                        .with(ControlPanel2.this)
                        .load(photoUrl)
                        .into(binding.prLogo);
                purposesidss = purposesids;
                islead = isl;
                problemss = problemsids;

                if(((int)(parse(purposes) * 100) == 100)){
                    Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_greeen);
                    binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_greeen);
                    binding.purpProgress.setProgressDrawable(progressDrawable);
                }
                if(((int)(parse(problems) * 100) == 100)){
                    Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_greeen);
                    binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_greeen);
                    binding.problemsProgress.setProgressDrawable(progressDrawable);
                }
                if(((int)(parse(tasks) * 100) == 100)){
                    Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_greeen);
                    binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_greeen);
                    binding.progressOfTasks.setProgressDrawable(progressDrawable);
                }

                ProgressBar purposesProgress = findViewById(R.id.purp_progress);
                ProgressBar problemsProgress = findViewById(R.id.problems_progress);
                ProgressBar tasksProgress = findViewById(R.id.progress_of_tasks);
                purposesProgress.setMax(100);
                problemsProgress.setMax(100);
                tasksProgress.setMax(100);

                ObjectAnimator animation = ObjectAnimator.ofInt(purposesProgress, "progress", 0, (int)(parse(purposes) * 100));
                animation.setStartDelay(300);
                animation.setDuration(1000);
                animation.setAutoCancel(true);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();

                ObjectAnimator anim = ObjectAnimator.ofInt(problemsProgress, "progress", 0, (int)(parse(problems) * 100));
                anim.setStartDelay(300);
                anim.setDuration(1000);
                anim.setAutoCancel(true);
                anim.setInterpolator(new DecelerateInterpolator());
                anim.start();

                ObjectAnimator anim3 = ObjectAnimator.ofInt(tasksProgress, "progress", 0, (int)(parse(tasks) * 100));
                anim3.setStartDelay(300);
                anim3.setDuration(1000);
                anim3.setAutoCancel(true);
                anim3.setInterpolator(new DecelerateInterpolator());
                anim3.start();

                getAdvertIds();
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
        binding.reminderPlace.removeAllViews();
        getMainInfo();
        super.onRestart();
    }

    public void getAdverts(String id1, String id2){
        if(!id1.isEmpty() || !id2.isEmpty()){
            PostDatas post = new PostDatas();
            post.postDataGetProjectAds("GetProjectAds", id1, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    String[] inf = res.split("\uD83D\uDD70");
                    String[] idm = id1.split(",");
                    for(int i = 0; i < inf.length; i += 3){
                        View custom = getLayoutInflater().inflate(R.layout.reminder, null);
                        ImageView loadImg = custom.findViewById(R.id.loadImg);
                        TextView name = custom.findViewById(R.id.textView33);
                        TextView descr = custom.findViewById(R.id.textView32);

                        int finalI = i;
                        descr.setText("Объявление");

                        String[] nameArr = inf[i].split(" ");
                        int len = 0;
                        String itog = "";
                        for(int j = 0; j < nameArr.length; j++){
                            len += nameArr[j].length();
                            if(len + 3 < 22){
                                if(j == 0){
                                    itog += nameArr[j];
                                }
                                else{
                                    itog += " " + nameArr[j];
                                }
                            }
                            else{
                                itog += "...";
                                break;
                            }
                        }

                        custom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ControlPanel2.this, Advertisment.class);
                                intent.putExtra("problemPhoto", inf[finalI+2]);
                                intent.putExtra("projectTitle", title);
                                intent.putExtra("projectUrlPhoto", urlPhoto);
                                intent.putExtra("projectId1", id);
                                intent.putExtra("problemName", inf[finalI]);
                                intent.putExtra("problemDescription", inf[finalI + 1]);
                                intent.putExtra("problemId", idm[finalI / 3]);
                                //Toast.makeText(ProjectAdvertisments.this, idm[finalI / 3] + " " + prId, Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        });

                        name.setText(itog);
                        Glide
                                .with(ControlPanel2.this)
                                .load(inf[i+2])
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        Transition t = null;
                                        t = new Slide(Gravity.END);
                                        t.setDuration(1000);

                                        TransitionManager.beginDelayedTransition(binding.reminderPlace, t);

                                        binding.reminderPlace.addView(custom);
                                        return false;
                                    }
                                })
                                .into(loadImg);
                    }

                    setTasks();
                }
            });
            post.postDataGetProjectAds("GetProjectAds", id2, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    String[] inf = res.split("\uD83D\uDD70");
                    String[] idm = id2.split(",");
                    for(int i = 0; i < inf.length; i += 3){
                        View custom = getLayoutInflater().inflate(R.layout.reminder, null);
                        ImageView loadImg = custom.findViewById(R.id.loadImg);
                        TextView name = custom.findViewById(R.id.textView33);
                        TextView descr = custom.findViewById(R.id.textView32);

                        int finalI = i;
                        descr.setText("Объявление");

                        String[] nameArr = inf[i].split(" ");
                        int len = 0;
                        String itog = "";
                        for(int j = 0; j < nameArr.length; j++){
                            len += nameArr[j].length();
                            if(len + 3 < 22){
                                if(j == 0){
                                    itog += nameArr[j];
                                }
                                else{
                                    itog += " " + nameArr[j];
                                }
                            }
                            else{
                                itog += "...";
                                break;
                            }
                        }

                        custom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ControlPanel2.this, Advertisment.class);
                                intent.putExtra("problemPhoto", inf[finalI+2]);
                                intent.putExtra("projectTitle", title);
                                intent.putExtra("projectUrlPhoto", urlPhoto);
                                intent.putExtra("projectId1", id);
                                intent.putExtra("problemName", inf[finalI]);
                                intent.putExtra("problemDescription", inf[finalI + 1]);
                                intent.putExtra("problemId", idm[finalI / 3]);
                                //Toast.makeText(ProjectAdvertisments.this, idm[finalI / 3] + " " + prId, Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        });

                        name.setText(itog);
                        Glide
                                .with(ControlPanel2.this)
                                .load(inf[i+2])
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        Transition t = null;
                                        t = new Slide(Gravity.END);
                                        t.setDuration(1000);

                                        TransitionManager.beginDelayedTransition(binding.reminderPlace, t);

                                        binding.reminderPlace.addView(custom);
                                        return false;
                                    }
                                })
                                .into(loadImg);

                    }
                }
            });

        }
        else{
            binding.textView15.setVisibility(View.VISIBLE);
        }

    }

    public void getAdvertIds(){
        PostDatas post = new PostDatas();
        post.postDataGetProjectAdsIds("GetProjectAdsIds", id, new CallBackInt() {
            @Override
            public void invoke(String res) {
                post.postDataGetProjectAdsIds("GetProjectAdsIds2", id, new CallBackInt() {
                    @Override
                    public void invoke(String res2) {
                        getAdverts(res, res2);
                    }
                });
            }
        });
    }

    private void setTasks(){
        PostDatas post = new PostDatas();
        post.postDataGetProjectTasks("GetTasksFromProject", id, mail, new CallBackTasksInfo()  {
            @Override
            public void invoke(String s1, String s2, String s3, String s4) {
                if(!s1.equals("Ошибка")) {
                    binding.textView15.setVisibility(View.GONE);
                    String[] idsArr = s1.split("\uD83D\uDD70");
                    String[] namesArr = s2.split("\uD83D\uDD70");
                    String[] textsArr = s3.split("\uD83D\uDD70");
                    String[] completeArr = s4.split("\uD83D\uDD70");
                    for (int i = 0; i < namesArr.length; i++) {
                        int finalI = i;
                        int finalI1 = i;
                        int finalI2 = i;
                        if (completeArr[finalI2].equals("0")) {

                            View custom = getLayoutInflater().inflate(R.layout.reminder, null);
                            ImageView loadImg = custom.findViewById(R.id.loadImg);
                            TextView name = custom.findViewById(R.id.textView33);
                            TextView descr = custom.findViewById(R.id.textView32);

                            name.setText(namesArr[finalI]);
                            descr.setText("Задание");

                            custom.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ControlPanel2.this, TaskActivityWatch.class);
                                    intent.putExtra("projectTitle", title);
                                    intent.putExtra("projectUrlPhoto", urlPhoto);
                                    intent.putExtra("projectId1", id);
                                    intent.putExtra("problemName", namesArr[finalI]);
                                    intent.putExtra("problemDescription", textsArr[finalI]);
                                    startActivity(intent);
                                }
                            });

                            Glide
                                    .with(ControlPanel2.this)
                                    .load(urlPhoto)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            Transition t = null;
                                            t = new Slide(Gravity.END);
                                            t.setDuration(1000);

                                            TransitionManager.beginDelayedTransition(binding.reminderPlace, t);

                                            binding.reminderPlace.addView(custom);
                                            return false;
                                        }
                                    })
                                    .into(loadImg);
                        }
                    }
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