package ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.view.adapter.ParticipantsAdapter;
import ru.dvteam.itcollabhub.databinding.ActivityProjectParticipantsBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ProjectParticipantsViewModel;

public class ProjectParticipants extends AppCompatActivity {

    ActivityProjectParticipantsBinding binding;
    ProjectParticipantsViewModel projectParticipantsViewModel;
    ParticipantsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityProjectParticipantsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        projectParticipantsViewModel = new ViewModelProvider(this).get(ProjectParticipantsViewModel.class);

        initEditText();
        binding.add.setVisibility(View.GONE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if(!projectParticipantsViewModel.getIsl()){
            binding.add.setVisibility(View.GONE);
        }

        binding.projectName.setText(projectParticipantsViewModel.getProjectTitle());
        Glide
                .with(ProjectParticipants.this)
                .load(projectParticipantsViewModel.getProjectLog())
                .into(binding.prLogo);

        binding.linMain.setLayoutManager(new LinearLayoutManager(this));

        projectParticipantsViewModel.getUsersFromProject().observe(this, userInformations -> {
            adapter = new ParticipantsAdapter(userInformations, this);
            binding.linMain.setAdapter(adapter);
        });

        projectParticipantsViewModel.getProjectUsers();

        binding.add.setOnClickListener(v -> {
            Intent intent = new Intent(ProjectParticipants.this, AddParticipant.class);
            startActivity(intent);
        });
        binding.notification.setOnClickListener(v -> {

        });
        binding.find.setOnClickListener(v -> {
            projectParticipantsViewModel.findParticipant();
            binding.cancel.setVisibility(View.VISIBLE);
        });
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.nameFriend.setText("");
                binding.cancel.setVisibility(View.GONE);
                binding.nameFriend.setText("");
                projectParticipantsViewModel.setFindName("");
                projectParticipantsViewModel.getProjectUsers();
            }
        });

//        PostDatas post = new PostDatas();
//        post.postDataGetProjectParticipant("GetPeoplesFromProjects", id, mail, new CallBackInt5() {
//            @Override
//            public void invoke(String ids, String names, String photos) {
//                binding.linMain.removeAllViews();
//                String[] idsArr = ids.split("\uD83D\uDD70");
//                String[] namesArr = names.split("\uD83D\uDD70");
//                String[] photosArr = photos.split("\uD83D\uDD70");
//
//                for (int i = 0; i < idsArr.length; i++) {
//                    View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
//                    TextView nameu = (TextView) custom.findViewById(R.id.textView3);
//                    ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
//                    ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
//                    TextView project1 = (TextView) custom.findViewById(R.id.projects1);
//                    project1.setVisibility(View.GONE);
//                    ImageView messege = (ImageView) custom.findViewById(R.id.notban);
//                    messege.setImageResource(R.drawable.delete_black);
//                    userCircle.setVisibility(View.GONE);
//                    System.out.println(photosArr[i]);
//
//                    Glide
//                            .with(ProjectParticipants.this)
//                            .load(photosArr[i])
//                            .into(loadImage);
//                    nameu.setText(namesArr[i]);
//
//                    messege.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });
//
//                    binding.linMain.addView(custom);
//                }
//                View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
//                binding.linMain.addView(empty);
//            }
//        });


    }

    private void initEditText(){
        binding.nameFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                projectParticipantsViewModel.setFindName(s.toString());
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