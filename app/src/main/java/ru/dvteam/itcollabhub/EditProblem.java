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
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.databinding.ActivityEditProblemBinding;

public class EditProblem extends AppCompatActivity {

    ActivityEditProblemBinding binding;

    private static final int PICK_IMAGES_CODE = 0;
    private String mediaPath = "";
    private Boolean acces = false, clicked = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);

        binding = ActivityEditProblemBinding.inflate(getLayoutInflater());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(binding.getRoot());

        registerResult();
        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        String problemPhoto = arguments.getString("problemPhoto");
        String projectTitle = arguments.getString("projectTitle");
        String projectPhoto = arguments.getString("projectUrlPhoto");
        String projectId = arguments.getString("projectId1");
        String problemId = arguments.getString("problemId");
        String problemName = arguments.getString("problemName");
        String problemDescription = arguments.getString("problemDescription");

        binding.nameProject.setText(projectTitle);
        Glide
                .with(EditProblem.this)
                .load(projectPhoto)
                .into(binding.prLogo);
        Glide
                .with(EditProblem.this)
                .load(problemPhoto)
                .into(binding.problemPhoto);
        binding.problemTitle.setHint(problemName);
        binding.problemDescription.setHint(problemDescription);

        PostDatas post = new PostDatas();
        binding.deleteProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.postDataDeleteProblem("DeleteProblem", problemId, mail, projectId, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        if(res.equals("Okey")){
                            finish();
                        }
                    }
                });
            }
        });
        binding.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                String description;
                if(binding.problemTitle.getText().toString().isEmpty()){
                    name = problemName;
                }else{
                    name = binding.problemTitle.getText().toString();
                }
                if(binding.problemDescription.getText().toString().isEmpty()){
                    description = problemDescription;
                }else{
                    description = binding.problemDescription.getText().toString();
                }


                if(mediaPath.isEmpty()){
                        post.postDataChangeProblemWithoutImage("ChangeProblemWithoutImage", name, description, projectId, mail,
                            problemId, new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    finish();
                                }
                            });
                }else{
                    File file = new File(mediaPath);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    post.postDataChangeProblem("ChangeProblem", name, requestBody, description, projectId, mail,
                            problemId, new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    Toast.makeText(EditProblem.this, "Изменения скоро вступят в силу", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
            }
        });

        if(Build.VERSION.SDK_INT >= 33) {
            binding.changePhoto.setOnClickListener(view -> pickImage());
        }
        else{
            binding.changePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(EditProblem.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProblem.this,
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
                Toast.makeText(EditProblem.this, "You loser", Toast.LENGTH_LONG).show();
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
                binding.problemPhoto.setImageURI(imageUri);
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
                            binding.problemPhoto.setImageURI(imageUri);
                            cursor.close();
                            acces = true;
                        }catch (Exception e){
                            Toast.makeText(EditProblem.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}