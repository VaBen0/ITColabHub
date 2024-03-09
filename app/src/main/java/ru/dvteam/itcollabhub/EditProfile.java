package ru.dvteam.itcollabhub;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class EditProfile extends AppCompatActivity {

    ImageView Img;
    private static final int PICK_IMAGES_CODE = 0;
    private String mediaPath = "";
    private int selectedColor, score;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    private String mail, name;
    private String[] wow = {"Хренос 2", "Кина не будет - электричество кончилось", "Ой, сломалось", "Караул!"};
    View back;
    ImageView dontWork;
    EditText UserName;
    Fragment frgmentAccountLinks, fragmentAboutApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        mail = sPref.getString("UserMail", "");
        name = sPref.getString("UserName", "");
        score = sPref.getInt("UserScore", 0);
        String imgUrl = sPref.getString("UrlImg", "");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        registerResult();

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(EditProfile.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        Img = findViewById(R.id.loadImg);
        UserName = findViewById(R.id.nameu);
        String s = "Ваши очки: " + score;
        ImageView userCircle = findViewById(R.id.userCircle);
        ImageView bguser = findViewById(R.id.bguser);
        TextView UserScore = findViewById(R.id.score);
        ImageView restartLine = findViewById(R.id.restart);
        TextView quitProfile = findViewById(R.id.quit);
        TextView aboApp = findViewById(R.id.second_menu);
        TextView links = findViewById(R.id.first_menu);
        View about = findViewById(R.id.linear_friends);
        View link = findViewById(R.id.linear_projects);
        UserName.setText(name);
        UserScore.setText(s);
        back = findViewById(R.id.view3);
        dontWork = findViewById(R.id.imageView12);

        about.setVisibility(View.INVISIBLE);
        link.setVisibility(View.VISIBLE);

        fragmentAboutApp = Fragment.instantiate(this, AboutApp.class.getName());
        frgmentAccountLinks = Fragment.instantiate(this, AccountLinks.class.getName());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.editProfileFragmentContainer, frgmentAccountLinks)
                .commit();

        Glide
                .with(EditProfile.this)
                .load(imgUrl)
                .into(Img);

        restartLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getIntent());
                finish();
            }
        });

        quitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("UserReg", "false");
                ed.putString("UserMail", "");
                ed.putInt("UserScore", 0);
                ed.putString("UrlImg", "");
                ed.apply();
                startActivity(intent);
                finish();
            }
        });

        if(android.os.Build.VERSION.SDK_INT >= 33) {
            Img.setOnClickListener(view -> pickImage());
        }
        else{
            Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(EditProfile.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfile.this,
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

        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about.setVisibility(View.INVISIBLE);
                link.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.editProfileFragmentContainer, frgmentAccountLinks)
                        .commit();
            }
        });

        aboApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link.setVisibility(View.INVISIBLE);
                about.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.editProfileFragmentContainer, fragmentAboutApp)
                        .commit();
            }
        });
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
                Toast.makeText(EditProfile.this, "You loser", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Button btn = findViewById(R.id.saveBut);
        if (requestCode == PICK_IMAGES_CODE){

            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                Img.setImageURI(imageUri);
                cursor.close();
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
                        }catch (Exception e){
                            Toast.makeText(EditProfile.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void editProfile(String vk, String tg, String web){
        EditText UserName = findViewById(R.id.nameu);
        PostDatas post = new PostDatas();
        if(mediaPath.isEmpty()){
            post.postDataEditProfileWithoutImage("CreateNameLog1", mail, UserName.getText().toString(), tg, vk, web, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    Toast.makeText(EditProfile.this, res, Toast.LENGTH_SHORT).show();
                    if(res.equals("Сохранено")) {
                        Intent intent = new Intent(EditProfile.this, Profile.class);

                        startActivity(intent);
                    }
                }
            });
        }
        else{
            File file = new File(mediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            post.postDataEditProfile("CreateNameLog1", UserName.getText().toString(), tg, vk, web, requestBody, mail, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    Toast.makeText(EditProfile.this, res, Toast.LENGTH_SHORT).show();
                    if(res.equals("Сохранено")) {
                        Intent intent = new Intent(EditProfile.this, Profile.class);

                        startActivity(intent);
                    }
                }
            });
        }

    }

    public String getMail(){
        return mail;
    }
    public int getScore(){
        return score;
    }
    public void error(){
        back.setVisibility(View.VISIBLE);
        dontWork.setVisibility(View.VISIBLE);
        Toast.makeText(EditProfile.this, wow[(int) (Math.random() * 4)], Toast.LENGTH_SHORT).show();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        back.setVisibility(View.GONE);
                        dontWork.setVisibility(View.GONE);
                    }
                });
            }
        };
        thread.start();
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