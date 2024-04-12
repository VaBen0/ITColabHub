package ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.callbackclasses.CallBackTaskInfo;
import ru.dvteam.itcollabhub.databinding.ActivityTaskMenuBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class TaskMenuActivity extends AppCompatActivity {

    ActivityTaskMenuBinding binding;
    private String mail, id, title, prPhoto, taskTitle, taskId;
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityTaskMenuBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding.blockMenu.setVisibility(View.GONE);

        binding.blockMenu.setVisibility(View.VISIBLE);
        final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add2);
        binding.blockMenu.startAnimation(show);

        mail = sharedPreferences.getString("UserMail", "");

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        taskTitle = arguments.getString("taskTitle");
        taskId = arguments.getString("taskId");
        String completed = arguments.getString("isComplete");

        assert completed != null;
        if(completed.equals("1")){
            binding.view11.setBackgroundResource(R.drawable.green_line);
            binding.oneOfImportant.setVisibility(View.VISIBLE);
            binding.secondOfImportant.setVisibility(View.GONE);
        }

        binding.taskName.setText(taskTitle);
        binding.nameProject.setText(title);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.prLogo);

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
                            ImageView img2 = customImage.findViewById(R.id.chosen_img2);
                            imageName.setText(imageBlockNameArr[imageCnt]);
                            Glide
                                    .with(TaskMenuActivity.this)
                                    .load(imageBlockLinkArr[imageCnt])
                                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            img2.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
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

        System.out.println(id);

        binding.nextMenuBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskMenuActivity.this, AnswerForTask.class);
                intent.putExtra("projectId", id);
                intent.putExtra("projectTitle", title);
                intent.putExtra("projectUrlPhoto", prPhoto);
                intent.putExtra("taskTitle", taskTitle);
                intent.putExtra("taskId", taskId);
                startActivity(intent);
            }
        });

    }

    private void goToLink(String url){
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
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