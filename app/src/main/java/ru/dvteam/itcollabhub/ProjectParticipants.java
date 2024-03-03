package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.databinding.ActivityProjectParticipantsBinding;

public class ProjectParticipants extends AppCompatActivity {

    ActivityProjectParticipantsBinding binding;
    String id, title, description, prPhoto, mail;
    ArrayList<Integer> indexViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProjectParticipantsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        description = arguments.getString("projectDescription");

        binding.projectName.setText(title);
        Glide
                .with(ProjectParticipants.this)
                .load(prPhoto)
                .into(binding.prLogo);

        if (score < 100) {
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.blue));
        } else if (score < 300) {
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.green));
        } else if (score < 1000) {
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.brown));
        } else if (score < 2500) {
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.light_gray));
        } else if (score < 7000) {
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.ohra));
        } else if (score < 17000) {
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.red));
        } else if (score < 30000) {
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.orange));
        } else if (score < 50000) {
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.violete));
        } else {
            binding.add.setBackgroundTintList(ContextCompat.getColorStateList(ProjectParticipants.this, R.color.main_green));
        }
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectParticipants.this, AddParticipant.class);
                startActivity(intent);
            }
        });
        binding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectParticipants.this, NotifyInParticipants.class);
                intent.putExtra("projectTitle", title);
                intent.putExtra("projectUrlPhoto", prPhoto);
                intent.putExtra("projectId", id);
                startActivity(intent);
            }
        });
        binding.find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexViews = new ArrayList<>();
                if(binding.nameFriend.getText().toString().isEmpty()){
                    Toast.makeText(ProjectParticipants.this, "Вы ничего не ввели", Toast.LENGTH_SHORT).show();
                }
                else{
                    for(int i = 0; i < binding.linMain.getChildCount() - 1; i++){
                        View custom = binding.linMain.getChildAt(i);
                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                        if(nameu.getText().toString().contains(binding.nameFriend.getText().toString())){
                            indexViews.add(i);
                        }
                    }
                    if(indexViews.isEmpty()){
                        Toast.makeText(ProjectParticipants.this, "Таких участников не существует", Toast.LENGTH_SHORT).show();
                    }else{
                        for(int i = 0; i < binding.linMain.getChildCount() - 1; i++){
                            if(!indexViews.contains(i)) {
                                binding.linMain.getChildAt(i).setVisibility(View.GONE);
                            }
                            else{
                                binding.linMain.getChildAt(i).setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            }
        });

        PostDatas post = new PostDatas();
        post.postDataGetProjectParticipant("GetPeoplesFromProjects", id, mail, new CallBackInt5() {
            @Override
            public void invoke(String ids, String names, String photos) {
                binding.linMain.removeAllViews();
                String[] idsArr = ids.split("\uD83D\uDD70");
                String[] namesArr = names.split("\uD83D\uDD70");
                String[] photosArr = photos.split("\uD83D\uDD70");

                for (int i = 0; i < idsArr.length; i++) {
                    View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
                    TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                    ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                    ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                    TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                    project1.setVisibility(View.GONE);
                    ImageView messege = (ImageView) custom.findViewById(R.id.notban);
                    messege.setImageResource(R.drawable.delete_black);
                    userCircle.setVisibility(View.GONE);
                    System.out.println(photosArr[i]);

                    Glide
                            .with(ProjectParticipants.this)
                            .load(photosArr[i])
                            .into(loadImage);
                    nameu.setText(namesArr[i]);

                    messege.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    binding.linMain.addView(custom);
                }
                View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                binding.linMain.addView(empty);
            }
        });


    }
}