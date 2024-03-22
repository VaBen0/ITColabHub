package ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityNotifyInParticipantsBinding;

public class NotifyInParticipants extends AppCompatActivity {

    ActivityNotifyInParticipantsBinding binding;

    String id, title, description, prPhoto, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotifyInParticipantsBinding.inflate(getLayoutInflater());

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
                .with(NotifyInParticipants.this)
                .load(prPhoto)
                .into(binding.prLogo);

    }
}