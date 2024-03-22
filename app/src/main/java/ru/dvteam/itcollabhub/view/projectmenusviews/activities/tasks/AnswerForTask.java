package ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.databinding.ActivityAnswerForTaskBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;

public class AnswerForTask extends AppCompatActivity {

    ActivityAnswerForTaskBinding binding;

    private String mail, id, title, prPhoto, taskTitle, taskId;
    private int cntImg = 0, cntImg1 = 0, cntLinks = 0, cntLinks1 = 0;

    private static final int PICK_IMAGES_CODE = 0;
    private Boolean enter = true;
    private ArrayList<String> mediaPaths, linkName, linkLink, imageNames, idsImageBlocks, idsLinkBlocks;
    private Boolean acces = false;
    private String text;
    private int score;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    private Boolean click = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityAnswerForTaskBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        registerResult();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);

        mediaPaths = new ArrayList<>();
        linkName = new ArrayList<>();
        imageNames = new ArrayList<>();
        linkLink = new ArrayList<>();

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

        if(android.os.Build.VERSION.SDK_INT >= 33) {
            binding.addImg.setOnClickListener(view -> pickImage());
        }
        else{
            binding.addImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cntImg < 7) {
                        if (ContextCompat.checkSelfPermission(AnswerForTask.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(AnswerForTask.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        } else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_PICK);
                            startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE);
                        }
                    }
                }
            });
        }

        binding.addLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cntLinks < 7 && !binding.linkTitle.getText().toString().isEmpty() && !binding.linkTitle.getText().toString().isEmpty()) {
                    View custom = getLayoutInflater().inflate(R.layout.photo_panel, null);
                    linkName.add(binding.linkTitle.getText().toString());
                    linkLink.add(binding.link.getText().toString());
                    //Toast.makeText(AnswerForTask.this, "", Toast.LENGTH_SHORT).show();
                    final int iko = cntLinks1;
                    custom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.main2.removeView(custom);
                            linkName.set(iko, "");
                            linkLink.set(iko, "");
                            for(int i = 0; i < binding.main2.getChildCount(); i++){
                                View lol = binding.main2.getChildAt(i);
                                TextView text = lol.findViewById(R.id.taskTitle);
                                String s = "Ссылка " + (i + 1);
                                text.setText(s);
                            }
                            cntLinks--;
                        }
                    });

                    cntLinks++;
                    cntLinks1++;
                    String s = "Ссылка " + (binding.main2.getChildCount() + 1);
                    TextView name = custom.findViewById(R.id.taskTitle);
                    name.setText(s);
                    binding.main2.addView(custom);
                    binding.linkTitle.setText("");
                    binding.link.setText("");
                }
            }
        });
        binding.uploadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click){
                    binding.load.setVisibility(View.VISIBLE);
                    binding.back.setVisibility(View.VISIBLE);
                    idsImageBlocks = new ArrayList<>();
                    idsLinkBlocks = new ArrayList<>();
                    click = false;
                    createTextBlock();
                }
            }
        });
    }

    private void createTextBlock(){
        if(binding.note.getText().toString().isEmpty()){
            Toast.makeText(this, "Нет заметки", Toast.LENGTH_SHORT).show();
            binding.load.setVisibility(View.GONE);
            binding.back.setVisibility(View.GONE);
        }else {
            PostDatas post = new PostDatas();
            post.postDataCreateStringBlock("CreateTextBlock", id, mail, binding.note.getText().toString(), new CallBackInt() {
                @Override
                public void invoke(String res) {
                    text = res;
                    Toast.makeText(AnswerForTask.this, text, Toast.LENGTH_SHORT).show();
                    createAllLinksBlocks();
                }
            });
        }
    }

    public void createAllImageBlocks(int i){
        PostDatas post = new PostDatas();
        if (cntImg == 0) {
            createWork();
        }else{
            if(!imageNames.get(i).equals("")) {
                File file = new File(mediaPaths.get(i));
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                int finalI2 = i;
                post.postDataCreateImageBlock("CreateImageBlock", imageNames.get(i), requestBody, id, mail, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        idsImageBlocks.add(res);
                        if (finalI2 == mediaPaths.size() - 1) {
                            Toast.makeText(AnswerForTask.this, idsImageBlocks.toString() + " " + finalI2 + " " + (mediaPaths.size() - 1), Toast.LENGTH_SHORT).show();
                            createWork();
                        } else {
                            createAllImageBlocks(finalI2 + 1);
                        }
                    }
                });
            }
        }
    }

    public void createAllLinksBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < linkName.size(); i++){
            int finalI = i;
            if(!linkName.get(i).equals("")) {
                //System.out.println(linkName.get(i));
                post.postDataCreateLinkBlock("CreateLinkBlock", id, mail, linkName.get(i), linkLink.get(i), new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        idsLinkBlocks.add(res);
                        if (finalI == linkName.size() - 1) {
                            Toast.makeText(AnswerForTask.this, idsLinkBlocks.toString(), Toast.LENGTH_SHORT).show();
                            createAllImageBlocks(0);
                        }
                    }
                });
            }
        }
        if(cntLinks == 0){
            createAllImageBlocks(0);
        }
    }

    private void createWork(){
        PostDatas post = new PostDatas();
        String idsLinkBlocksStr;
        String idsImageBlocksStr;
        if(idsLinkBlocks.isEmpty()){
            idsLinkBlocksStr = "";
        }else{
            idsLinkBlocksStr = String.join(",", idsLinkBlocks);
        }
        if(idsImageBlocks.isEmpty()){
            idsImageBlocksStr = "";
        }else{
            idsImageBlocksStr = String.join(",", idsImageBlocks);
        }
        post.postDataCreateWork("CreateWorkInTask", id, mail, taskId, text, idsLinkBlocksStr,
                idsImageBlocksStr, new CallBackInt() {
            @Override
            public void invoke(String res) {
                Toast.makeText(AnswerForTask.this, res, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void pickImage(){
        if(cntImg < 7){
            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            resultLauncher.launch(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE);
            } else {
                Toast.makeText(AnswerForTask.this, "You loser", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_CODE){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String mediaPath = cursor.getString(columnIndex);

                cntImg++;
                cntImg1++;

                mediaPaths.add(mediaPath);
                imageNames.add("Фото " + cntImg);

                View custom = getLayoutInflater().inflate(R.layout.photo_panel, null);

                final int iko = cntImg1 - 1;
                custom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.main1.removeView(custom);
                        imageNames.set(iko, "");
                        mediaPaths.set(iko, "");
                        for(int i = 0; i < binding.main1.getChildCount(); i++){
                            View lol = binding.main1.getChildAt(i);
                            TextView text = lol.findViewById(R.id.taskTitle);
                            String s = "Фото " + (i + 1);
                            text.setText(s);
                        }
                        cntImg--;
                    }
                });

                String s = "Фото " + (binding.main1.getChildCount() + 1);
                TextView name = custom.findViewById(R.id.taskTitle);
                name.setText(s);

                binding.main1.addView(custom);

                acces = true;
            }

        }
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try{
                            Uri imageUri = result.getData().getData();

                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                            assert cursor != null;
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String mediaPath = cursor.getString(columnIndex);

                            cntImg++;
                            cntImg1++;

                            mediaPaths.add(mediaPath);
                            imageNames.add("Фото " + cntImg);

                            View custom = getLayoutInflater().inflate(R.layout.photo_panel, null);

                            final int iko = cntImg1 - 1;
                            custom.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    binding.main1.removeView(custom);
                                    imageNames.set(iko, "");
                                    mediaPaths.set(iko, "");
                                    for(int i = 0; i < binding.main1.getChildCount(); i++){
                                        View lol = binding.main1.getChildAt(i);
                                        TextView text = lol.findViewById(R.id.taskTitle);
                                        String s = "Фото " + (i + 1);
                                        text.setText(s);
                                    }
                                    cntImg--;
                                }
                            });

                            String s = "Фото " + (binding.main1.getChildCount() + 1);
                            TextView name = custom.findViewById(R.id.taskTitle);
                            name.setText(s);

                            binding.main1.addView(custom);

                        }catch (Exception e){
                            Toast.makeText(AnswerForTask.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(AnswerForTask.this,R.color.blue));
            binding.addLink.setBackgroundTintList(ContextCompat.getColorStateList(AnswerForTask.this, R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(AnswerForTask.this,R.color.green));
            binding.addLink.setBackgroundTintList(ContextCompat.getColorStateList(AnswerForTask.this, R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(AnswerForTask.this,R.color.brown));
            binding.addLink.setBackgroundTintList(ContextCompat.getColorStateList(AnswerForTask.this, R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(AnswerForTask.this,R.color.light_gray));
            binding.addLink.setBackgroundTintList(ContextCompat.getColorStateList(AnswerForTask.this, R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(AnswerForTask.this,R.color.ohra));
            binding.addLink.setBackgroundTintList(ContextCompat.getColorStateList(AnswerForTask.this, R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(AnswerForTask.this,R.color.red));
            binding.addLink.setBackgroundTintList(ContextCompat.getColorStateList(AnswerForTask.this, R.color.red));
        }
        else if(score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(AnswerForTask.this, R.color.orange));
            binding.addLink.setBackgroundTintList(ContextCompat.getColorStateList(AnswerForTask.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(AnswerForTask.this,R.color.violete));
            binding.addLink.setBackgroundTintList(ContextCompat.getColorStateList(AnswerForTask.this, R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(AnswerForTask.this,R.color.main_green));
            binding.addLink.setBackgroundTintList(ContextCompat.getColorStateList(AnswerForTask.this, R.color.main_green));
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
    private void setButtonBackground(int score, View but){
        if(score < 100){
            but.setBackgroundResource(R.drawable.blue_line);
        }
        else if(score < 300){
            but.setBackgroundResource(R.drawable.green_line);
        }
        else if(score < 1000){
            but.setBackgroundResource(R.drawable.brown_line);
        }
        else if(score < 2500){
            but.setBackgroundResource(R.drawable.light_gray_line);
        }
        else if(score < 7000){
            but.setBackgroundResource(R.drawable.ohra_line);
        }
        else if(score < 17000){
            but.setBackgroundResource(R.drawable.red_line);
        }
        else if(score < 30000) {
            but.setBackgroundResource(R.drawable.brown_line);
        }
        else if(score < 50000){
            but.setBackgroundResource(R.drawable.violete_line);
        }
        else{
            but.setBackgroundResource(R.drawable.blue_green_line);
        }
    }

    public void setThemeActivity(){
        int themeType = UsersChosenTheme.getThemeNum();

        switch (themeType){
            case(1):
                setTheme(R.style.Theme_ITCollabHub_Blue);
                break;
            case(2):
                setTheme(R.style.Theme_ITCollabHub_Green);
                break;
            case(3):
                setTheme(R.style.Theme_ITCollabHub_Brown);
                break;
            case(4):
                setTheme(R.style.Theme_ITCollabHub_PinkGold);
                break;
            case(5):
                setTheme(R.style.Theme_ITCollabHub_Ohra);
                break;
            case(6):
                setTheme(R.style.Theme_ITCollabHub_Red);
                break;
            case(7):
                setTheme(R.style.Theme_ITCollabHub_Orange);
                break;
            case(8):
                setTheme(R.style.Theme_ITCollabHub_Violete);
                break;
            case(9):
                setTheme(R.style.Theme_ITCollabHub_BlueGreen);
                break;
        }

    }
}