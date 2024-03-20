package ru.dvteam.itcollabhub.view.projectmenusviews.activities;

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
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import ru.dvteam.itcollabhub.view.projectmenusviews.fragments.FragmentLinksProjectEdit;
import ru.dvteam.itcollabhub.view.projectmenusviews.fragments.FragmentOtherEdit;
import ru.dvteam.itcollabhub.view.projectmenusviews.fragments.ProjectDescriptionEdit;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.UsersChosenTheme;
import ru.dvteam.itcollabhub.databinding.ActivityEditProjectBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditProjectViewModel;

public class EditProject extends AppCompatActivity {

    ActivityEditProjectBinding binding;
    int score;
    private static final int PICK_IMAGES_CODE = 0;
    private Boolean acces = false;
    private String mediaPath, uriPath;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    EditProjectViewModel editProjectViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding = ActivityEditProjectBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_create_project1);
        editProjectViewModel = new ViewModelProvider(this).get(EditProjectViewModel.class);

        setContentView(binding.getRoot());
        registerResult();
        initEditText();

        editProjectViewModel.getPrInfo().observe(this, projectInformation -> {
            binding.projectName.setText(projectInformation.getProjectTitle());
            Glide
                    .with(EditProject.this)
                    .load(projectInformation.getProjectLogo())
                    .into(binding.prLogo);
        });

        editProjectViewModel.getAllProjectInfo();

        Fragment fragmentDescr = Fragment.instantiate(this, ProjectDescriptionEdit.class.getName());
        Fragment fragmentLink = Fragment.instantiate(this, FragmentLinksProjectEdit.class.getName());
        Fragment fragmentOther = Fragment.instantiate(this, FragmentOtherEdit.class.getName());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentDescr)
                .commit();

        binding.linearDescription.setVisibility(View.VISIBLE);
        binding.linkLine.setVisibility(View.INVISIBLE);
        binding.otherLine.setVisibility(View.INVISIBLE);

        binding.tint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editProjectViewModel.getAllProjectInfo();
        binding.linksFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linkLine.setVisibility(View.VISIBLE);
                binding.linearDescription.setVisibility(View.INVISIBLE);
                binding.otherLine.setVisibility(View.INVISIBLE);
                hidePanel();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragmentLink)
                        .commit();
            }
        });
        binding.descriptionFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linearDescription.setVisibility(View.VISIBLE);
                binding.linkLine.setVisibility(View.INVISIBLE);
                binding.otherLine.setVisibility(View.INVISIBLE);
                hidePanel();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragmentDescr)
                        .commit();
            }
        });
        binding.otherFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.otherLine.setVisibility(View.VISIBLE);
                binding.linkLine.setVisibility(View.INVISIBLE);
                binding.linearDescription.setVisibility(View.INVISIBLE);
                hidePanel();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragmentOther)
                        .commit();
            }
        });

        if(Build.VERSION.SDK_INT >= 33) {
            //binding.prLogo.setOnClickListener(view -> pickImage());
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

    private void initEditText(){
        binding.projectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editProjectViewModel.setProjectName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                editProjectViewModel.setProjectImage(cursor.getString(columnIndex));
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
                        editProjectViewModel.setProjectImage(cursor.getString(columnIndex));
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

    public void showPanel(){
        editProjectViewModel.getAllProjectInfo();
        binding.blockMenu.setVisibility(View.VISIBLE);
        final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add3);
        binding.blockMenu.startAnimation(show);
    }
    public void hidePanel(){
        if (binding.blockMenu.getVisibility() == View.VISIBLE) {
            binding.blockMenu.setVisibility(View.GONE);
            final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete2);
            binding.blockMenu.startAnimation(hide);
        }
    }

