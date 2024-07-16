package ru.dvteam.itcollabhub.view.authorizeviews;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.databinding.ActivityCreateAccountBinding;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels.CreateAccountViewModel;


public class CreateAccount extends AppCompatActivity {

    ActivityCreateAccountBinding binding;
    CreateAccountViewModel createAccountViewModel;
    private static final int PICK_IMAGES_CODE = 0;
    private Boolean access = false, accessName = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        createAccountViewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);
        registerResult();
        initEditText();

        float width = binding.itCollabHubText.getPaint().measureText("ITCollabHub");

        Shader textShader1 = new LinearGradient(0, 0, width, binding.itCollabHubText.getTextSize(),
                new int[]{Color.rgb(199, 8, 225), Color.rgb(236, 54, 75)},
                null, Shader.TileMode.CLAMP);

        binding.itCollabHubText.getPaint().setShader(textShader1);

        float width2 = binding.itCollabHubText2.getPaint().measureText("Вместе в IT");

        Shader textShader2 = new LinearGradient(0, 0, width2, binding.itCollabHubText2.getTextSize(),
                new int[]{Color.rgb(246, 168, 253), Color.rgb(207, 177, 241)},
                null, Shader.TileMode.CLAMP);

        binding.itCollabHubText2.getPaint().setShader(textShader2);

        float width3 = binding.version.getPaint().measureText("1.0.0");

        Shader textShader3 = new LinearGradient(0, 0, width3, binding.version.getTextSize(),
                new int[]{Color.rgb(119, 96, 212), Color.rgb(37, 201, 159)},
                null, Shader.TileMode.CLAMP);

        binding.version.getPaint().setShader(textShader3);

        float width4 = binding.stadium.getPaint().measureText("Beta");

        Shader textShader4 = new LinearGradient(0, 0, width4, binding.stadium.getTextSize(),
                new int[]{Color.rgb(119, 96, 212), Color.rgb(37, 201, 159)},
                null, Shader.TileMode.CLAMP);

        binding.stadium.getPaint().setShader(textShader4);

        if(android.os.Build.VERSION.SDK_INT >= 33) {
            binding.log.setOnClickListener(view -> pickImage());
        }
        else{
            binding.log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(CreateAccount.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CreateAccount.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_PICK);
                        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGES_CODE);
                    }
                }
            });
        }

        createAccountViewModel.getChosenImage().observe(this, aBoolean -> {
            access = aBoolean;
        });

        createAccountViewModel.getCorrectlyName().observe(this, aBoolean -> {
            if(aBoolean){
                binding.nameuBg.setBackgroundColor(getResources().getColor(R.color.dark_theme));
                accessName = true;
            }else{
                binding.nameuBg.setBackgroundColor(getResources().getColor(R.color.red));
                accessName = false;
            }
        });

        binding.saveBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access && accessName) {
                    createAccountViewModel.onSaveClicked(new CallBackBoolean() {
                        @Override
                        public void invoke(Boolean res) {
                            Intent intent = new Intent(CreateAccount.this, Profile.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Toast.makeText(CreateAccount.this, "Заполнены не все поля", Toast.LENGTH_SHORT).show();
                }
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
                Toast.makeText(CreateAccount.this, "You loser", Toast.LENGTH_LONG).show();
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
                createAccountViewModel.setImage(cursor.getString(columnIndex));
                binding.log.setImageURI(imageUri);
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
                            createAccountViewModel.setImage(cursor.getString(columnIndex));
                            binding.log.setImageURI(imageUri);
                            cursor.close();

                        }catch (Exception e){
                            Toast.makeText(CreateAccount.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void initEditText(){
        binding.nameu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createAccountViewModel.setName(binding.nameu.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}