package ru.dvteam.itcollabhub;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.databinding.ActivityPartisipantTasksBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class PartisipantTasks extends AppCompatActivity {

    ActivityPartisipantTasksBinding binding;
    private String mail, id, title, prPhoto, taskName, queue, idsArrStr;
    private ArrayList<String> idsArr, idsTextBlocks, idsLinkBlocks, idsYouTubeBlocks, idsImageBlocks;
    String[] textBlockArr, linkBlockArr, youtubeBlockArr, imageBlockArr;
    private Boolean click = true;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityPartisipantTasksBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        fa = this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        idsArr = new ArrayList<String>();
        idsTextBlocks = new ArrayList<String>();
        idsLinkBlocks = new ArrayList<String>();
        idsYouTubeBlocks = new ArrayList<String>();
        idsImageBlocks = new ArrayList<String>();

        id = Objects.requireNonNull(getIntent().getExtras()).getString("projectId");
        title = Objects.requireNonNull(getIntent().getExtras()).getString("projectTitle");
        prPhoto = Objects.requireNonNull(getIntent().getExtras()).getString("projectUrlPhoto");
        taskName = Objects.requireNonNull(getIntent().getExtras()).getString("TaskName");
        queue = Objects.requireNonNull(getIntent().getExtras()).getString("Queue");
        String stringBlock = Objects.requireNonNull(getIntent().getExtras()).getString("StringBlock");
        String linkBlock = Objects.requireNonNull(getIntent().getExtras()).getString("LinkBlock");
        String youtubeBlock = Objects.requireNonNull(getIntent().getExtras()).getString("YouTubeBlock");
        String imageBlock = Objects.requireNonNull(getIntent().getExtras()).getString("ImageBlock");

        binding.nameProject.setText(title);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.prLogo);

        assert stringBlock != null;
        if(stringBlock.equals("Empty")){
            textBlockArr = new String[0];
        }else{
            textBlockArr = stringBlock.split("_/-");
        }
        assert linkBlock != null;
        if(linkBlock.equals("Empty")){
            linkBlockArr = new String[0];
        }else{
            linkBlockArr = linkBlock.split("_/-");
        }
        assert youtubeBlock != null;
        if(youtubeBlock.equals("Empty")){
            youtubeBlockArr = new String[0];
        }else{
            youtubeBlockArr = youtubeBlock.split("_/-");
        }
        assert imageBlock != null;
        if(imageBlock.equals("Empty")){
            imageBlockArr = new String[0];
        }else{
            imageBlockArr = imageBlock.split("_/-");
        }

        PostDatas post = new PostDatas();
        post.postDataGetProjectParticipants("GetPeoplesFromProjects", id, mail, new CallBackInt5() {
            @Override
            public void invoke(String s1, String s2, String s3) {
                String[] ids = s1.split("\uD83D\uDD70");
                String[] names = s2.split("\uD83D\uDD70");
                String[] photos = s3.split("\uD83D\uDD70");

                for (int i = 0; i < ids.length; i++) {
                    View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
                    TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                    ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                    ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                    TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                    ImageView messege = (ImageView) custom.findViewById(R.id.notban);

                    userCircle.setVisibility(View.GONE);
                    project1.setVisibility(View.GONE);

                    setButtonColor(score, messege);

                    Glide
                            .with(PartisipantTasks.this)
                            .load(photos[i])
                            .into(loadImage);
                    nameu.setText(names[i]);

                    int finalI = i;

                    messege.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            idsArr.add(ids[finalI]);
                            System.out.println(ids[finalI]);
                            messege.setVisibility(View.GONE);
                        }
                    });
                    binding.main.addView(custom);
                }
                View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                binding.main.addView(empty);
            }
        });

        binding.uploadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(idsArr + " " + mail);
                if(click) {
                    binding.load.setVisibility(View.VISIBLE);
                    binding.back.setVisibility(View.VISIBLE);
                    click = false;
                    if (!idsArr.isEmpty()) {
                        idsArrStr = String.join(",", idsArr);
                        createAllTextBlocks();
                    } else {
                        //Toast.makeText(PartisipantTasks.this, "Добавьте людей", Toast.LENGTH_SHORT).show();
                        idsArrStr = "";
                        createAllTextBlocks();
                    }
                }
            }
        });

    }

    public void createAllTextBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < textBlockArr.length; i++){
            int finalI = i;
            post.postDataCreateStringBlock("CreateTextBlock", id, mail, textBlockArr[i], new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsTextBlocks.add(res);
                    if(finalI == textBlockArr.length - 1){
                        System.out.println("Text ok");
                        createAllLinksBlocks();
                    }
                }
            });
        }
        if(textBlockArr.length == 0){
            System.out.println("Text ok");
            createAllLinksBlocks();
        }
    }

    public void createAllLinksBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < linkBlockArr.length; i++){
            String[] s = linkBlockArr[i].split("\uD83D\uDD70");
            int finalI = i;
            post.postDataCreateLinkBlock("CreateLinkBlock", id, mail, s[0], s[1], new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsLinkBlocks.add(res);
                    if(finalI == linkBlockArr.length - 1){
                        System.out.println("Links ok");
                        createAllYoutubeBlocks();
                    }
                }
            });
        }
        if(linkBlockArr.length == 0){
            System.out.println("Links ok");
            createAllYoutubeBlocks();
        }
    }

    public void createAllYoutubeBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < youtubeBlockArr.length; i++){
            String[] s = youtubeBlockArr[i].split("\uD83D\uDD70");
            int finalI = i;
            post.postDataCreateYoutubeBlock("CreateYouTubeBlock", id, mail, s[0], s[1], new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsYouTubeBlocks.add(res);
                    if(finalI == youtubeBlockArr.length - 1){
                        System.out.println("YouTube ok");
                        createAllImageBlocks(0);
                    }
                }
            });
        }
        if(youtubeBlockArr.length == 0){
            System.out.println("YouTube ok");
            createAllImageBlocks(0);
        }
    }

    public void createAllImageBlocks(int i){
        PostDatas post = new PostDatas();
        if(imageBlockArr.length == 0){
            System.out.println("Imgs ok");
            createTask();
        }else{
            String[] s = imageBlockArr[i].split("\uD83D\uDD70");
            File file = new File(s[0]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            int finalI = i;
            post.postDataCreateImageBlock("CreateImageBlock", s[1], requestBody, id, mail, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsImageBlocks.add(res);
                    if(finalI == imageBlockArr.length - 1){
                        createTask();
                    }else{
                        createAllImageBlocks(finalI + 1);
                    }
                }
            });
        }
    }

    public void createTask(){
        String idsTextBlocksStr;
        String idsLinkBlocksStr;
        String idsImageBlocksArr;
        String idsYouTubeBlocksArr;

        if(idsTextBlocks.size() == 0){
            idsTextBlocksStr = "";
        }else{
            idsTextBlocksStr = String.join(",", idsTextBlocks);
        }
        if(idsLinkBlocks.size() == 0){
            idsLinkBlocksStr = "";
        }else{
            idsLinkBlocksStr = String.join(",", idsLinkBlocks);
        }
        if(idsImageBlocks.size() == 0){
            idsImageBlocksArr = "";
        }else{
            idsImageBlocksArr = String.join(",", idsImageBlocks);
        }
        if(idsYouTubeBlocks.size() == 0){
            idsYouTubeBlocksArr = "";
        }else{
            idsYouTubeBlocksArr = String.join(",", idsYouTubeBlocks);
        }

        PostDatas post = new PostDatas();
        post.postDataCreateTask("CreateTask", id, mail, taskName, queue, idsTextBlocksStr,
                idsLinkBlocksStr, idsArrStr, idsImageBlocksArr,
                idsYouTubeBlocksArr, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        CreateTask.fa.finish();
                        finish();
                    }
                });
    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(PartisipantTasks.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(PartisipantTasks.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(PartisipantTasks.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(PartisipantTasks.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(PartisipantTasks.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(PartisipantTasks.this,R.color.red));
        }
        else if(score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(PartisipantTasks.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(PartisipantTasks.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(PartisipantTasks.this,R.color.main_green));
        }
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

    public void setThemeActivity(){
        int themeType = UsersChosenTheme.getThemeNum();

        switch (themeType){
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