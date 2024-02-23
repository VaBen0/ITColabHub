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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        binding = ActivityControlPanel2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanel2.this,R.color.blue));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.blue));
            binding.projectChat.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.blue));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_bg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_bg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_bg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_bg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_bg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_bg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanel2.this,R.color.green));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.green));
            binding.projectChat.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.green));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_gg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_gg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_gg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_gg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_gg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_gg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanel2.this,R.color.brown));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.brown));
            binding.projectChat.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.brown));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_brg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_brg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_brg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_brg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_brg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_brg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanel2.this,R.color.light_gray));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.light_gray));
            binding.projectChat.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.light_gray));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_lgg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_lgg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_lgg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_lgg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_lgg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_lgg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanel2.this,R.color.ohra));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.ohra));
            binding.projectChat.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.ohra));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_ohg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_ohg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_ohg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_ohg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_ohg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_ohg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanel2.this,R.color.red));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.red));
            binding.projectChat.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.red));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_rg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_rg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_rg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_rg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_rg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_rg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 30000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanel2.this,R.color.orange));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.orange));
            binding.projectChat.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.orange));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_og);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_og);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_og);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_og);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_og);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_og);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanel2.this,R.color.violete));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.violete));
            binding.projectChat.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.violete));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_vg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_vg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_vg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_vg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_vg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_vg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ControlPanel2.this,R.color.main_green));
            binding.projectFiles.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.main_green));
            binding.projectChat.setBackgroundTintList(ContextCompat.getColorStateList(ControlPanel2.this, R.color.main_green));
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_mgg);
            binding.purpProgress.setBackgroundResource(R.drawable.custom_progress_bar_mgg);
            binding.purpProgress.setProgressDrawable(progressDrawable);
            Drawable progressDrawable1 = getResources().getDrawable(R.drawable.custom_progress_bar_mgg);
            binding.problemsProgress.setBackgroundResource(R.drawable.custom_progress_bar_mgg);
            binding.problemsProgress.setProgressDrawable(progressDrawable1);
            Drawable progressDrawable2 = getResources().getDrawable(R.drawable.custom_progress_bar_mgg);
            binding.progressOfTasks.setBackgroundResource(R.drawable.custom_progress_bar_mgg);
            binding.progressOfTasks.setProgressDrawable(progressDrawable2);
        }

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        id = arguments.getString("projectId");

        getAdvertIds();

        PostDatas postDatas = new PostDatas();
        postDatas.postDataGetProjectInformation("GetProjectMainInformation", id, mail, new CallBackInt4() {
            @Override
            public void invoke(String name, String photoUrl, String descript, double isend, String purposes,
                               String problems, String peoples, String time, String time1, String tg, String vk, String webs, String purposesids,
                               String problemsids, String isl) {
                title = name;
                urlPhoto = photoUrl;
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

                ProgressBar purposesProgress = findViewById(R.id.purp_progress);
                ProgressBar problemsProgress = findViewById(R.id.problems_progress);
                purposesProgress.setMax(100);
                problemsProgress.setMax(100);

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
            }
        });

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

        /*for (int i = 0; i < 5; i++) {
            View custom = getLayoutInflater().inflate(R.layout.reminder, null);

            binding.reminderPlace.addView(custom);
        }*/
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
}