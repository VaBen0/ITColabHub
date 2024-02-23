package ru.dvteam.itcollabhub;

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

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreateProject extends AppCompatActivity {
    private ImageView Img;
    private NavController navController;
    private EditText description;
    private static final int PICK_IMAGES_CODE = 0;
    private String purposes_name = "", purposes = "", tasks_name = "", tasks = "";
    private String id1 = "";
    private String mediaPath = "", uriPath = "";
    private Boolean acces = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_create_project1);
        registerResult();

        Img = findViewById(R.id.pr_logo);
        EditText nameu = findViewById(R.id.projectName);
        EditText description  = findViewById(R.id.description);
        Button sendProject = findViewById(R.id.send);

        if(score < 100){
            sendProject.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject.this, R.color.blue));
        }
        else if(score < 300){
            sendProject.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject.this, R.color.green));
        }
        else if(score < 1000){
            sendProject.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject.this, R.color.brown));
        }
        else if(score < 2500){
            sendProject.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject.this, R.color.light_gray));
        }
        else if(score < 7000){
            sendProject.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject.this, R.color.ohra));
        }
        else if(score < 17000){
            sendProject.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject.this, R.color.red));
        }
        else if(score < 30000){
            sendProject.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject.this, R.color.orange));
        }
        else if(score < 50000){
            sendProject.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject.this, R.color.violete));
        }
        else{
            sendProject.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject.this, R.color.main_green));
        }

        if(Build.VERSION.SDK_INT >= 33) {
            Img.setOnClickListener(view -> pickImage());
        }
        else{
            Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(CreateProject.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CreateProject.this,
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
        sendProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainDescription;
                String mainName;

                if (nameu.getText().toString().isEmpty()) {
                    mainName = "Отсутствует наименование проекта";
                } else {
                    mainName = nameu.getText().toString();
                }
                if (description.getText().toString().isEmpty()) {
                    mainDescription = "Без названия";
                } else {
                    mainDescription = description.getText().toString();
                }
                Intent intent = new Intent(CreateProject.this, CreateProject2.class);
                intent.putExtra("uriPath", uriPath);
                intent.putExtra("mediaPath", mediaPath);
                intent.putExtra("title", mainName);
                intent.putExtra("prDescription", mainDescription);
                startActivity(intent);

            }
        });
    }

    public void purpose(){
        NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment2);
        navController2.navigate(R.id.clop1);
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
                Toast.makeText(CreateProject.this, "You loser", Toast.LENGTH_LONG).show();
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
                Img.setImageURI(imageUri);
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
                            Img.setImageURI(imageUri);
                            cursor.close();
                            acces = true;
                        }catch (Exception e){
                            Toast.makeText(CreateProject.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public int getScore(){
        return score;
    }
}