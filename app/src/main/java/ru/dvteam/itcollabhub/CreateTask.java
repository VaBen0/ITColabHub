package ru.dvteam.itcollabhub;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.databinding.ActivityCreateTaskBinding;
import ru.dvteam.itcollabhub.databinding.ActivityTasksMainBinding;

public class CreateTask extends AppCompatActivity {

    private String mail, id, title, prPhoto, description;
    ActivityCreateTaskBinding binding;
    ImageView Img;
    private static final int PICK_IMAGES_CODE = 0;
    private Boolean enter = true;
    private ArrayList<String> mediaPaths, stringBlock, youtubeBlock, linkBlock, imageBlock;
    private Boolean acces = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    private ArrayList<String> type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateTaskBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        registerResult();

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");

        binding.nameProject.setText(title);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.prLogo);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.advertPhoto);

        mediaPaths = new ArrayList<String>();
        type = new ArrayList<String>();

        binding.addBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.blockMenu.getVisibility() == View.GONE) {
                    binding.blockMenu.setVisibility(View.VISIBLE);
                    binding.viewHideBut.setVisibility(View.VISIBLE);
                    final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add);
                    binding.blockMenu.startAnimation(show);
                }
            }
        });

        binding.addDescr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.blockMenu.getVisibility() == View.GONE) {
                    binding.blockMenu.setVisibility(View.VISIBLE);
                    binding.viewHideBut.setVisibility(View.VISIBLE);
                    final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add);
                    binding.blockMenu.startAnimation(show);
                }
            }
        });

        binding.youtubeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.blockMenu.getVisibility() == View.VISIBLE) {
                    binding.blockMenu.setVisibility(View.GONE);
                    binding.viewHideBut.setVisibility(View.GONE);
                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete);
                    binding.blockMenu.startAnimation(hide);
                    final int cnt = type.size();

                    View custom = getLayoutInflater().inflate(R.layout.gblock_youtube_link, null);
                    ImageView del = custom.findViewById(R.id.deleteBut);
                    del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.main.removeView(custom);
                            type.remove(cnt);
                            if(binding.main.getChildCount() == 0){
                                binding.addBlock.setVisibility(View.VISIBLE);
                                binding.texttexttext.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    binding.main.addView(custom);
                    if(binding.addBlock.getVisibility() == View.VISIBLE){
                        binding.addBlock.setVisibility(View.GONE);
                        binding.texttexttext.setVisibility(View.GONE);
                    }
                }

                type.add("4");
            }
        });

        binding.linkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.blockMenu.getVisibility() == View.VISIBLE) {
                    binding.blockMenu.setVisibility(View.GONE);
                    binding.viewHideBut.setVisibility(View.GONE);
                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete);
                    binding.blockMenu.startAnimation(hide);
                    final int cnt = type.size();

                    View custom = getLayoutInflater().inflate(R.layout.gblock_link, null);
                    ImageView del = custom.findViewById(R.id.deleteBut);
                    del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.main.removeView(custom);
                            type.remove(cnt);
                            if(binding.main.getChildCount() == 0){
                                binding.addBlock.setVisibility(View.VISIBLE);
                                binding.texttexttext.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    binding.main.addView(custom);
                    if(binding.addBlock.getVisibility() == View.VISIBLE){
                        binding.addBlock.setVisibility(View.GONE);
                        binding.texttexttext.setVisibility(View.GONE);
                    }

                    type.add("2");
                }
            }
        });

        binding.imageBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.blockMenu.getVisibility() == View.VISIBLE) {
                    binding.blockMenu.setVisibility(View.GONE);
                    binding.viewHideBut.setVisibility(View.GONE);
                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete);
                    binding.blockMenu.startAnimation(hide);
                    final int cnt = type.size();

                    View custom = getLayoutInflater().inflate(R.layout.gblock_image, null);

                    ImageView del = custom.findViewById(R.id.deleteBut);
                    ImageView addImg = custom.findViewById(R.id.addImg);
                    Img = custom.findViewById(R.id.chosen_img);
                    TextView clear = custom.findViewById(R.id.textAdd);
                    ImageView arr = custom.findViewById(R.id.arrow);

                    if(android.os.Build.VERSION.SDK_INT >= 33) {
                        addImg.setOnClickListener(view -> pickImage(clear, arr, Img));
                    }
                    else{
                        addImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clear.setVisibility(View.GONE);
                                arr.setVisibility(View.GONE);
                                Img.setVisibility(View.VISIBLE);
                                if (ContextCompat.checkSelfPermission(CreateTask.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(CreateTask.this,
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

                    del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.main.removeView(custom);
                            type.remove(cnt);
                            if(binding.main.getChildCount() == 0){
                                binding.addBlock.setVisibility(View.VISIBLE);
                                binding.texttexttext.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    binding.main.addView(custom);

                    if(binding.addBlock.getVisibility() == View.VISIBLE){
                        binding.addBlock.setVisibility(View.GONE);
                        binding.texttexttext.setVisibility(View.GONE);
                    }

                    type.add("3");
                }
            }
        });

        binding.textBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.blockMenu.getVisibility() == View.VISIBLE) {
                    binding.blockMenu.setVisibility(View.GONE);
                    binding.viewHideBut.setVisibility(View.GONE);
                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete);
                    binding.blockMenu.startAnimation(hide);
                    final int cnt = type.size();

                    View custom = getLayoutInflater().inflate(R.layout.gblock_text, null);

                    ImageView del = custom.findViewById(R.id.deleteBut);

                    del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.main.removeView(custom);
                            type.remove(cnt);
                            Toast.makeText(CreateTask.this, type.toString(), Toast.LENGTH_SHORT).show();
                            if(binding.main.getChildCount() == 0){
                                binding.addBlock.setVisibility(View.VISIBLE);
                                binding.texttexttext.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    binding.main.addView(custom);

                    if(binding.addBlock.getVisibility() == View.VISIBLE){
                        binding.addBlock.setVisibility(View.GONE);
                        binding.texttexttext.setVisibility(View.GONE);
                    }

                    type.add("1");
                }
            }
        });

        binding.viewHideBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.blockMenu.getVisibility() == View.VISIBLE) {
                    binding.blockMenu.setVisibility(View.GONE);
                    binding.viewHideBut.setVisibility(View.GONE);
                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete);
                    binding.blockMenu.startAnimation(hide);
                }
            }
        });

        binding.uploadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBlock = new ArrayList<String>();
                linkBlock = new ArrayList<String>();;
                youtubeBlock = new ArrayList<String>();
                imageBlock = new ArrayList<String>();
                enter = true;
                int count = binding.main.getChildCount();
                int imgCount = 0;
                if(count > 0){
                    for(int i = 0; i < count; i++){
                        View custom = binding.main.getChildAt(i);
                        if(type.get(i).equals("1")){
                            EditText text = custom.findViewById(R.id.text);
                            if(text.getText().toString().isEmpty()){
                                Toast.makeText(CreateTask.this, "Нет текств", Toast.LENGTH_SHORT).show();
                                enter = false;
                                break;
                            }
                            stringBlock.add(text.getText().toString());
                        }
                        if(type.get(i).equals("2")){
                            EditText linkTitle = custom.findViewById(R.id.name_link);
                            if(linkTitle.getText().toString().isEmpty()){
                                Toast.makeText(CreateTask.this, "Нет названия ссылки", Toast.LENGTH_SHORT).show();
                                enter = false;
                                break;
                            }
                            EditText link = custom.findViewById(R.id.link_main);
                            if(link.getText().toString().isEmpty()){
                                Toast.makeText(CreateTask.this, "Нет ссылки", Toast.LENGTH_SHORT).show();
                                enter = false;
                                break;
                            }
                            String s = linkTitle.getText().toString() + "\uD83D\uDD70" + link.getText().toString();
                            linkBlock.add(s);
                        }
                        if(type.get(i).equals("4")){
                            EditText linkTitle = custom.findViewById(R.id.linkTitle);
                            if(linkTitle.getText().toString().isEmpty()){
                                Toast.makeText(CreateTask.this, "Нет названия ссылки на Ютуб", Toast.LENGTH_SHORT).show();
                                enter = false;
                                break;
                            }
                            EditText link = custom.findViewById(R.id.link);
                            if(link.getText().toString().isEmpty()){
                                Toast.makeText(CreateTask.this, "Нет ссылки на Ютуб", Toast.LENGTH_SHORT).show();
                                enter = false;
                                break;
                            }
                            String s = linkTitle.getText().toString() + "\uD83D\uDD70" + link.getText().toString();
                            youtubeBlock.add(s);
                        }
                        if(type.get(i).equals("3")){
                            EditText imgTitle = custom.findViewById(R.id.title);
                            if(imgTitle.getText().toString().isEmpty()){
                                Toast.makeText(CreateTask.this, "Нет названия изображения", Toast.LENGTH_SHORT).show();
                                enter = false;
                                break;
                            }
                            if(mediaPaths.size() <= imgCount){
                                Toast.makeText(CreateTask.this, "Нет изображения", Toast.LENGTH_SHORT).show();
                                enter = false;
                                break;
                            }
                            String s = mediaPaths.get(imgCount) + "\uD83D\uDD70" + imgTitle.getText().toString();
                            imageBlock.add(s);
                            imgCount++;
                        }
                    }

                    String stringBlockStr;
                    String linkBlockStr;
                    String youtubeBlockStr;
                    String imageBlockStr;

                    if(stringBlock.size() == 0){
                        stringBlockStr = "Empty";
                    }else{
                        Toast.makeText(CreateTask.this, stringBlock.size() + "", Toast.LENGTH_SHORT).show();
                        stringBlockStr = String.join("_/-", stringBlock);
                    }
                    if(linkBlock.size() == 0){
                        linkBlockStr = "Empty";
                    }else{
                        linkBlockStr = String.join("_/-", linkBlock);
                    }
                    if(youtubeBlock.size() == 0){
                        youtubeBlockStr = "Empty";
                    }else{
                        youtubeBlockStr = String.join("_/-", youtubeBlock);
                    }
                    if(imageBlock.size() == 0){
                        imageBlockStr = "Empty";
                    }else{
                        imageBlockStr = String.join("_/-", imageBlock);
                    }
                    if(enter) {
                        Intent intent = new Intent(CreateTask.this, PartisipantTasks.class);
                        intent.putExtra("projectId", id);
                        intent.putExtra("projectTitle", title);
                        intent.putExtra("projectUrlPhoto", prPhoto);
                        intent.putExtra("TaskName", binding.taskName.getText().toString());
                        intent.putExtra("Queue", String.join(",", type));
                        intent.putExtra("StringBlock", stringBlockStr);
                        intent.putExtra("LinkBlock", linkBlockStr);
                        intent.putExtra("YouTubeBlock", youtubeBlockStr);
                        intent.putExtra("ImageBlock", imageBlockStr);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void pickImage(TextView hiden1, ImageView hiden2, ImageView shown){
        hiden1.setVisibility(View.GONE);
        hiden2.setVisibility(View.GONE);
        shown.setVisibility(View.VISIBLE);
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
                Toast.makeText(CreateTask.this, "You loser", Toast.LENGTH_LONG).show();
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
                String mediaPath = cursor.getString(columnIndex);

                mediaPaths.add(mediaPath);

                Img.setImageURI(imageUri);

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
                            String mediaPath = cursor.getString(columnIndex);

                            mediaPaths.add(mediaPath);

                            Img.setImageURI(imageUri);

                        }catch (Exception e){
                            Toast.makeText(CreateTask.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}