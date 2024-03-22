package ru.dvteam.itcollabhub.view.forum;

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

import java.util.ArrayList;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.ActivityCreateArticleBinding;

public class CreateArticle extends AppCompatActivity {

    ActivityCreateArticleBinding binding;
    private String mail;
    ImageView Img;
    private static final int PICK_IMAGES_CODE = 0;
    private Boolean enter = true;
    private ArrayList<String> mediaPaths, stringBlock, youtubeBlock, linkBlock, imageBlock;
    private Boolean acces = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    private int imgCnt = 0, p, checlMedia = 0;
    private ArrayList<String> type;
    private ArrayList<Integer> del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateArticleBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        registerResult();

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);
        setActivityFormat(score);

        View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
        binding.main.addView(empty);

        mediaPaths = new ArrayList<String>();
        type = new ArrayList<String>();
        del = new ArrayList<>();
        setButtonColor(score, binding.addBlock);
        setButtonColor(score, binding.addDescr);

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
                    binding.main.removeView(empty);
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
                            type.set(cnt, "");
                            if(binding.main.getChildCount() == 0){
                                binding.addBlock.setVisibility(View.VISIBLE);
                                binding.texttexttext.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    binding.main.addView(custom);
                    binding.main.addView(empty);
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
                    binding.main.removeView(empty);
                    binding.blockMenu.setVisibility(View.GONE);
                    binding.viewHideBut.setVisibility(View.GONE);
                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete);
                    binding.blockMenu.startAnimation(hide);
                    final int cnt = type.size();

                    View custom = getLayoutInflater().inflate(R.layout.gblock_link, null);
                    ImageView del = custom.findViewById(R.id.deleteBut);
                    custom.setId(cnt);
                    del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.main.removeView(custom);
                            type.set(cnt, "");
                            if(binding.main.getChildCount() == 0){
                                binding.addBlock.setVisibility(View.VISIBLE);
                                binding.texttexttext.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    binding.main.addView(custom);
                    binding.main.addView(empty);
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
                    binding.main.removeView(empty);
                    binding.blockMenu.setVisibility(View.GONE);
                    binding.viewHideBut.setVisibility(View.GONE);
                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_delete);
                    binding.blockMenu.startAnimation(hide);
                    final int cnt = type.size();
                    final int importInt = imgCnt;

                    View custom = getLayoutInflater().inflate(R.layout.gblock_image, null);
                    mediaPaths.add("");
                    del.add(1);

                    ImageView deli = custom.findViewById(R.id.deleteBut);
                    ImageView addImg = custom.findViewById(R.id.addImg);
                    setButtonColor(score, addImg);
                    final ImageView chosenImg = custom.findViewById(R.id.chosen_img);
                    TextView clear = custom.findViewById(R.id.textAdd);
                    ImageView arr = custom.findViewById(R.id.arrow);
                    custom.setId(importInt);

                    if(android.os.Build.VERSION.SDK_INT >= 33) {
                        addImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Img = chosenImg;
                                p = importInt;
                                clear.setVisibility(View.GONE);
                                arr.setVisibility(View.GONE);
                                Img.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
                                resultLauncher.launch(intent);
                            }
                        });
                    }
                    else{
                        addImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Img = chosenImg;
                                p = importInt;
                                clear.setVisibility(View.GONE);
                                arr.setVisibility(View.GONE);
                                Img.setVisibility(View.VISIBLE);
                                if (ContextCompat.checkSelfPermission(CreateArticle.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(CreateArticle.this,
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

                    deli.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.main.removeView(custom);
                            //type.set(cnt, "");
                            mediaPaths.set(importInt, "");
                            del.set(importInt, 0);

                            if(binding.main.getChildCount() == 0){
                                binding.addBlock.setVisibility(View.VISIBLE);
                                binding.texttexttext.setVisibility(View.VISIBLE);
                            }

                        }
                    });
                    binding.main.addView(custom);
                    binding.main.addView(empty);

                    if(binding.addBlock.getVisibility() == View.VISIBLE){
                        binding.addBlock.setVisibility(View.GONE);
                        binding.texttexttext.setVisibility(View.GONE);
                    }

                    imgCnt++;

                    type.add("3");
                }
            }
        });

        binding.textBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.blockMenu.getVisibility() == View.VISIBLE) {
                    binding.main.removeView(empty);
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
                            type.set(cnt, "");
                            //Toast.makeText(CreateTask.this, type.toString(), Toast.LENGTH_SHORT).show();
                            if(binding.main.getChildCount() == 0){
                                binding.addBlock.setVisibility(View.VISIBLE);
                                binding.texttexttext.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    binding.main.addView(custom);
                    binding.main.addView(empty);

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
                /*if(binding.taskName.getText().toString().isEmpty()){
                    Toast.makeText(CreateArticle.this, "Дайте название заданию", Toast.LENGTH_SHORT).show();
                }else{
                    stringBlock = new ArrayList<String>();
                    linkBlock = new ArrayList<String>();;
                    youtubeBlock = new ArrayList<String>();
                    imageBlock = new ArrayList<String>();
                    enter = true;
                    int count = binding.main.getChildCount();
                    int imgCount = 0;
                    StringBuilder typeStr = new StringBuilder();
                    int a = 0;
                    if(count > 0) {
                        for (int i = 0; i < type.size(); i++) {
                            View custom = binding.main.getChildAt(a);
                            if (type.get(i).equals("1")) {
                                EditText text = custom.findViewById(R.id.text);
                                if (text.getText().toString().isEmpty()) {
                                    Toast.makeText(CreateArticle.this, "Нет текств", Toast.LENGTH_SHORT).show();
                                    enter = false;
                                    break;
                                }
                                stringBlock.add(text.getText().toString());
                                String l = type.get(i) + ",";
                                typeStr.append(l);
                                a++;
                            }
                            if (type.get(i).equals("2")) {
                                EditText linkTitle = custom.findViewById(R.id.name_link);
                                if (linkTitle.getText().toString().isEmpty()) {
                                    Toast.makeText(CreateArticle.this, "Нет названия ссылки", Toast.LENGTH_SHORT).show();
                                    enter = false;
                                    break;
                                }
                                EditText link = custom.findViewById(R.id.link_main);
                                if (link.getText().toString().isEmpty()) {
                                    Toast.makeText(CreateArticle.this, "Нет ссылки", Toast.LENGTH_SHORT).show();
                                    enter = false;
                                    break;
                                }
                                String s = linkTitle.getText().toString() + "\uD83D\uDD70" + link.getText().toString();
                                linkBlock.add(s);
                                String l = type.get(i) + ",";
                                typeStr.append(l);
                                a++;
                            }
                            if (type.get(i).equals("4")) {
                                EditText linkTitle = custom.findViewById(R.id.linkTitle);
                                if (linkTitle.getText().toString().isEmpty()) {
                                    Toast.makeText(CreateArticle.this, "Нет названия ссылки на Ютуб", Toast.LENGTH_SHORT).show();
                                    enter = false;
                                    break;
                                }
                                EditText link = custom.findViewById(R.id.link);
                                if (link.getText().toString().isEmpty()) {
                                    Toast.makeText(CreateArticle.this, "Нет ссылки на Ютуб", Toast.LENGTH_SHORT).show();
                                    enter = false;
                                    break;
                                }
                                String s = linkTitle.getText().toString() + "\uD83D\uDD70" + link.getText().toString();
                                youtubeBlock.add(s);
                                String l = type.get(i) + ",";
                                typeStr.append(l);
                                a++;
                            }
                            if (type.get(i).equals("3")) {
                                if(del.get(imgCount) == 1) {
                                    EditText imgTitle = custom.findViewById(R.id.title);
                                    if (imgTitle.getText().toString().isEmpty()) {
                                        Toast.makeText(CreateArticle.this, "Нет названия изображения", Toast.LENGTH_SHORT).show();
                                        enter = false;
                                        break;
                                    }
                                    if (mediaPaths.get(imgCount).equals("")) {
                                        Toast.makeText(CreateArticle.this, "Нет изображения", Toast.LENGTH_SHORT).show();
                                        enter = false;
                                        break;
                                    }
                                    String s = mediaPaths.get(imgCount) + "\uD83D\uDD70" + imgTitle.getText().toString();
                                    imageBlock.add(s);
                                    String l = type.get(i) + ",";
                                    typeStr.append(l);
                                    a++;
                                }
                                imgCount++;
                            }
                        }
                        String typeString = typeStr.substring(0, typeStr.length() - 1);
                        String stringBlockStr;
                        String linkBlockStr;
                        String youtubeBlockStr;
                        String imageBlockStr;

                        if (stringBlock.size() == 0) {
                            stringBlockStr = "Empty";
                        } else {
                            stringBlockStr = String.join("_/-", stringBlock);
                        }
                        if (linkBlock.size() == 0) {
                            linkBlockStr = "Empty";
                        } else {
                            linkBlockStr = String.join("_/-", linkBlock);
                        }
                        if (youtubeBlock.size() == 0) {
                            youtubeBlockStr = "Empty";
                        } else {
                            youtubeBlockStr = String.join("_/-", youtubeBlock);
                        }
                        if (imageBlock.size() == 0) {
                            imageBlockStr = "Empty";
                        } else {
                            imageBlockStr = String.join("_/-", imageBlock);
                        }
                        if (enter) {
                            Intent intent = new Intent(CreateArticle.this, PartisipantTasks.class);
                            intent.putExtra("TaskName", binding.taskName.getText().toString());
                            intent.putExtra("Queue", typeString);
                            intent.putExtra("StringBlock", stringBlockStr);
                            intent.putExtra("LinkBlock", linkBlockStr);
                            intent.putExtra("YouTubeBlock", youtubeBlockStr);
                            intent.putExtra("ImageBlock", imageBlockStr);
                            startActivity(intent);
                        }
                    }
                }*/
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
                Toast.makeText(CreateArticle.this, "You loser", Toast.LENGTH_LONG).show();
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

                mediaPaths.set(p, mediaPath);
                checlMedia++;

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

                            mediaPaths.set(p, mediaPath);
                            checlMedia++;

                            Img.setImageURI(imageUri);

                        }catch (Exception e){
                            Toast.makeText(CreateArticle.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateArticle.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateArticle.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateArticle.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateArticle.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateArticle.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateArticle.this,R.color.red));
        }
        else if(score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateArticle.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateArticle.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateArticle.this,R.color.main_green));
        }
    }
    private void setButtonColor(int score, ImageView but){
        if(score < 100){
            but.setImageResource(R.drawable.ad);
        }
        else if(score < 300){
            but.setImageResource(R.drawable.green_add);
        }
        else if(score < 1000){
            but.setImageResource(R.drawable.brown_add);
        }
        else if(score < 2500){
            but.setImageResource(R.drawable.light_gray_add);
        }
        else if(score < 7000){
            but.setImageResource(R.drawable.ohra_add);
        }
        else if(score < 17000){
            but.setImageResource(R.drawable.red_add);
        }
        else if(score < 30000) {
            but.setImageResource(R.drawable.brown_add);
        }
        else if(score < 50000){
            but.setImageResource(R.drawable.violete_add);
        }
        else{
            but.setImageResource(R.drawable.blue_green_add);
        }
    }

}