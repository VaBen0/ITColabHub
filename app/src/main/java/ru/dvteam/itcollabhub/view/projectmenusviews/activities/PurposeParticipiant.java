package ru.dvteam.itcollabhub.view.projectmenusviews.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.UsersChosenTheme;
import ru.dvteam.itcollabhub.adapter.PurposeParticipantAdapter;
import ru.dvteam.itcollabhub.adapter.PurposesAdapter;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.databinding.ActivityPurposeLeadBinding;
import ru.dvteam.itcollabhub.databinding.ActivityPurposeParticipiantBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.PurposeViewModle;

public class PurposeParticipiant extends AppCompatActivity {

    ActivityPurposeParticipiantBinding binding;
    PurposeViewModle purposeViewModle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityPurposeParticipiantBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        purposeViewModle = new ViewModelProvider(this).get(PurposeViewModle.class);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(PurposeParticipiant.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);



        binding.nameProject.setText(purposeViewModle.getProjectTitle());
        Glide
                .with(PurposeParticipiant.this)
                .load(purposeViewModle.getProjectLog())
                .into(binding.prLogo);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.purposePlace.setLayoutManager(linearLayoutManager);

        purposeViewModle.getPurpsArray().observe(this, purposeInformations -> {
            PurposeParticipantAdapter adapter = new PurposeParticipantAdapter(purposeInformations, this);
            binding.purposePlace.setAdapter(adapter);
        });

        purposeViewModle.setPurposes();

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