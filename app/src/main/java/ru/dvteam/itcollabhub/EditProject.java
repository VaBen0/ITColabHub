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
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.databinding.ActivityEditProjectBinding;

public class EditProject extends AppCompatActivity {

    ActivityEditProjectBinding binding;

    private NavController navController;
    String id, title, description, prPhoto, mail;
    int score;
    private static final int PICK_IMAGES_CODE = 0;
    private String mediaPath = "", uriPath = "";
    private Boolean acces = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding = ActivityEditProjectBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_create_project1);

        setContentView(binding.getRoot());
        registerResult();

        Bundle arguments = getIntent().getExtras();
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        if(score < 100){
            binding.linearParticipiant.setBackgroundColor(0);
            binding.linearEdit.setBackgroundResource(R.drawable.blue_line);
        }
        else if(score < 300){
            binding.linearParticipiant.setBackgroundColor(0);
            binding.linearEdit.setBackgroundResource(R.drawable.green_line);
        }
        else if(score < 1000){
            binding.linearParticipiant.setBackgroundColor(0);
            binding.linearEdit.setBackgroundResource(R.drawable.brown_line);
        }
        else if(score < 2500){
            binding.linearParticipiant.setBackgroundColor(0);
            binding.linearEdit.setBackgroundResource(R.drawable.light_gray_line);
        }
        else if(score < 7000){
            binding.linearParticipiant.setBackgroundColor(0);
            binding.linearEdit.setBackgroundResource(R.drawable.ohra_line);
        }
        else if(score < 17000){
            binding.linearParticipiant.setBackgroundColor(0);
            binding.linearEdit.setBackgroundResource(R.drawable.red_line);
        }
        else if(score < 30000){
            binding.linearParticipiant.setBackgroundColor(0);
            binding.linearEdit.setBackgroundResource(R.drawable.orange_line);
        }
        else if(score < 50000){
            binding.linearParticipiant.setBackgroundColor(0);
            binding.linearEdit.setBackgroundResource(R.drawable.violete_line);
        }
        else{
            binding.linearParticipiant.setBackgroundColor(0);
            binding.linearEdit.setBackgroundResource(R.drawable.blue_green_line);
        }
        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        description = arguments.getString("projectDescription");
        navController.navigate(R.id.editProjectFragment);

        binding.projectName.setText(title);
        Glide
                .with(EditProject.this)
                .load(prPhoto)
                .into(binding.prLogo);
        binding.tint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.editFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score < 100){
                    binding.linearParticipiant.setBackgroundColor(0);
                    binding.linearEdit.setBackgroundResource(R.drawable.blue_line);
                }
                else if(score < 300){
                    binding.linearParticipiant.setBackgroundColor(0);
                    binding.linearEdit.setBackgroundResource(R.drawable.green_line);
                }
                else if(score < 1000){
                    binding.linearParticipiant.setBackgroundColor(0);
                    binding.linearEdit.setBackgroundResource(R.drawable.brown_line);
                }
                else if(score < 2500){
                    binding.linearParticipiant.setBackgroundColor(0);
                    binding.linearEdit.setBackgroundResource(R.drawable.light_gray_line);
                }
                else if(score < 7000){
                    binding.linearParticipiant.setBackgroundColor(0);
                    binding.linearEdit.setBackgroundResource(R.drawable.ohra_line);
                }
                else if(score < 17000){
                    binding.linearParticipiant.setBackgroundColor(0);
                    binding.linearEdit.setBackgroundResource(R.drawable.red_line);
                }
                else if(score < 30000){
                    binding.linearParticipiant.setBackgroundColor(0);
                    binding.linearEdit.setBackgroundResource(R.drawable.orange_line);
                }
                else if(score < 50000){
                    binding.linearParticipiant.setBackgroundColor(0);
                    binding.linearEdit.setBackgroundResource(R.drawable.violete_line);
                }
                else{
                    binding.linearParticipiant.setBackgroundColor(0);
                    binding.linearEdit.setBackgroundResource(R.drawable.blue_green_line);
                }
                navController.navigate(R.id.editProjectFragment);
            }
        });
        binding.participiantFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score < 100){
                    binding.linearEdit.setBackgroundColor(0);
                    binding.linearParticipiant.setBackgroundResource(R.drawable.blue_line);
                }
                else if(score < 300){
                    binding.linearEdit.setBackgroundColor(0);
                    binding.linearParticipiant.setBackgroundResource(R.drawable.green_line);
                }
                else if(score < 1000){
                    binding.linearEdit.setBackgroundColor(0);
                    binding.linearParticipiant.setBackgroundResource(R.drawable.brown_line);
                }
                else if(score < 2500){
                    binding.linearEdit.setBackgroundColor(0);
                    binding.linearParticipiant.setBackgroundResource(R.drawable.light_gray_line);
                }
                else if(score < 7000){
                    binding.linearEdit.setBackgroundColor(0);
                    binding.linearParticipiant.setBackgroundResource(R.drawable.ohra_line);
                }
                else if(score < 17000){
                    binding.linearEdit.setBackgroundColor(0);
                    binding.linearParticipiant.setBackgroundResource(R.drawable.red_line);
                }
                else if(score < 30000){
                    binding.linearEdit.setBackgroundColor(0);
                    binding.linearParticipiant.setBackgroundResource(R.drawable.orange_line);
                }
                else if(score < 50000){
                    binding.linearEdit.setBackgroundColor(0);
                    binding.linearParticipiant.setBackgroundResource(R.drawable.violete_line);
                }
                else{
                    binding.linearEdit.setBackgroundColor(0);
                    binding.linearParticipiant.setBackgroundResource(R.drawable.blue_green_line);
                }
                navController.navigate(R.id.participantEditProject);
            }
        });

        if(Build.VERSION.SDK_INT >= 33) {
            binding.prLogo.setOnClickListener(view -> pickImage());
        }
        else{
            binding.prLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(EditProject.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProject.this,
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

    public void confirm(){
        binding.tint.setVisibility(View.VISIBLE);
        binding.panel.setVisibility(View.VISIBLE);
        binding.confirmationText.setVisibility(View.VISIBLE);
        binding.yesBut.setVisibility(View.VISIBLE);
        binding.noBut.setVisibility(View.VISIBLE);
        binding.projectName.clearFocus();
        binding.yesBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tint.setVisibility(View.GONE);
                binding.panel.setVisibility(View.GONE);
                binding.confirmationText.setVisibility(View.GONE);
                binding.yesBut.setVisibility(View.GONE);
                binding.noBut.setVisibility(View.GONE);
            }
        });
        binding.noBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tint.setVisibility(View.GONE);
                binding.panel.setVisibility(View.GONE);
                binding.confirmationText.setVisibility(View.GONE);
                binding.yesBut.setVisibility(View.GONE);
                binding.noBut.setVisibility(View.GONE);
            }
        });
    }
    public void saveChanges(String changedDescription, String tg, String vk, String web){
        PostDatas post = new PostDatas();
        String changedName = title;
        if(!binding.projectName.getText().toString().isEmpty()){
            changedName = binding.projectName.getText().toString();
        }
        if (mediaPath.isEmpty()) {
            post.postDataChangeProjectWithoutImage("CreateNewProject", changedName, changedDescription, id, mail, tg, vk, web, new CallBackInt() {
                        @Override
                        public void invoke(String res) {
                            Toast.makeText(EditProject.this, res, Toast.LENGTH_SHORT).show();
                            if (res.equals("Успешно")) {
                                Intent intent = new Intent(EditProject.this, ActivityProject.class);
                                startActivity(intent);
                            }
                        }
                    });
        } else {
            File file = new File(mediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            post.postDataChangeProject("CreateNewProject", changedName, requestBody, id, mail,
                    changedDescription, tg, vk, web, new CallBackInt() {
                        @Override
                        public void invoke(String res) {
                            Toast.makeText(EditProject.this, res, Toast.LENGTH_SHORT).show();
                            if (res.equals("Успешно")) {
                                Intent intent = new Intent(EditProject.this, ActivityProject.class);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }

    public String getDescription(){
        return description;
    }
    public int getScore(){return score;}
    public String getMail(){return mail;}

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
                Toast.makeText(EditProject.this, "You loser", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_CODE){

            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uriPath = imageUri.toString();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                binding.prLogo.setImageURI(imageUri);
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
                            binding.prLogo.setImageURI(imageUri);
                            cursor.close();
                            acces = true;
                        }catch (Exception e){
                            Toast.makeText(EditProject.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}