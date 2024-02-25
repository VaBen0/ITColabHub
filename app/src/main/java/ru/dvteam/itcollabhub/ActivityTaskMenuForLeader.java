package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import ru.dvteam.itcollabhub.databinding.ActivityTaskMenuForLeaderBinding;

public class ActivityTaskMenuForLeader extends AppCompatActivity {

    ActivityTaskMenuForLeaderBinding binding;
    private String mail, id, title, prPhoto, taskTitle, taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaskMenuForLeaderBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        taskTitle = arguments.getString("taskTitle");
        taskId = arguments.getString("taskId");

        binding.taskName.setText(taskTitle);
        binding.nameProject.setText(title);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.prLogo);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.advertPhoto);

        PostDatas post = new PostDatas();
        post.postDataGetMoreInfoTask("GetMoreInformationFromTaskFromProject", id, mail, taskId, new CallBackTaskInfo() {
            @Override
            public void invoke(String queue, String textBlock, String youtubeBlockName,
                               String youtubeBlockLink, String linkBlockName, String linkBlockLink,
                               String imageBlockName, String imageBlockLink) {

                String[] queueArr = queue.split(",");
                String[] textBlockArr = textBlock.split("\uD83D\uDD70");
                String[] youtubeBlockNameArr = youtubeBlockName.split("\uD83D\uDD70");
                String[] youtubeBlockLinkArr = youtubeBlockLink.split("\uD83D\uDD70");
                String[] linkBlockNameArr = linkBlockName.split("\uD83D\uDD70");
                String[] linkBlockLinkArr = linkBlockLink.split("\uD83D\uDD70");
                String[] imageBlockNameArr = imageBlockName.split("\uD83D\uDD70");
                String[] imageBlockLinkArr = imageBlockLink.split("\uD83D\uDD70");

                int textCnt = 0, youtubeCnt = 0, linkCnt = 0, imageCnt = 0;

                for(int i = 0; i < queueArr.length; i++){
                    int finalI = i;
                    switch (Integer.parseInt(queueArr[i])){
                        case(1):
                            View customText = getLayoutInflater().inflate(R.layout.gblock_text2, null);
                            TextView text = customText.findViewById(R.id.text);
                            text.setText(textBlockArr[textCnt]);
                            binding.main.addView(customText);
                            textCnt++;
                            break;
                        case(2):
                            View customLink = getLayoutInflater().inflate(R.layout.gblock_link2, null);
                            TextView textLink = customLink.findViewById(R.id.name_link);

                            SpannableString content = new SpannableString(linkBlockNameArr[linkCnt]);
                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                            textLink.setText(content);

                            int finalLinkCnt = linkCnt;
                            textLink.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goToLink(linkBlockLinkArr[finalLinkCnt]);
                                }
                            });
                            binding.main.addView(customLink);
                            linkCnt++;
                            break;
                        case(3):
                            View customImage = getLayoutInflater().inflate(R.layout.gblock_image2, null);
                            TextView imageName = customImage.findViewById(R.id.title);
                            ImageView img = customImage.findViewById(R.id.chosen_img);
                            imageName.setText(imageBlockNameArr[imageCnt]);
                            Glide
                                    .with(ActivityTaskMenuForLeader.this)
                                    .load(imageBlockLinkArr[imageCnt])
                                    .into(img);
                            binding.main.addView(customImage);
                            imageCnt++;
                            break;
                        case(4):
                            View customLinkYoutube = getLayoutInflater().inflate(R.layout.gblock_youtube_link2, null);
                            TextView textLink2 = customLinkYoutube.findViewById(R.id.name_link);

                            SpannableString content2 = new SpannableString(youtubeBlockNameArr[youtubeCnt]);
                            content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
                            textLink2.setText(content2);

                            int finalYoutubeCnt = youtubeCnt;
                            textLink2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goToLink(youtubeBlockLinkArr[finalYoutubeCnt]);
                                }
                            });
                            youtubeCnt++;
                            binding.main.addView(customLinkYoutube);
                            break;
                    }
                }
                View custom = getLayoutInflater().inflate(R.layout.emty_obj, null);
                binding.main.addView(custom);
            }
        });

        /*binding.nextMenuBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTaskMenuForLeader.this, AnswerForTask.class);
                intent.putExtra("projectId", id);
                intent.putExtra("projectTitle", title);
                intent.putExtra("projectUrlPhoto", prPhoto);
                intent.putExtra("taskTitle", taskTitle);
                intent.putExtra("taskId", taskId);
                startActivity(intent);
            }
        });*/

    }

    private void goToLink(String url){
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}