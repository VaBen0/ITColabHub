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
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.databinding.ActivityProblemsBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class Problems extends AppCompatActivity {

    ActivityProblemsBinding binding;
    int countProblems = 0, countTicked = 0;
    private static final int PICK_IMAGES_CODE = 0;
    private String mediaPath = "";
    private Boolean acces = false, clicked = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;

    String projectTitle, photoProject, prId, mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        binding = ActivityProblemsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        registerResult();

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(Problems.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        binding.blockMenu.setVisibility(View.GONE);

        binding.blockMenu.setVisibility(View.VISIBLE);
        final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add2);
        binding.blockMenu.startAnimation(show);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        String id = arguments.getString("projectId");
        prId = arguments.getString("projectId1");
        projectTitle = arguments.getString("projectTitle");
        photoProject = arguments.getString("projectUrlPhoto");

        postProblems();

        binding.nameProject.setText(projectTitle);
        Glide
                .with(Problems.this)
                .load(photoProject)
                .into(binding.prLogo);
        Glide
                .with(Problems.this)
                .load(photoProject)
                .into(binding.problemImage);


        binding.addProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countProblems == 30){
                    Toast.makeText(Problems.this, "Вы достигли предела", Toast.LENGTH_SHORT).show();
                }
                else if(binding.name.getText().toString().isEmpty()){
                    Toast.makeText(Problems.this, "Нет названия", Toast.LENGTH_SHORT).show();
                }
                else if(binding.description1.getText().toString().isEmpty()){
                    Toast.makeText(Problems.this, "Нет описания", Toast.LENGTH_SHORT).show();
                }
                else if (mediaPath.isEmpty()) {
                    PostDatas post = new PostDatas();
                    post.postDataCreateProblemWithoutImage("CreateProblemWithoutImage", binding.name.getText().toString(),
                            binding.description1.getText().toString(), prId, mail, new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    countTicked = 0;
                                    binding.name.setText("");
                                    binding.description1.setText("");
                                    binding.reminderPlace.removeAllViews();
                                    postProblems();
                                }
                            });
                } else{
                    File file = new File(mediaPath);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    PostDatas post = new PostDatas();
                    post.postDataCreateProblem("CreateProblem", binding.name.getText().toString(), requestBody,
                        binding.description1.getText().toString(), prId, mail, new CallBackInt() {
                            @Override
                            public void invoke(String res) {
                                mediaPath = "";
                                Glide
                                        .with(Problems.this)
                                        .load(photoProject)
                                        .into(binding.problemImage);
                                binding.name.setText("");
                                binding.description1.setText("");
                                binding.reminderPlace.removeAllViews();
                                countTicked = 0;
                                postProblems();
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
                    if (ContextCompat.checkSelfPermission(Problems.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Problems.this,
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
                Toast.makeText(Problems.this, "You loser", Toast.LENGTH_LONG).show();
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
                binding.problemImage.setImageURI(imageUri);
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
                            binding.problemImage.setImageURI(imageUri);
                            cursor.close();
                            acces = true;
                        }catch (Exception e){
                            Toast.makeText(Problems.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }



    public void getProblems(String id3){
        PostDatas post = new PostDatas();
        post.postDataGetProblems("GetProblems", id3, new CallBackInt() {
            @Override
            public void invoke(String res) {
                String[] inf = res.split("\uD83D\uDD70");
                assert id3 != null;
                String[] idm = id3.split(",");
                for(int i = 0; i < inf.length; i += 4){
                    View custom = getLayoutInflater().inflate(R.layout.problem_panel, null);
                    ImageView loadImg = custom.findViewById(R.id.problemImage);
                    TextView name = custom.findViewById(R.id.problemName);
                    TextView descr = custom.findViewById(R.id.problemDescription);
                    TextView title = custom.findViewById(R.id.problemTitlePanel);
                    View back = custom.findViewById(R.id.view8);
                    LinearLayout yesOrNo = custom.findViewById(R.id.yes_or_no);
                    LinearLayout descript = custom.findViewById(R.id.description_purpose);
                    Button yes = custom.findViewById(R.id.yes);
                    Button no = custom.findViewById(R.id.no);
                    ImageView editBut = custom.findViewById(R.id.editProblem);

                    if(inf[i + 2].equals("1")){
                        back.setBackgroundResource(R.drawable.green_transperent);
                        countTicked += 1;
                    }
                    int finalI = i;

                    title.setText(inf[i]);
                    name.setText(inf[i]);
                    descr.setText(inf[i+1]);
                    Glide
                            .with(Problems.this)
                            .load(inf[i+3])
                            .into(loadImg);

                    custom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(countTicked >= idm.length - 1 && inf[finalI + 2].equals("0")){
                                Toast.makeText(Problems.this, "Эту задачу нельзя отметить выполненной", Toast.LENGTH_SHORT).show();
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
                            post.postDatasetProblemIsEnd("SetProblemComplete", idm[finalI / 4], prId, mail, new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    back.setBackgroundResource(R.drawable.progress_panel_background);
                                    descript.setVisibility(View.VISIBLE);
                                    yesOrNo.setVisibility(View.GONE);
                                    clicked = false;
                                    back.setBackgroundResource(R.drawable.green_transperent);
                                    inf[finalI + 2] = "1";
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

                    editBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Problems.this, EditProblem.class);
                            intent.putExtra("problemPhoto", inf[finalI+3]);
                            intent.putExtra("projectTitle", projectTitle);
                            intent.putExtra("projectUrlPhoto", photoProject);
                            intent.putExtra("projectId1", prId);
                            intent.putExtra("problemName", inf[finalI]);
                            intent.putExtra("problemDescription", inf[finalI + 1]);
                            intent.putExtra("problemId", idm[finalI / 4]);
                            startActivity(intent);
                        }
                    });

                    countProblems++;
                    binding.reminderPlace.addView(custom, 0);
                }
            }
        });
    }

    private void postProblems(){
        PostDatas postDatas = new PostDatas();
        postDatas.postDataGetProjectProblems("GetProjectProblemsIDs", prId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                getProblems(res);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.reminderPlace.removeAllViews();
        postProblems();
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