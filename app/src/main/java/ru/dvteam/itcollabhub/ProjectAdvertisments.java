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
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.databinding.ActivityProjectAdvertismentsBinding;

public class ProjectAdvertisments extends AppCompatActivity {

    ActivityProjectAdvertismentsBinding binding;

    private static final int PICK_IMAGES_CODE = 0;
    private String mediaPath = "";
    private Boolean acces = false, clicked = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;

    private String projectTitle, photoProject, prId, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        binding = ActivityProjectAdvertismentsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        registerResult();

        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectAdvertisments.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectAdvertisments.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectAdvertisments.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectAdvertisments.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectAdvertisments.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectAdvertisments.this,R.color.red));
        }
        else if(score < 30000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectAdvertisments.this,R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectAdvertisments.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProjectAdvertisments.this,R.color.main_green));
        }


        /*Glide.with(this)
                .load("https://serveritcollabhub.development-team.ru/project_image/moaiitcollabhub.png")
                .into(binding.textView8);*/

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        String id = arguments.getString("projectId");
        prId = arguments.getString("projectId1");
        projectTitle = arguments.getString("projectTitle");
        photoProject = arguments.getString("projectUrlPhoto");



        binding.nameProject.setText(projectTitle);
        Glide
                .with(ProjectAdvertisments.this)
                .load(photoProject)
                .into(binding.prLogo);
        Glide
                .with(ProjectAdvertisments.this)
                .load(photoProject)
                .into(binding.fileImage);

        getAdvertIds();

        binding.addAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.advertName.getText().toString().isEmpty()){
                    Toast.makeText(ProjectAdvertisments.this, "Нет названия", Toast.LENGTH_SHORT).show();
                }
                else if(binding.advert.getText().toString().isEmpty()){
                    Toast.makeText(ProjectAdvertisments.this, "Нет описания", Toast.LENGTH_SHORT).show();
                }
                if(mediaPath.isEmpty()){
                    PostDatas post = new PostDatas();
                    post.postDataCreateAdvertWithoutImage("CreateAdWithoutImage", binding.advertName.getText().toString(),
                            binding.advert.getText().toString(), prId, mail, new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    binding.advertName.setText("");
                                    binding.advert.setText("");
                                    binding.advertsPlace.removeAllViews();
                                    getAdvertIds();
                                }
                            });
                }
                else{
                    PostDatas post = new PostDatas();
                    File file = new File(mediaPath);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    post.postDataCreateAdvert("CreateAd", binding.advertName.getText().toString(), requestBody,
                            binding.advert.getText().toString(), prId, mail, new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    mediaPath = "";
                                    binding.advertName.setText("");
                                    binding.advert.setText("");
                                    Glide
                                            .with(ProjectAdvertisments.this)
                                            .load(photoProject)
                                            .into(binding.fileImage);
                                    binding.advertsPlace.removeAllViews();
                                    getAdvertIds();
                                }
                            });
                }
            }
        });

        if(Build.VERSION.SDK_INT >= 33) {
            binding.addPhoto.setOnClickListener(view -> pickImage());
        }
        else{
            binding.addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(ProjectAdvertisments.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProjectAdvertisments.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_PICK);
                        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE);
                    }
                }
            });
        }
    }

    private void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
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
                Toast.makeText(ProjectAdvertisments.this, "You loser", Toast.LENGTH_LONG).show();
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
                mediaPath = cursor.getString(columnIndex);
                binding.fileImage.setImageURI(imageUri);
                cursor.close();
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
                            //imageUri.getPath();
                            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                            assert cursor != null;
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            mediaPath = cursor.getString(columnIndex);
                            binding.fileImage.setImageURI(imageUri);
                            cursor.close();
                            acces = true;
                        }catch (Exception e){
                            Toast.makeText(ProjectAdvertisments.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void getAdverts(String id1, String id2){
        PostDatas post = new PostDatas();
        post.postDataGetProjectAds("GetProjectAds", id1, new CallBackInt() {
            @Override
            public void invoke(String res) {
                String[] inf = res.split("\uD83D\uDD70");
                String[] idm = id1.split(",");
                for(int i = 0; i < inf.length; i += 3){
                    View custom = getLayoutInflater().inflate(R.layout.advertisment_panel, null);
                    ImageView loadImg = custom.findViewById(R.id.advertismentImage);
                    TextView name = custom.findViewById(R.id.fileName);
                    ImageView editBut = custom.findViewById(R.id.editBut);
                    ImageView deleteBut = custom.findViewById(R.id.deleteBut);

                    int finalI = i;

                    name.setText(inf[i]);
                    Glide
                            .with(ProjectAdvertisments.this)
                            .load(inf[i+2])
                            .into(loadImg);

                    custom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    editBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ProjectAdvertisments.this, EditAdvertisment.class);
                            intent.putExtra("problemPhoto", inf[finalI+2]);
                            intent.putExtra("projectTitle", projectTitle);
                            intent.putExtra("projectUrlPhoto", photoProject);
                            intent.putExtra("projectId1", prId);
                            intent.putExtra("problemName", inf[finalI]);
                            intent.putExtra("problemDescription", inf[finalI + 1]);
                            intent.putExtra("problemId", idm[finalI / 3]);
                            //Toast.makeText(ProjectAdvertisments.this, idm[finalI / 3] + " " + prId, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    });

                    deleteBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PostDatas post = new PostDatas();
                            post.postDataDeleteAd("DeleteAd", prId, mail, idm[finalI / 3], new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    binding.advertsPlace.removeView(custom);
                                }
                            });
                        }
                    });

                    binding.advertsPlace.addView(custom);
                }
            }
        });
        post.postDataGetProjectAds("GetProjectAds", id2, new CallBackInt() {
            @Override
            public void invoke(String res) {
                String[] inf = res.split("\uD83D\uDD70");
                String[] idm = id2.split(",");
                for(int i = 0; i < inf.length; i += 3){
                    View custom = getLayoutInflater().inflate(R.layout.advertisment_panel, null);
                    ImageView loadImg = custom.findViewById(R.id.advertismentImage);
                    TextView name = custom.findViewById(R.id.fileName);
                    ImageView editBut = custom.findViewById(R.id.editBut);
                    ImageView deleteBut = custom.findViewById(R.id.deleteBut);

                    int finalI = i;

                    name.setText(inf[i]);
                    Glide
                            .with(ProjectAdvertisments.this)
                            .load(inf[i+2])
                            .into(loadImg);

                    custom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    editBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ProjectAdvertisments.this, EditAdvertisment.class);
                            intent.putExtra("problemPhoto", inf[finalI+2]);
                            intent.putExtra("projectTitle", projectTitle);
                            intent.putExtra("projectUrlPhoto", photoProject);
                            intent.putExtra("projectId1", prId);
                            intent.putExtra("problemName", inf[finalI]);
                            intent.putExtra("problemDescription", inf[finalI + 1]);
                            intent.putExtra("problemId", idm[finalI / 3]);
                            //Toast.makeText(ProjectAdvertisments.this, idm[finalI / 3] + " " + prId, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    });

                    deleteBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PostDatas post = new PostDatas();
                            post.postDataDeleteAd("DeleteAd", prId, mail, idm[finalI / 3], new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    binding.advertsPlace.removeView(custom);
                                }
                            });
                        }
                    });

                    binding.advertsPlace.addView(custom);
                }
            }
        });
    }

    public void getAdvertIds(){
        PostDatas post = new PostDatas();
        post.postDataGetProjectAdsIds("GetProjectAdsIds", prId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                post.postDataGetProjectAdsIds("GetProjectAdsIds2", prId, new CallBackInt() {
                    @Override
                    public void invoke(String res2) {
                        getAdverts(res, res2);
                    }
                });
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.advertsPlace.removeAllViews();
        getAdvertIds();
    }
}