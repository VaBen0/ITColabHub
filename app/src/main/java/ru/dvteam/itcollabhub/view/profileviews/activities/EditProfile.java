package ru.dvteam.itcollabhub.view.profileviews.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.view.profileviews.fragments.AboutApp;
import ru.dvteam.itcollabhub.view.profileviews.fragments.AccountLinks;
import ru.dvteam.itcollabhub.view.MainActivity;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.databinding.ActivityEditProfileBinding;
import ru.dvteam.itcollabhub.viewmodel.profileviewmodels.EditProfileViewModel;

public class EditProfile extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    EditProfileViewModel editProfileViewModel;
    private static final int PICK_IMAGES_CODE = 0;
    private String mediaPath = "";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    private final String[] wow = {"Хренос 2", "Кина не будет - электричество кончилось", "Ой, сломалось", "Караул!"};
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;
    View back;
    ImageView dontWork;
    Fragment frgmentAccountLinks, fragmentAboutApp;
    Boolean access = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);

        super.onCreate(savedInstanceState);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        editProfileViewModel.setShPref(sharedPreferences);

        registerResult();
        initEditText();

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(EditProfile.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        editProfileViewModel.getUserAllInfo().observe(this, profileInformation -> {
            String s = "Ваши очки: " + profileInformation.getUserScore();
            binding.score.setText(s);

            Glide
                    .with(EditProfile.this)
                    .load(profileInformation.getUserImageUrl())
                    .into(binding.loadImg);
            binding.nameu.setText(profileInformation.getUserName());
            editProfileViewModel.isbanned();
        });

        editProfileViewModel.getBanned().observe(this, aBoolean -> {
            access = !aBoolean;
            binding.nameu.setClickable(!aBoolean);
        });

        editProfileViewModel.getProfileInformation(sharedPreferences);
        editProfileViewModel.getUserAllLinks(sharedPreferences);

        back = findViewById(R.id.view3);
        dontWork = findViewById(R.id.imageView12);

        binding.linearFriends.setVisibility(View.INVISIBLE);
        binding.linearProjects.setVisibility(View.VISIBLE);

        fragmentAboutApp = Fragment.instantiate(this, AboutApp.class.getName());
        frgmentAccountLinks = Fragment.instantiate(this, AccountLinks.class.getName());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.editProfileFragmentContainer, frgmentAccountLinks)
                .commit();

        binding.restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        binding.quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.clear();
                ed.apply();
                UsersChosenTheme.setThemeNum(0);
                startActivity(intent);
                finish();
            }
        });

        if(android.os.Build.VERSION.SDK_INT >= 33) {
            binding.loadImg.setOnClickListener(view -> pickImage());
        }
        else{
            binding.loadImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(access) {
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
                    }else Toast.makeText(EditProfile.this, "Вы заблокированны", Toast.LENGTH_SHORT).show();
                }
            });
        }

        binding.firstMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linearFriends.setVisibility(View.INVISIBLE);
                binding.linearProjects.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.editProfileFragmentContainer, frgmentAccountLinks)
                        .commit();
            }
        });

        binding.secondMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linearProjects.setVisibility(View.INVISIBLE);
                binding.linearFriends.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.editProfileFragmentContainer, fragmentAboutApp)
                        .commit();
            }
        });
    }

    private void pickImage(){
        if(access) {
            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            resultLauncher.launch(intent);
        }else Toast.makeText(this, "Вы заблокированны", Toast.LENGTH_SHORT).show();
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
        if (requestCode == PICK_IMAGES_CODE){

            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                editProfileViewModel.setMediaPath(cursor.getString(columnIndex));
                binding.loadImg.setImageURI(imageUri);
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
                            editProfileViewModel.setMediaPath(cursor.getString(columnIndex));
                            binding.loadImg.setImageURI(imageUri);
                            cursor.close();
                        }catch (Exception e){
                            Toast.makeText(EditProfile.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void editProfile(String vk, String tg, String web){
//        EditText UserName = findViewById(R.id.nameu);
//        PostDatas post = new PostDatas();
//        if(mediaPath.isEmpty()){
//            post.postDataEditProfileWithoutImage("CreateNameLog1", mail, UserName.getText().toString(), tg, vk, web, new CallBackInt() {
//                @Override
//                public void invoke(String res) {
//                    Toast.makeText(EditProfile.this, res, Toast.LENGTH_SHORT).show();
//                    if(res.equals("Сохранено")) {
//                        Intent intent = new Intent(EditProfile.this, Profile.class);
//
//                        startActivity(intent);
//                    }
//                }
//            });
//        }
//        else{
//            File file = new File(mediaPath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//            post.postDataEditProfile("CreateNameLog1", UserName.getText().toString(), tg, vk, web, requestBody, mail, new CallBackInt() {
//                @Override
//                public void invoke(String res) {
//                    Toast.makeText(EditProfile.this, res, Toast.LENGTH_SHORT).show();
//                    if(res.equals("Сохранено")) {
//                        Intent intent = new Intent(EditProfile.this, Profile.class);
//
//                        startActivity(intent);
//                    }
//                }
//            });
//        }

    }

//    public String getMail(){
//        return mail;
//    }
//    public int getScore(){
//        return score;
//    }
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

    public void initEditText(){
        binding.nameu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editProfileViewModel.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onRestart() {
        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);
        super.onRestart();
    }
}