//    public void saveDescription(String description){
//        PostDatas post = new PostDatas();
//        if(mediaPath.isEmpty()){
//            post.postDataChangeProjectWithoutImageWithDescription("SaveChangesFromProjectWithoutImageAndDescription",
//                    binding.projectName.getText().toString(), description, id, mail, new CallBackInt() {
//                        @Override
//                        public void invoke(String res) {
//                            binding.blockMenu.setVisibility(View.VISIBLE);
//                            final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add3);
//                            binding.blockMenu.startAnimation(show);
//                        }
//                    });
//        } else{
//            File file = new File(mediaPath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//            post.postDataChangeProjectWithDescription("SaveChangesFromProjectWithImageAndDescription", binding.projectName.getText().toString(),
//                    requestBody, id, mail, description, new CallBackInt() {
//                        @Override
//                        public void invoke(String res) {
//                            deleteCache(EditProject.this);
//                            System.out.println("image");
//                            binding.blockMenu.setVisibility(View.VISIBLE);
//                            final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add3);
//                            binding.blockMenu.startAnimation(show);
//                        }
//                    });
//        }
//    }
//    public void saveLinks(String tg, String vk, String web){
//        PostDatas post = new PostDatas();
//        System.out.println(tg + " " + vk + " " + web);
//        if(mediaPath.isEmpty()){
//            post.postDataChangeProjectWithoutImageWithLink("SaveChangesFromProjectWithoutImageAndLinks",
//                    binding.projectName.getText().toString(), tg, vk, web, id, mail, new CallBackInt() {
//                        @Override
//                        public void invoke(String res) {
//                            binding.blockMenu.setVisibility(View.VISIBLE);
//                            final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add3);
//                            binding.blockMenu.startAnimation(show);
//                        }
//                    });
//        } else{
//            File file = new File(mediaPath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//            post.postDataChangeProjectWithLink("SaveChangesFromProjectWithImageAndLinks", binding.projectName.getText().toString(),
//                    requestBody, id, mail, tg, vk, web, new CallBackInt() {
//                        @Override
//                        public void invoke(String res) {
//                            deleteCache(EditProject.this);
//                            System.out.println("image");
//                            binding.blockMenu.setVisibility(View.VISIBLE);
//                            final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add3);
//                            binding.blockMenu.startAnimation(show);
//                        }
//                    });
//        }
//    }
//
//    public void saveOther(String open, String visible){
//        PostDatas post = new PostDatas();
//
//        if(mediaPath.isEmpty()){
//            post.postDataChangeProjectWithoutImageWithStatus("SaveChangesFromProjectWithoutImageAndIsOpenAndIsVisibile",
//                    binding.projectName.getText().toString(), open, visible, id, mail, new CallBackInt() {
//                        @Override
//                        public void invoke(String res) {
//                            binding.blockMenu.setVisibility(View.VISIBLE);
//                            final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add3);
//                            binding.blockMenu.startAnimation(show);
//                        }
//                    });
//        } else{
//            File file = new File(mediaPath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//            post.postDataChangeProjectWithStatus("SaveChangesFromProjectWithImageAndIsOpenAndIsVisibile", binding.projectName.getText().toString(),
//                    requestBody, id, mail, open, visible, new CallBackInt() {
//                        @Override
//                        public void invoke(String res) {
//                            deleteCache(EditProject.this);
//                            System.out.println("image");
//
//                        }
//                    });
//        }
//    }
//
//    public int description(){return score;}
//    public String getDescription(){
//        return description;
//    }
//    public int getScore(){return score;}
//    public String getMail(){return mail;}
//    public String getTgLink(){return tgLink;}
//    public String getVkLink(){return vkLink;}
//    public String getWebLink(){return webLink;}
//    public String getIsOpen(){return isOpen;}
//    public String getIsVisible(){return isVisible;}
//
//    private void pickImage(){
//        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
//        resultLauncher.launch(intent);
//    }
//
//    public static void deleteCache(Context context) {
//        try {
//            File dir = context.getCacheDir();
//            deleteDir(dir);
//        } catch (Exception e) { e.printStackTrace();}
//    }
//
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//            return dir.delete();
//        } else if(dir!= null && dir.isFile()) {
//            return dir.delete();
//        } else {
//            return false;
//        }
//    }
//
//    public void endProject(){
//        Intent intent = new Intent(EditProject.this, EndProject.class);
//        intent.putExtra("projectTitle", title);
//        intent.putExtra("projectUrlPhoto", prPhoto);
//        intent.putExtra("projectId", id);
//        startActivity(intent);
//    }

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