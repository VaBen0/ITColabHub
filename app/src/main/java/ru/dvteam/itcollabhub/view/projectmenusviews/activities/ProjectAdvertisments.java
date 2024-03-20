package ru.dvteam.itcollabhub.view.projectmenusviews.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.UsersChosenTheme;
import ru.dvteam.itcollabhub.adapter.AdvertsAdapter;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackDelOrChangeAd;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.databinding.ActivityProjectAdvertismentsBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ProjectAdvertismentsViewModel;

public class ProjectAdvertisments extends AppCompatActivity {

    ActivityProjectAdvertismentsBinding binding;

    private static final int PICK_IMAGES_CODE = 0;
    private Boolean acces = false, acces2 = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    ProjectAdvertismentsViewModel projectAdvertismentsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityProjectAdvertismentsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ProjectAdvertisments.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);
        registerResult();
        initEditTexts();

        projectAdvertismentsViewModel = new ViewModelProvider(this).get(ProjectAdvertismentsViewModel.class);

        projectAdvertismentsViewModel.getNameNotEmpty().observe(this, aBoolean -> {
            acces = aBoolean;
        });
        projectAdvertismentsViewModel.getDescrNotEmpty().observe(this, aBoolean -> {
            acces2 = aBoolean;
        });

        binding.nameProject.setText(projectAdvertismentsViewModel.getProjectTitle());
        Glide
                .with(ProjectAdvertisments.this)
                .load(projectAdvertismentsViewModel.getProjectPhoto())
                .into(binding.prLogo);
        Glide
                .with(ProjectAdvertisments.this)
                .load(projectAdvertismentsViewModel.getProjectPhoto())
                .into(binding.fileImage);

        binding.ads.setLayoutManager(new LinearLayoutManager(this));

        projectAdvertismentsViewModel.getAdds1().observe(this, dataOfWatchers -> {
            AdvertsAdapter adapter = new AdvertsAdapter(this, dataOfWatchers, new CallBackDelOrChangeAd() {
                @Override
                public void delete(String id) {
                    projectAdvertismentsViewModel.deleteAd(id);
                }

                @Override
                public void change(int position) {
                    projectAdvertismentsViewModel.onChangeClick(position, new CallBackBoolean() {
                        @Override
                        public void invoke(Boolean res) {
                            Intent intent = new Intent(ProjectAdvertisments.this, EditAdvertisment.class);
                            startActivity(intent);
                        }
                    });
                }
            });

            binding.ads.setAdapter(adapter);

        });

        projectAdvertismentsViewModel.setAdverts();

        binding.addAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(acces + " " + acces2);
                if(acces && acces2) {
                    projectAdvertismentsViewModel.onCreateClick();
                    projectAdvertismentsViewModel.setAdverts();
                    binding.advert.setText("");
                    binding.advertName.setText("");
                    Glide
                            .with(ProjectAdvertisments.this)
                            .load(projectAdvertismentsViewModel.getProjectPhoto())
                            .into(binding.fileImage);
                    projectAdvertismentsViewModel.setAdverts();
                }else{
                    Toast.makeText(ProjectAdvertisments.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
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
                projectAdvertismentsViewModel.setAdvImg(cursor.getString(columnIndex));
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
                            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                            assert cursor != null;
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            projectAdvertismentsViewModel.setAdvImg(cursor.getString(columnIndex));
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

    @Override
    protected void onRestart() {
        super.onRestart();
        projectAdvertismentsViewModel.setAdverts();
    }

    private void initEditTexts(){
        binding.advertName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                projectAdvertismentsViewModel.setAdvName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.advert.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                projectAdvertismentsViewModel.setAdvDescr(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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