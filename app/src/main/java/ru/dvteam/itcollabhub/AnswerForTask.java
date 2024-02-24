package ru.dvteam.itcollabhub;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.databinding.ActivityAnswerForTaskBinding;

public class AnswerForTask extends AppCompatActivity {

    ActivityAnswerForTaskBinding binding;

    private String mail, id, title, prPhoto, taskTitle, taskId;
    private int cntImg = 0, cntLinks = 0;

    private static final int PICK_IMAGES_CODE = 0;
    private Boolean enter = true;
    private ArrayList<String> mediaPaths, linkName, linkLink, imageNames, idsImageBlocks, idsLinkBlocks;
    private Boolean acces = false;
    private String text;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAnswerForTaskBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        registerResult();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");

        mediaPaths = new ArrayList<>();
        linkName = new ArrayList<>();
        imageNames = new ArrayList<>();
        linkLink = new ArrayList<>();
        idsImageBlocks = new ArrayList<>();
        idsLinkBlocks = new ArrayList<>();

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

        if(android.os.Build.VERSION.SDK_INT >= 33) {
            binding.addImg.setOnClickListener(view -> pickImage());
        }
        else{
            binding.addImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cntImg <= 7) {
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
                if(cntLinks <= 7) {
                    View custom = getLayoutInflater().inflate(R.layout.photo_panel, null);
                    linkName.add(binding.linkTitle.getText().toString());
                    linkLink.add(binding.link.getText().toString());
                    binding.main2.addView(custom);
                    binding.linkTitle.setText("");
                    binding.link.setText("");
                    cntLinks++;
                }
            }
        });
        binding.uploadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AnswerForTask.this, mediaPaths.size()+" ", Toast.LENGTH_SHORT).show();
                createTextBlock();
            }
        });
    }

    private void createTextBlock(){
        if(binding.note.getText().toString().isEmpty()){
            Toast.makeText(this, "Нет заметки", Toast.LENGTH_SHORT).show();
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

    public void createAllImageBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < mediaPaths.size(); i++){
            File file = new File(mediaPaths.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            int finalI = i;
            post.postDataCreateImageBlock("CreateImageBlock", imageNames.get(i), requestBody, id, mail, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsImageBlocks.add(res);
                    if(finalI == mediaPaths.size() - 1){
                        Toast.makeText(AnswerForTask.this, idsImageBlocks.toString(), Toast.LENGTH_SHORT).show();
                        createWork();
                    }
                }
            });
        }
        if(linkName.size() == 0){
            createWork();
        }
    }

    public void createAllLinksBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < linkName.size(); i++){
            int finalI = i;
            post.postDataCreateLinkBlock("CreateLinkBlock", id, mail, linkName.get(i), linkLink.get(i), new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsLinkBlocks.add(res);
                    if(finalI == linkName.size() - 1){
                        Toast.makeText(AnswerForTask.this, idsLinkBlocks.toString(), Toast.LENGTH_SHORT).show();
                        createAllImageBlocks();
                    }
                }
            });
        }
        if(linkName.size() == 0){
            createAllImageBlocks();
        }
    }

    private void createWork(){
        PostDatas post = new PostDatas();
        post.postDataCreateWork("CreateWork", id, mail, taskId, text, String.join(",", idsLinkBlocks),
                String.join(",", idsImageBlocks), new CallBackInt() {
            @Override
            public void invoke(String res) {
                Toast.makeText(AnswerForTask.this, res, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickImage(){
        if(cntImg <= 7){
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

                mediaPaths.add(mediaPath);
                imageNames.add("Фото" + cntImg);
                cntImg++;

                View custom = getLayoutInflater().inflate(R.layout.photo_panel, null);

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

                            mediaPaths.add(mediaPath);
                            imageNames.add("Фото" + cntImg);
                            cntImg++;

                            View custom = getLayoutInflater().inflate(R.layout.photo_panel, null);

                            binding.main1.addView(custom);

                        }catch (Exception e){
                            Toast.makeText(AnswerForTask.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}