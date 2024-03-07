package ru.dvteam.itcollabhub;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.bumptech.glide.Glide;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.databinding.ActivityPurposeLeadBinding;

public class Purpose extends AppCompatActivity {

    ActivityPurposeLeadBinding binding;

    FragmentTransaction transaction;

    private NavController navController;
    private EditText description;
    private boolean clicked = false;
    private static final int PICK_IMAGES_CODE = 0;
    private String mediaPath = "";
    private Boolean acces = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    int score;

    String prId, mail;

    int countPurposes = 0, countTicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        binding = ActivityPurposeLeadBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        registerResult();

        binding.blockMenu.setVisibility(View.GONE);

        binding.blockMenu.setVisibility(View.VISIBLE);
        final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add2);
        binding.blockMenu.startAnimation(show);

        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(Purpose.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(Purpose.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(Purpose.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(Purpose.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(Purpose.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(Purpose.this,R.color.red));
        }
        else if(score < 30000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(Purpose.this,R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(Purpose.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(Purpose.this,R.color.main_green));
        }

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        String id = arguments.getString("projectId");
        prId = arguments.getString("projectId1");
        String projectTitle = arguments.getString("projectTitle");
        String photoProject = arguments.getString("projectUrlPhoto");

        postPurpose();

        binding.nameProject.setText(projectTitle);
        Glide
                .with(Purpose.this)
                .load(photoProject)
                .into(binding.prLogo);
        Glide
                .with(Purpose.this)
                .load(photoProject)
                .into(binding.mainImagePurp);

        binding.addPurp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countPurposes >= 3){
                    Toast.makeText(Purpose.this, "Вы достигли предела", Toast.LENGTH_SHORT).show();
                }
                else if(binding.name.getText().toString().isEmpty()){
                    Toast.makeText(Purpose.this, "Нет названия", Toast.LENGTH_SHORT).show();
                }
                else if(binding.description1.getText().toString().isEmpty()){
                    Toast.makeText(Purpose.this, "Нет описания", Toast.LENGTH_SHORT).show();
                }
                else if (mediaPath.isEmpty()) {
                    PostDatas post = new PostDatas();
                    countPurposes += 1;
                    post.postDataCreatePurposeWithoutImage("CreatePurposeWithoutImage", binding.name.getText().toString(),
                        binding.description1.getText().toString(), prId, mail, new CallBackInt() {
                            @Override
                            public void invoke(String res) {
                                binding.name.setText("");
                                binding.description1.setText("");
                                countTicked = 0;
                                binding.reminderPlace.removeAllViews();
                                postPurpose();
                            }
                        });
                } else{
                    File file = new File(mediaPath);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    PostDatas post = new PostDatas();
                    post.postDataCreatePurpose("CreatePurpose", binding.name.getText().toString(), requestBody,
                        binding.description1.getText().toString(), mail, prId, new CallBackInt() {
                            @Override
                            public void invoke(String res) {
                                mediaPath = "";
                                countTicked = 0;
                                Glide
                                        .with(Purpose.this)
                                        .load(photoProject)
                                        .into(binding.mainImagePurp);
                                binding.name.setText("");
                                binding.description1.setText("");
                                binding.reminderPlace.removeAllViews();
                                postPurpose();
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
                    if (ContextCompat.checkSelfPermission(Purpose.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Purpose.this,
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
                Toast.makeText(Purpose.this, "You loser", Toast.LENGTH_LONG).show();
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
                binding.mainImagePurp.setImageURI(imageUri);
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

                            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                            assert cursor != null;
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            mediaPath = cursor.getString(columnIndex);
                            binding.mainImagePurp.setImageURI(imageUri);
                            cursor.close();
                            acces = true;
                        }catch (Exception e){
                            Toast.makeText(Purpose.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void getPurposes(String id){
        PostDatas post = new PostDatas();
        post.postDataGetPurpose("GetPurposes", id, new CallBackInt() {
            @Override
            public void invoke(String res) {
                String[] inf = res.split("\uD83d\uDD70");
                assert id != null;
                String[] idm = id.split(",");
                countPurposes = idm.length;
                for(int i = 0; i < inf.length; i += 4){
                    View custom = getLayoutInflater().inflate(R.layout.purpose_panel, null);
                    ImageView loadImg = custom.findViewById(R.id.imagePurp);
                    TextView name = custom.findViewById(R.id.name);
                    TextView descr = custom.findViewById(R.id.description1);
                    TextView title = custom.findViewById(R.id.problemTitlePanel);
                    View back = custom.findViewById(R.id.view8);
                    LinearLayout yesOrNo = custom.findViewById(R.id.yes_or_no);
                    LinearLayout descript = custom.findViewById(R.id.description_purpose);
                    Button yes = custom.findViewById(R.id.yes);
                    Button no = custom.findViewById(R.id.no);

                    if(inf[i + 2].equals("1")){
                        back.setBackgroundResource(R.drawable.green_transperent);
                        countTicked += 1;
                    }
                    int finalI = i;
                    custom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(countTicked >= idm.length - 1 && inf[finalI + 2].equals("0")){
                                Toast.makeText(Purpose.this, "Эту цель нельзя отметить выполненной", Toast.LENGTH_SHORT).show();
                            }
                            else if(!clicked && inf[finalI + 2].equals("0")) {
                                back.setBackgroundResource(R.drawable.progress_panel_background2);
                                descript.setVisibility(View.GONE);
                                yesOrNo.setVisibility(View.VISIBLE);
                                clicked = true;
                            }
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            post.postDatasetPurposeIsEnd("SetPurposeComplete", idm[finalI / 4], prId, mail, new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    back.setBackgroundResource(R.drawable.green_transperent);
                                    descript.setVisibility(View.VISIBLE);
                                    yesOrNo.setVisibility(View.GONE);
                                    clicked = false;
                                    countTicked += 1;
                                }
                            });
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            back.setBackgroundResource(R.drawable.progress_panel_background);
                            descript.setVisibility(View.VISIBLE);
                            yesOrNo.setVisibility(View.GONE);
                            clicked = false;
                        }
                    });

                    title.setText(inf[i]);
                    name.setText(inf[i]);
                    descr.setText(inf[i+1]);
                    Glide
                            .with(Purpose.this)
                            .load(inf[i+3])
                            .into(loadImg);
                    binding.reminderPlace.addView(custom, 0);
                }
            }
        });
    }
    private void postPurpose(){
        PostDatas postDatas = new PostDatas();
        postDatas.postDataGetProjectPurposes("GetProjectPurposesIDs", prId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                getPurposes(res);
            }
        });
    }

}