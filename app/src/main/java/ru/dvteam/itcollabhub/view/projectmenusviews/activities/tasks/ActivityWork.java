package ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import ru.dvteam.itcollabhub.callbackclasses.CallBackWorkInfo;
import ru.dvteam.itcollabhub.databinding.ActivityWorkBinding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;

public class ActivityWork extends AppCompatActivity {

    ActivityWorkBinding binding;
    private String mail, id, title, prPhoto, taskTitle, workId;
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityWorkBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);

        mail = sharedPreferences.getString("UserMail", "");

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        taskTitle = arguments.getString("taskTitle");
        workId = arguments.getString("workId");
        String userName = arguments.getString("userName");
        String userPhoto = arguments.getString("userPhoto");

        binding.textView3.setText(userName);
        Glide
                .with(ActivityWork.this)
                .load(userPhoto)
                .into(binding.log);
        binding.nameProject.setText(title);
        binding.taskName.setText(taskTitle);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.prLogo);

        System.out.println(id);


        PostDatas post = new PostDatas();
        post.postDataGetWork("GetMoreInformationFromWorkFromTask", id, mail, workId, new CallBackWorkInfo() {
            @Override
            public void invoke(String text_block, String link_block_link, String link_block_name, String image_block_link, String image_block_name) {

                View textBlock = getLayoutInflater().inflate(R.layout.gblock_text2, null);
                TextView text = textBlock.findViewById(R.id.text);
                text.setText(text_block);
                binding.main.addView(textBlock);

                String[] linkBlockArr = link_block_link.split("\uD83D\uDD70");
                String[] linkBlockNameArr = link_block_link.split("\uD83D\uDD70");
                String[] imageBlockArr = image_block_link.split("\uD83D\uDD70");
                String[] imageBlockNameArr = image_block_name.split("\uD83D\uDD70");
                if (!linkBlockNameArr[0].equals("")) {
                    for (int i = 0; i < linkBlockArr.length; i++) {
                        View linkBlock = getLayoutInflater().inflate(R.layout.gblock_link2, null);
                        TextView link = linkBlock.findViewById(R.id.name_link);
                        link.setText(linkBlockNameArr[i]);
                        int finalI = i;
                        linkBlock.setOnClickListener(view -> goToLink(linkBlockArr[finalI]));
                        binding.main.addView(linkBlock);
                    }
                }
                if (!imageBlockNameArr[0].equals("")) {
                    for (int i = 0; i < imageBlockNameArr.length; i++) {
                        View imageBlock = getLayoutInflater().inflate(R.layout.gblock_image2, null);
                        TextView name = imageBlock.findViewById(R.id.title);
                        name.setText(imageBlockNameArr[i]);
                        ImageView img = imageBlock.findViewById(R.id.chosen_img);
                        ImageView img2 = imageBlock.findViewById(R.id.chosen_img2);
                        Glide
                                .with(ActivityWork.this)
                                .load(imageBlockArr[i])
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
                        binding.main.addView(imageBlock);
                    }
                }
            }
        });

    }

    private void goToLink(String url){
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityWork.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityWork.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityWork.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityWork.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityWork.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityWork.this,R.color.red));
        }
        else if(score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityWork.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityWork.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ActivityWork.this,R.color.main_green));
        }
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