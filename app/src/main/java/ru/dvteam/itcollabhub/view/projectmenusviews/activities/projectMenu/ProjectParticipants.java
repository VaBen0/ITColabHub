package ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.view.adapter.ParticipantsAdapter;
import ru.dvteam.itcollabhub.databinding.ActivityProjectParticipantsBinding;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ProjectParticipantsViewModel;

public class ProjectParticipants extends AppCompatActivity implements ColorPickerDialogListener {

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
        //binding.add.setVisibility(View.GONE);
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ProjectParticipants.this, typedValue.resourceId);

        getWindow().setStatusBarColor(color);

        binding.projectName.setText(projectParticipantsViewModel.getProjectTitle());
        Glide
                .with(ProjectParticipants.this)
                .load(projectParticipantsViewModel.getProjectLog())
                .into(binding.prLogo);

        binding.linMain.setLayoutManager(new LinearLayoutManager(this));

        projectParticipantsViewModel.getUsersFromProject().observe(this, userInformations -> {
            adapter = new ParticipantsAdapter(userInformations, this, color);
            binding.linMain.setAdapter(adapter);
        });

        projectParticipantsViewModel.getProjectUsers();

        binding.notification.setOnClickListener(v -> {
            ColorPickerDialog.newBuilder()
                    .setColor(Color.RED)
                    .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                    .setAllowCustom(true)
                    .setAllowPresets(true)
                    .setColorShape(ColorShape.SQUARE)
                    .show(this);
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

    @Override
    public void onColorSelected(int dialogId, int color) {
        binding.textView.setTextColor(color);
        System.out.printf("#%06X%n", (0xFFFFFF & color));
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}