package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.databinding.ActivityPartisipantTasksBinding;

public class PartisipantTasks extends AppCompatActivity {

    ActivityPartisipantTasksBinding binding;
    private String mail, id, title, prPhoto, taskName, queue;
    private ArrayList<String> idsArr, idsTextBlocks, idsLinkBlocks, idsYouTubeBlocks, idsImageBlocks;
    String[] textBlockArr, linkBlockArr, youtubeBlockArr, imageBlockArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartisipantTasksBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");

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

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.advertPhoto);

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
                    messege.setImageResource(R.drawable.violete_add);

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
                if(!idsArr.isEmpty()) {
                    createAllTextBlocks();
                }
                else{
                    Toast.makeText(PartisipantTasks.this, "Добавьте людей", Toast.LENGTH_SHORT).show();
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
                        createAllLinksBlocks();
                    }
                }
            });
        }
        if(textBlockArr.length == 0){
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
                        createAllYoutubeBlocks();
                    }
                }
            });
        }
        if(linkBlockArr.length == 0){
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
                        createAllImageBlocks();
                    }
                }
            });
        }
        if(youtubeBlockArr.length == 0){
            createAllImageBlocks();
        }
    }

    public void createAllImageBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < imageBlockArr.length; i++){
            String[] s = imageBlockArr[0].split("\uD83D\uDD70");
            File file = new File(s[0]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            int finalI = i;
            post.postDataCreateImageBlock("CreateImageBlock", s[1], requestBody, id, mail, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsImageBlocks.add(res);
                    if(finalI == imageBlockArr.length - 1){
                        createTask();
                    }
                }
            });
        }
        if(imageBlockArr.length == 0){
            createTask();
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
                idsLinkBlocksStr, String.join(",", idsArr), idsImageBlocksArr,
                idsYouTubeBlocksArr, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        Intent intent = new Intent(PartisipantTasks.this, TasksActivityMain.class);
                        intent.putExtra("projectId", id);
                        intent.putExtra("projectTitle", title);
                        intent.putExtra("projectUrlPhoto", prPhoto);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}